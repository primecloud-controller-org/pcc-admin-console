/*
 * Copyright 2018 by PrimeCloud Controller/OSS Community.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package org.primecloudcontroller.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primecloudcontroller.admin.exception.ApplicationException;
import org.primecloudcontroller.admin.model.AwsCertificate;
import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.model.PlatformAws;
import org.primecloudcontroller.admin.model.PlatformVmware;
import org.primecloudcontroller.admin.model.PlatformVmwareInstanceType;
import org.primecloudcontroller.admin.model.VmwareKeyPair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatformService extends AbstractService {

    public List<Platform> findAll() {
        List<Platform> platforms = platformRepository.findAll();

        {
            List<PlatformAws> platformAwses = platformAwsRepository.findAll();
            Map<Long, PlatformAws> platformAwsMap = new LinkedHashMap<Long, PlatformAws>();
            for (PlatformAws platformAws : platformAwses) {
                platformAwsMap.put(platformAws.getPlatformNo(), platformAws);
            }

            for (Platform platform : platforms) {
                platform.setAws(platformAwsMap.get(platform.getPlatformNo()));
            }
        }

        {
            List<PlatformVmware> platformVmwares = platformVmwareRepository.findAll();
            Map<Long, PlatformVmware> platformVmwareMap = new LinkedHashMap<Long, PlatformVmware>();
            for (PlatformVmware platformVmware : platformVmwares) {
                platformVmwareMap.put(platformVmware.getPlatformNo(), platformVmware);
            }

            for (Platform platform : platforms) {
                platform.setVmware(platformVmwareMap.get(platform.getPlatformNo()));
            }
        }

        return platforms;
    }

    public Platform findOne(Long platformNo) {
        Platform platform = platformRepository.findOne(platformNo);

        if (platform == null) {
            return null;
        }

        // AWS
        if ("aws".equals(platform.getPlatformType())) {
            PlatformAws platformAws = platformAwsRepository.findOne(platformNo);
            platform.setAws(platformAws);
        }
        // VMware
        else if ("vmware".equals(platform.getPlatformType())) {
            PlatformVmware platformVmware = platformVmwareRepository.findOne(platformNo);
            platform.setVmware(platformVmware);

            List<PlatformVmwareInstanceType> platformVmwareInstanceTypes = platformVmwareInstanceTypeRepository
                    .findByPlatformNoOrderByInstanceTypeNo(platformNo);
            platformVmware.setInstanceTypes(platformVmwareInstanceTypes);
        }

        return platform;
    }

    public List<Platform> findByPlatformNos(Collection<Long> platformNos) {
        List<Platform> platforms = platformRepository.findByPlatformNoIn(platformNos);

        {
            List<PlatformAws> platformAwses = platformAwsRepository.findAll();
            Map<Long, PlatformAws> platformAwsMap = new LinkedHashMap<Long, PlatformAws>();
            for (PlatformAws platformAws : platformAwses) {
                platformAwsMap.put(platformAws.getPlatformNo(), platformAws);
            }

            for (Platform platform : platforms) {
                platform.setAws(platformAwsMap.get(platform.getPlatformNo()));
            }
        }

        {
            List<PlatformVmware> platformVmwares = platformVmwareRepository.findByPlatformNoIn(platformNos);
            Map<Long, PlatformVmware> platformVmwareMap = new LinkedHashMap<Long, PlatformVmware>();
            for (PlatformVmware platformVmware : platformVmwares) {
                platformVmwareMap.put(platformVmware.getPlatformNo(), platformVmware);
            }

            for (Platform platform : platforms) {
                platform.setVmware(platformVmwareMap.get(platform.getPlatformNo()));
            }
        }

        return platforms;
    }

    public Platform saveAws(Map<String, String> params) {
        params = trim(params);

        boolean add;
        if (StringUtils.equals(params.get("mode"), "add")) {
            add = true;
        } else if (StringUtils.equals(params.get("mode"), "edit")) {
            add = false;
        } else {
            throw new ApplicationException("message.platform.illegalMode");
        }

        Platform platform = checkSave(params, "aws", add);
        PlatformAws platformAws = checkSaveAws(params, add);

        platform = platformRepository.save(platform);
        platformAws = platformAwsRepository.save(platformAws);
        platform.setAws(platformAws);

        return platform;
    }

    public Platform saveVmware(Map<String, String> params) {
        params = trim(params);

        boolean add;
        if (StringUtils.equals(params.get("mode"), "add")) {
            add = true;
        } else if (StringUtils.equals(params.get("mode"), "edit")) {
            add = false;
        } else {
            throw new ApplicationException("message.platform.illegalMode");
        }

        Platform platform = checkSave(params, "vmware", add);
        PlatformVmware platformVmware = checkSaveVmware(params, add);

        platform = platformRepository.save(platform);
        platformVmware = platformVmwareRepository.save(platformVmware);
        platform.setVmware(platformVmware);

        return platform;
    }

    protected Platform checkSave(Map<String, String> params, String platformType, boolean add) {
        Platform platform;

        // PlatformNo
        {
            Long platformNo;
            try {
                platformNo = Long.valueOf(params.get("platformNo"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.illegalPlatformNo");
            }

            if (add) {
                boolean exists = platformRepository.exists(platformNo);
                if (exists) {
                    throw new ApplicationException("message.platform.existPlatformNo");
                }

                platform = new Platform();
                platform.setPlatformNo(platformNo);
            } else {
                platform = platformRepository.findOne(platformNo);
                if (platform == null) {
                    throw new ApplicationException("message.platform.notExistPlatformNo");
                }
            }
        }

        // PlatformName
        {
            String platformName = params.get("platformName");

            int length = StringUtils.length(platformName);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.platform.illegalPlatformName");
            }

            if (add || !StringUtils.equals(platform.getPlatformName(), platformName)) {
                boolean exists = platformRepository.existsByPlatformName(platformName);
                if (exists) {
                    throw new ApplicationException("message.platform.existPlatformName");
                }
            }

            platform.setPlatformName(platformName);
        }

        // PlatformNameDisp
        {
            String platformNameDisp = params.get("platformNameDisp");

            int length = StringUtils.length(platformNameDisp);
            if (length == 0 || length > 300) {
                throw new ApplicationException("message.platform.illegalPlatformNameDisp");
            }

            platform.setPlatformNameDisp(platformNameDisp);
        }

        // PlatformSimplenameDisp
        {
            String platformSimplenameDisp = params.get("platformSimplenameDisp");

            int length = StringUtils.length(platformSimplenameDisp);
            if (length == 0 || length > 200) {
                throw new ApplicationException("message.platform.illegalPlatformSimplenameDisp");
            }

            platform.setPlatformSimplenameDisp(platformSimplenameDisp);
        }

        // Internal
        platform.setInternal(Boolean.valueOf(params.get("internal")));

        // Proxy
        platform.setProxy(Boolean.valueOf(params.get("proxy")));

        // PlatformType
        {
            if (!add) {
                if (!StringUtils.equals(platform.getPlatformType(), platformType)) {
                    throw new ApplicationException("message.platform.notChangablePlatformType");
                }
            }

            platform.setPlatformType(platformType);
        }

        // Selectable
        platform.setSelectable(Boolean.valueOf(params.get("selectable")));

        // ViewOrder
        {
            Integer viewOrder = null;
            if (StringUtils.isNotEmpty(params.get("viewOrder"))) {
                try {
                    viewOrder = Integer.valueOf(params.get("viewOrder"));
                } catch (NumberFormatException e) {
                    throw new ApplicationException("message.platform.illegalViewOrder");
                }
            }

            platform.setViewOrder(viewOrder);
        }

        return platform;
    }

    protected PlatformAws checkSaveAws(Map<String, String> params, boolean add) {
        PlatformAws platformAws;

        Long platformNo = Long.valueOf(params.get("platformNo"));

        if (add) {
            platformAws = new PlatformAws();
            platformAws.setPlatformNo(platformNo);
        } else {
            platformAws = platformAwsRepository.findOne(platformNo);
        }

        // Host
        {
            String host = params.get("host");

            int length = StringUtils.length(host);
            if (length == 0 || length > 500) {
                throw new ApplicationException("message.platform.illegalAwsHost");
            }

            platformAws.setHost(host);
        }

        // Port
        {
            Integer port;
            try {
                port = Integer.valueOf(params.get("port"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.illegalAwsPort");
            }

            if (port == null || port < 0 || port > 65535) {
                throw new ApplicationException("message.platform.illegalAwsPort");
            }

            platformAws.setPort(port);
        }

        // Secure
        platformAws.setSecure(Boolean.valueOf(params.get("secure")));

        // Eucalyptus
        if (BooleanUtils.isTrue(Boolean.valueOf(params.get("euca")))) {
            platformAws.setEuca(true);
            platformAws.setVpc(false);
            platformAws.setAvailabilityZone("");
            platformAws.setVpcId("");
            platformAws.setSubnetId("");
        }
        // AWS VPC
        else if (BooleanUtils.isTrue(Boolean.valueOf(params.get("vpc")))) {
            platformAws.setEuca(false);
            platformAws.setVpc(true);
            platformAws.setAvailabilityZone("");

            // VpcID
            {
                String vpcId = params.get("vpcId");

                int length = StringUtils.length(vpcId);
                if (length == 0 || length > 30) {
                    throw new ApplicationException("message.platform.illegalAwsVpcId");
                }

                platformAws.setVpcId(vpcId);
            }

            // SubnetId
            {
                String subnetId = params.get("subnetId");

                int length = StringUtils.length(subnetId);
                if (length > 300) {
                    throw new ApplicationException("message.platform.illegalAwsSubnetId");
                }

                platformAws.setSubnetId(StringUtils.defaultString(subnetId));
            }
        }
        // AWS Clasic
        else {
            platformAws.setEuca(false);
            platformAws.setVpc(false);
            platformAws.setVpcId("");
            platformAws.setSubnetId("");

            // AvailabilityZone
            {
                String availabilityZone = params.get("availabilityZone");

                int length = StringUtils.length(availabilityZone);
                if (length > 300) {
                    throw new ApplicationException("message.platform.illegalAwsAvailabilityZone");
                }

                platformAws.setAvailabilityZone(StringUtils.defaultString(availabilityZone));
            }
        }

        platformAws.setRegion("");

        return platformAws;
    }

    protected PlatformVmware checkSaveVmware(Map<String, String> params, boolean add) {
        PlatformVmware platformVmware;

        Long platformNo = Long.valueOf(params.get("platformNo"));

        if (add) {
            platformVmware = new PlatformVmware();
            platformVmware.setPlatformNo(platformNo);
        } else {
            platformVmware = platformVmwareRepository.findOne(platformNo);
        }

        // Url
        {
            String url = params.get("url");

            int length = StringUtils.length(url);
            if (length == 0 || length > 500) {
                throw new ApplicationException("message.platform.illegalVmwareUrl");
            }

            platformVmware.setUrl(url);
        }

        // Username
        {
            String username = params.get("username");

            int length = StringUtils.length(username);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.platform.illegalVmwareUsername");
            }

            platformVmware.setUsername(username);
        }

        // Password
        {
            String password = params.get("password");

            int length = StringUtils.length(password);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.platform.illegalVmwarePassword");
            }

            platformVmware.setPassword(password);
        }

        // Datacenter
        {
            String datacenter = params.get("datacenter");

            int length = StringUtils.length(datacenter);
            if (length == 0 || length > 300) {
                throw new ApplicationException("message.platform.illegalVmwareDatacenter");
            }

            platformVmware.setDatacenter(datacenter);
        }

        // PublicNetwork
        {
            String publicNetwork = params.get("publicNetwork");

            int length = StringUtils.length(publicNetwork);
            if (length > 300) {
                throw new ApplicationException("message.platform.illegalVmwarePublicNetwork");
            }

            platformVmware.setPublicNetwork(StringUtils.defaultString(publicNetwork));
        }

        // PrivateNetwork
        {
            String privateNetwork = params.get("privateNetwork");

            int length = StringUtils.length(privateNetwork);
            if (length == 0 || length > 300) {
                throw new ApplicationException("message.platform.illegalVmwarePrivateNetwork");
            }

            platformVmware.setPrivateNetwork(privateNetwork);
        }

        // ComputeResource
        {
            String computeResource = params.get("computeResource");

            int length = StringUtils.length(computeResource);
            if (length > 300) {
                throw new ApplicationException("message.platform.illegalVmwareComputeResource");
            }

            platformVmware.setComputeResource(StringUtils.defaultString(computeResource));
        }

        return platformVmware;
    }

    public Platform checkRemove(String platformNoStr) {
        Long platformNo;
        try {
            platformNo = Long.valueOf(platformNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.platform.illegalPlatformNo");
        }

        Platform platform = platformRepository.findOne(platformNo);
        if (platform == null) {
            throw new ApplicationException("message.platform.notExist");
        }

        // instance must not exist
        {
            long count = instanceRepository.countByPlatformNo(platformNo);
            if (count > 0) {
                throw new ApplicationException("message.platform.notRemovableAsInstanceExist");
            }
        }

        // image must not exist
        {
            long count = imageRepository.countByPlatformNo(platformNo);
            if (count > 0) {
                throw new ApplicationException("message.platform.notRemovableAsImageExist");
            }
        }

        return platform;
    }

    public void remove(String platformNoStr) {
        Platform platform = checkRemove(platformNoStr);

        PlatformAws platformAws = platformAwsRepository.findOne(platform.getPlatformNo());
        if (platformAws != null) {
            platformAwsRepository.delete(platformAws);
        }

        PlatformVmware platformVmware = platformVmwareRepository.findOne(platform.getPlatformNo());
        if (platformVmware != null) {
            List<PlatformVmwareInstanceType> instanceTypes = platformVmwareInstanceTypeRepository
                    .findByPlatformNoOrderByInstanceTypeNo(platform.getPlatformNo());
            if (instanceTypes.size() > 0) {
                platformVmwareInstanceTypeRepository.delete(instanceTypes);
            }

            platformVmwareRepository.delete(platformVmware);
        }

        platformRepository.delete(platform);
    }

    public PlatformVmwareInstanceType addVmwareInstanceType(Map<String, String> params) {
        params = trim(params);

        PlatformVmwareInstanceType instanceType = new PlatformVmwareInstanceType();

        {
            Long platformNo;
            try {
                platformNo = Long.valueOf(params.get("platformNo"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.vmwareInstanceType.illegalPlatformNo");
            }

            boolean exists = platformRepository.exists(platformNo);
            if (!exists) {
                throw new ApplicationException("message.platform.vmwareInstanceType.notExistPlatformNo");
            }

            instanceType.setPlatformNo(platformNo);
        }

        {
            Long instanceTypeNo;
            try {
                instanceTypeNo = Long.valueOf(params.get("instanceTypeNo"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.vmwareInstanceType.illegalInstanceTypeNo");
            }

            boolean exists = platformVmwareInstanceTypeRepository.exists(instanceTypeNo);
            if (exists) {
                throw new ApplicationException("message.platform.vmwareInstanceType.existInstanceTypeNo");
            }

            instanceType.setInstanceTypeNo(instanceTypeNo);
        }

        {
            String instanceTypeName = params.get("instanceTypeName");

            int length = StringUtils.length(instanceTypeName);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.platform.vmwareInstanceType.illegalInstanceTypeName");
            }

            boolean exists = platformVmwareInstanceTypeRepository
                    .existsByPlatformNoAndInstanceTypeName(instanceType.getPlatformNo(), instanceTypeName);
            if (exists) {
                throw new ApplicationException("message.platform.vmwareInstanceType.existInstanceTypeName");
            }

            instanceType.setInstanceTypeName(instanceTypeName);
        }

        {
            Integer cpu;
            try {
                cpu = Integer.valueOf(params.get("cpu"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.vmwareInstanceType.illegalCpu");
            }

            instanceType.setCpu(cpu);
        }

        {
            Long memory;
            try {
                memory = Long.valueOf(params.get("memory"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.platform.vmwareInstanceType.illegalMemory");
            }

            instanceType.setMemory(memory);
        }

        instanceType = platformVmwareInstanceTypeRepository.save(instanceType);

        return instanceType;
    }

    public PlatformVmwareInstanceType checkRemoveVmwareInstanceType(String instanceTypeNoStr) {
        Long instanceTypeNo;
        try {
            instanceTypeNo = Long.valueOf(instanceTypeNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.platform.vmwareInstanceType.illegalInstanceTypeNo");
        }

        PlatformVmwareInstanceType instanceType = platformVmwareInstanceTypeRepository.findOne(instanceTypeNo);
        if (instanceType == null) {
            throw new ApplicationException("message.platform.vmwareInstanceType.notExist");
        }

        // vmware instance must not exist
        {
            List<Long> instanceNos = instanceRepository.findInstanceNoByPlatformNo(instanceType.getPlatformNo());
            long count = vmwareInstanceRepository.countByInstanceNoInAndInstanceType(instanceNos,
                    instanceType.getInstanceTypeName());
            if (count > 0) {
                throw new ApplicationException("message.platform.vmwareInstanceType.notRemovableAsInstanceExist");
            }
        }

        return instanceType;
    }

    public void removeVmwareInstanceType(String instanceTypeNoStr) {
        PlatformVmwareInstanceType instanceType = checkRemoveVmwareInstanceType(instanceTypeNoStr);

        platformVmwareInstanceTypeRepository.delete(instanceType);
    }

    public List<Platform> findAvailables(Long userNo) {
        List<Platform> platforms = new ArrayList<>();

        List<AwsCertificate> awsCertificates = awsCertificateRepository.findByUserNo(userNo);
        for (AwsCertificate awsCertificate : awsCertificates) {
            Platform platform = findOne(awsCertificate.getPlatformNo());
            platforms.add(platform);
        }

        List<VmwareKeyPair> vmwareKeyPairs = vmwareKeyPairRepository.findByUserNo(userNo);
        for (VmwareKeyPair vmwareKeyPair : vmwareKeyPairs) {
            Platform platform = findOne(vmwareKeyPair.getPlatformNo());
            platforms.add(platform);
        }

        Collections.sort(platforms, (p1, p2) -> p1.getPlatformNo().compareTo(p2.getPlatformNo()));

        return platforms;
    }

}
