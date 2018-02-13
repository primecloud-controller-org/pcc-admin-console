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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primecloudcontroller.admin.model.AwsInstance;
import org.primecloudcontroller.admin.model.Farm;
import org.primecloudcontroller.admin.model.Image;
import org.primecloudcontroller.admin.model.Instance;
import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.model.VmwareInstance;
import org.primecloudcontroller.admin.model.VmwareKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstanceService extends AbstractService {

    @Autowired
    protected FarmService farmService;

    @Autowired
    protected PlatformService platformService;

    @Autowired
    protected ImageService imageService;

    public List<Instance> findAll() {
        List<Instance> instances = instanceRepository.findAll();

        {
            List<AwsInstance> awsInstancees = awsInstanceRepository.findAll();
            Map<Long, AwsInstance> awsInstanceMap = new LinkedHashMap<Long, AwsInstance>();
            for (AwsInstance awsInstance : awsInstancees) {
                awsInstanceMap.put(awsInstance.getInstanceNo(), awsInstance);
            }

            for (Instance instance : instances) {
                instance.setAws(awsInstanceMap.get(instance.getInstanceNo()));
            }
        }

        {
            List<VmwareInstance> vmwareInstances = vmwareInstanceRepository.findAll();
            Map<Long, VmwareInstance> vmwareInstanceMap = new LinkedHashMap<Long, VmwareInstance>();
            for (VmwareInstance vmwareInstance : vmwareInstances) {
                vmwareInstanceMap.put(vmwareInstance.getInstanceNo(), vmwareInstance);
            }

            for (Instance instance : instances) {
                instance.setVmware(vmwareInstanceMap.get(instance.getInstanceNo()));
            }
        }

        {
            List<Farm> farms = farmService.findAll();
            Map<Long, Farm> farmMap = new LinkedHashMap<Long, Farm>();
            for (Farm farm : farms) {
                farmMap.put(farm.getFarmNo(), farm);
            }

            for (Instance instance : instances) {
                instance.setFarm(farmMap.get(instance.getFarmNo()));
            }
        }

        {
            List<Platform> platforms = platformService.findAll();
            Map<Long, Platform> platformMap = new LinkedHashMap<Long, Platform>();
            for (Platform platform : platforms) {
                platformMap.put(platform.getPlatformNo(), platform);
            }

            for (Instance instance : instances) {
                instance.setPlatform(platformMap.get(instance.getPlatformNo()));
            }
        }

        {
            List<Image> images = imageService.findAll();
            Map<Long, Image> imageMap = new LinkedHashMap<Long, Image>();
            for (Image image : images) {
                imageMap.put(image.getImageNo(), image);
            }

            for (Instance instance : instances) {
                instance.setImage(imageMap.get(instance.getImageNo()));
            }
        }

        return instances;
    }

    public Instance findOne(Long instanceNo) {
        Instance instance = instanceRepository.findOne(instanceNo);

        if (instance == null) {
            return null;
        }

        AwsInstance awsInstance = awsInstanceRepository.findOne(instanceNo);
        instance.setAws(awsInstance);

        VmwareInstance vmwareInstance = vmwareInstanceRepository.findOne(instanceNo);
        instance.setVmware(vmwareInstance);

        if (vmwareInstance != null) {
            VmwareKeyPair vmwareKeyPair = vmwareKeyPairRepository.findOne(vmwareInstance.getKeyPairNo());
            vmwareInstance.setKeyPair(vmwareKeyPair);
        }

        Farm farm = farmService.findOne(instance.getFarmNo());
        instance.setFarm(farm);

        Platform platform = platformService.findOne(instance.getPlatformNo());
        instance.setPlatform(platform);

        Image image = imageService.findOne(instance.getImageNo());
        instance.setImage(image);

        return instance;
    }

}
