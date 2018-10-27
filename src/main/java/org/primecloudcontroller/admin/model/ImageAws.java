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
package org.primecloudcontroller.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE_AWS")
public class ImageAws implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IMAGE_NO")
    private Long imageNo;

    @Column(name = "IMAGE_ID")
    private String imageId;

    @Column(name = "KERNEL_ID")
    private String kernelId;

    @Column(name = "RAMDISK_ID")
    private String ramdiskId;

    @Column(name = "INSTANCE_TYPES")
    private String instanceTypes;

    @Column(name = "EBS_IMAGE")
    private Boolean ebsImage;

    @Column(name = "ROOT_SIZE")
    private Integer rootSize;

    @Column(name = "DEFAULT_INSTANCE_TYPE")
    private String defaultInstanceType;

    public Long getImageNo() {
        return imageNo;
    }

    public void setImageNo(Long imageNo) {
        this.imageNo = imageNo;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getKernelId() {
        return kernelId;
    }

    public void setKernelId(String kernelId) {
        this.kernelId = kernelId;
    }

    public String getRamdiskId() {
        return ramdiskId;
    }

    public void setRamdiskId(String ramdiskId) {
        this.ramdiskId = ramdiskId;
    }

    public String getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(String instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

    public Boolean getEbsImage() {
        return ebsImage;
    }

    public void setEbsImage(Boolean ebsImage) {
        this.ebsImage = ebsImage;
    }

    public Integer getRootSize() {
        return rootSize;
    }

    public void setRootSize(Integer rootSize) {
        this.rootSize = rootSize;
    }

    public String getDefaultInstanceType() {
        return defaultInstanceType;
    }

    public void setDefaultInstanceType(String defaultInstanceType) {
        this.defaultInstanceType = defaultInstanceType;
    }

}
