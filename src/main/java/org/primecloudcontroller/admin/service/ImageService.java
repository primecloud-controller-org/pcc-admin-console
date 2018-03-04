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

import org.apache.commons.lang3.StringUtils;
import org.primecloudcontroller.admin.exception.ApplicationException;
import org.primecloudcontroller.admin.model.Image;
import org.primecloudcontroller.admin.model.ImageAws;
import org.primecloudcontroller.admin.model.ImageVmware;
import org.primecloudcontroller.admin.model.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageService extends AbstractService {

    @Autowired
    protected PlatformService platformService;

    public List<Image> findAll() {
        List<Image> images = imageRepository.findAll();

        {
            List<ImageAws> imageAwses = imageAwsRepository.findAll();
            Map<Long, ImageAws> imageAwsMap = new LinkedHashMap<Long, ImageAws>();
            for (ImageAws imageAws : imageAwses) {
                imageAwsMap.put(imageAws.getImageNo(), imageAws);
            }

            for (Image image : images) {
                image.setAws(imageAwsMap.get(image.getImageNo()));
            }
        }

        {
            List<ImageVmware> imageVmwares = imageVmwareRepository.findAll();
            Map<Long, ImageVmware> imageVmwareMap = new LinkedHashMap<Long, ImageVmware>();
            for (ImageVmware imageVmware : imageVmwares) {
                imageVmwareMap.put(imageVmware.getImageNo(), imageVmware);
            }

            for (Image image : images) {
                image.setVmware(imageVmwareMap.get(image.getImageNo()));
            }
        }

        {
            List<Platform> platforms = platformService.findAll();
            Map<Long, Platform> platformMap = new LinkedHashMap<Long, Platform>();
            for (Platform platform : platforms) {
                platformMap.put(platform.getPlatformNo(), platform);
            }

            for (Image image : images) {
                image.setPlatform(platformMap.get(image.getPlatformNo()));
            }
        }

        return images;
    }

    public Image findOne(Long imageNo) {
        Image image = imageRepository.findOne(imageNo);

        if (image == null) {
            return null;
        }

        Platform platform = platformService.findOne(image.getPlatformNo());
        image.setPlatform(platform);

        // AWS
        if ("aws".equals(platform.getPlatformType())) {
            ImageAws imageAws = imageAwsRepository.findOne(imageNo);
            image.setAws(imageAws);
        }
        // VMware
        else if ("vmware".equals(platform.getPlatformType())) {
            ImageVmware imageVmware = imageVmwareRepository.findOne(imageNo);
            image.setVmware(imageVmware);
        }

        return image;
    }

    public Image saveAws(Map<String, String> params) {
        params = trim(params);

        boolean add;
        if (StringUtils.equals(params.get("mode"), "add")) {
            add = true;
        } else if (StringUtils.equals(params.get("mode"), "edit")) {
            add = false;
        } else {
            throw new ApplicationException("message.image.illegalMode");
        }

        Image image = checkSave(params, "aws", add);
        ImageAws imageAws = checkSaveAws(params, add);

        image = imageRepository.save(image);
        imageAws = imageAwsRepository.save(imageAws);
        image.setAws(imageAws);

        return image;
    }

    public Image saveVmware(Map<String, String> params) {
        params = trim(params);

        boolean add;
        if (StringUtils.equals(params.get("mode"), "add")) {
            add = true;
        } else if (StringUtils.equals(params.get("mode"), "edit")) {
            add = false;
        } else {
            throw new ApplicationException("message.image.illegalMode");
        }

        Image image = checkSave(params, "vmware", add);
        ImageVmware imageVmware = checkSaveVmware(params, add);

        image = imageRepository.save(image);
        imageVmware = imageVmwareRepository.save(imageVmware);
        image.setVmware(imageVmware);

        return image;
    }

    protected Image checkSave(Map<String, String> params, String platformType, boolean add) {
        Image image;

        // ImageNo
        {
            Long imageNo;
            try {
                imageNo = Long.valueOf(params.get("imageNo"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.image.illegalImageNo");
            }

            if (add) {
                boolean exists = imageRepository.exists(imageNo);
                if (exists) {
                    throw new ApplicationException("message.image.existImageNo");
                }

                image = new Image();
                image.setImageNo(imageNo);
            } else {
                image = imageRepository.findOne(imageNo);
                if (image == null) {
                    throw new ApplicationException("message.image.notExistImageNo");
                }
            }
        }

        // PlatformNo
        {
            Long platformNo;
            try {
                platformNo = Long.valueOf(params.get("platformNo"));
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.image.illegalPlatformNo");
            }

            Platform platform = platformRepository.findOne(platformNo);
            if (!StringUtils.equals(platform.getPlatformType(), platformType)) {
                throw new ApplicationException("message.image.notChangablePlatformType");
            }

            image.setPlatformNo(platformNo);
        }

        // ImageName
        {
            String imageName = params.get("imageName");

            int length = StringUtils.length(imageName);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.image.illegalImageName");
            }

            if (add || !StringUtils.equals(image.getImageName(), imageName)) {
                boolean exists = imageRepository.existsByImageName(imageName);
                if (exists) {
                    throw new ApplicationException("message.image.existImageName");
                }
            }

            image.setImageName(imageName);
        }

        // ImageNameDisp
        {
            String imageNameDisp = params.get("imageNameDisp");

            int length = StringUtils.length(imageNameDisp);
            if (length == 0 || length > 300) {
                throw new ApplicationException("message.image.illegalImageNameDisp");
            }

            image.setImageNameDisp(imageNameDisp);
        }

        // Os
        {
            String os = params.get("os");

            int length = StringUtils.length(os);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.image.illegalOs");
            }

            image.setOs(os);
        }

        // OsDisp
        {
            String osDisp = params.get("osDisp");

            int length = StringUtils.length(osDisp);
            if (length == 0 || length > 300) {
                throw new ApplicationException("message.image.illegalOsDisp");
            }

            image.setOsDisp(osDisp);
        }

        // Selectable
        image.setSelectable(Boolean.valueOf(params.get("selectable")));

        // ComponentTypeNos
        {
            String componentTypeNos = params.get("componentTypeNos");

            int length = StringUtils.length(componentTypeNos);
            if (length > 500) {
                throw new ApplicationException("message.image.illegalComponentTypeNos");
            }

            image.setComponentTypeNos(StringUtils.defaultString(componentTypeNos));
        }

        // ZabbixTemplate
        {
            String zabbixTemplate = params.get("zabbixTemplate");

            int length = StringUtils.length(zabbixTemplate);
            if (length > 100) {
                throw new ApplicationException("message.image.illegalZabbixTemplate");
            }

            image.setZabbixTemplate(StringUtils.defaultString(zabbixTemplate));
        }

        // ZabbixDisabled
        image.setZabbixDisabled(Boolean.valueOf(params.get("zabbixDisabled")));

        // PuppetDisabled
        image.setPuppetDisabled(Boolean.valueOf(params.get("puppetDisabled")));

        // ViewOrder
        {
            Integer viewOrder = null;
            if (StringUtils.isNotEmpty(params.get("viewOrder"))) {
                try {
                    viewOrder = Integer.valueOf(params.get("viewOrder"));
                } catch (NumberFormatException e) {
                    throw new ApplicationException("message.image.illegalViewOrder");
                }
            }

            image.setViewOrder(viewOrder);
        }

        return image;
    }

    protected ImageAws checkSaveAws(Map<String, String> params, boolean add) {
        ImageAws imageAws;

        Long imageNo = Long.valueOf(params.get("imageNo"));

        if (add) {
            imageAws = new ImageAws();
            imageAws.setImageNo(imageNo);
        } else {
            imageAws = imageAwsRepository.findOne(imageNo);
        }

        // ImageId
        {
            String imageId = params.get("imageId");

            int length = StringUtils.length(imageId);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.image.illegalAwsImageId");
            }

            imageAws.setImageId(imageId);
        }

        // KernelId
        {
            String kernelId = params.get("kernelId");

            int length = StringUtils.length(kernelId);
            if (length > 100) {
                throw new ApplicationException("message.image.illegalAwsKernelId");
            }

            imageAws.setKernelId(StringUtils.defaultString(kernelId));
        }

        // RamdiskId
        {
            String ramdiskId = params.get("ramdiskId");

            int length = StringUtils.length(ramdiskId);
            if (length > 100) {
                throw new ApplicationException("message.image.illegalAwsRamdiskId");
            }

            imageAws.setRamdiskId(StringUtils.defaultString(ramdiskId));
        }

        // InstanceTypes
        {
            String instanceTypes = params.get("instanceTypes");

            int length = StringUtils.length(instanceTypes);
            if (length == 0 || length > 500) {
                throw new ApplicationException("message.image.illegalAwsInstanceTypes");
            }

            imageAws.setInstanceTypes(instanceTypes);
        }

        // EbsImage
        imageAws.setEbsImage(Boolean.valueOf(params.get("ebsImage")));

        // RootSize
        {
            Integer rootSize = null;
            if (StringUtils.isNotEmpty(params.get("rootSize"))) {
                try {
                    rootSize = Integer.valueOf(params.get("rootSize"));
                } catch (NumberFormatException e) {
                    throw new ApplicationException("message.image.illegalAwsRootSize");
                }
            }

            if (rootSize != null && rootSize <= 0) {
                throw new ApplicationException("message.image.illegalAwsRootSize");
            }

            imageAws.setRootSize(rootSize);
        }

        return imageAws;
    }

    protected ImageVmware checkSaveVmware(Map<String, String> params, boolean add) {
        ImageVmware imageVmware;

        Long imageNo = Long.valueOf(params.get("imageNo"));

        if (add) {
            imageVmware = new ImageVmware();
            imageVmware.setImageNo(imageNo);
        } else {
            imageVmware = imageVmwareRepository.findOne(imageNo);
        }

        // TemplateName
        {
            String templateName = params.get("templateName");

            int length = StringUtils.length(templateName);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.image.illegalVmwareTemplateName");
            }

            imageVmware.setTemplateName(templateName);
        }

        // InstanceTypes
        {
            String instanceTypes = params.get("instanceTypes");

            int length = StringUtils.length(instanceTypes);
            if (length == 0 || length > 500) {
                throw new ApplicationException("message.image.illegalVmwareInstanceTypes");
            }

            imageVmware.setInstanceTypes(instanceTypes);
        }

        // RootSize
        {
            Integer rootSize = null;
            if (StringUtils.isNotEmpty(params.get("rootSize"))) {
                try {
                    rootSize = Integer.valueOf(params.get("rootSize"));
                } catch (NumberFormatException e) {
                    throw new ApplicationException("message.image.illegalVmwareRootSize");
                }
            }

            if (rootSize != null && rootSize <= 0) {
                throw new ApplicationException("message.image.illegalVmwareRootSize");
            }

            imageVmware.setRootSize(rootSize);
        }

        return imageVmware;
    }

    public Image checkRemove(String imageNoStr) {
        Long imageNo;
        try {
            imageNo = Long.valueOf(imageNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.image.illegalImageNo");
        }

        Image image = imageRepository.findOne(imageNo);
        if (image == null) {
            throw new ApplicationException("message.image.notExist");
        }

        // instance must not exist
        {
            long count = instanceRepository.countByImageNo(imageNo);
            if (count > 0) {
                throw new ApplicationException("message.image.notRemovableAsInstanceExist");
            }
        }

        return image;
    }

    public void remove(String imageNoStr) {
        Image image = checkRemove(imageNoStr);

        ImageAws imageAws = imageAwsRepository.findOne(image.getImageNo());
        if (imageAws != null) {
            imageAwsRepository.delete(imageAws);
        }

        ImageVmware imageVmware = imageVmwareRepository.findOne(image.getImageNo());
        if (imageVmware != null) {
            imageVmwareRepository.delete(imageVmware);
        }

        imageRepository.delete(image);
    }

}
