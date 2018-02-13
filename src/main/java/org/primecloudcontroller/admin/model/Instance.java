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
import javax.persistence.Transient;

@Entity
@Table(name = "INSTANCE")
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "INSTANCE_NO")
    private Long instanceNo;

    @Column(name = "FARM_NO")
    private Long farmNo;

    @Column(name = "INSTANCE_NAME")
    private String instanceName;

    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "IMAGE_NO")
    private Long imageNo;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "FQDN")
    private String fqdn;

    @Column(name = "INSTANCE_CODE")
    private String instanceCode;

    @Column(name = "PUBLIC_IP")
    private String publicIp;

    @Column(name = "PRIVATE_IP")
    private String privateIp;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROGRESS")
    private Integer progress;

    @Column(name = "COODINATE_STATUS")
    private String coodinateStatus;

    @Column(name = "LOAD_BALANCER")
    private Boolean loadBalancer;

    @Transient
    private AwsInstance aws;

    @Transient
    private VmwareInstance vmware;

    @Transient
    private Farm farm;

    @Transient
    private Platform platform;

    @Transient
    private Image image;

    public Long getInstanceNo() {
        return instanceNo;
    }

    public void setInstanceNo(Long instanceNo) {
        this.instanceNo = instanceNo;
    }

    public Long getFarmNo() {
        return farmNo;
    }

    public void setFarmNo(Long farmNo) {
        this.farmNo = farmNo;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public Long getImageNo() {
        return imageNo;
    }

    public void setImageNo(Long imageNo) {
        this.imageNo = imageNo;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getInstanceCode() {
        return instanceCode;
    }

    public void setInstanceCode(String instanceCode) {
        this.instanceCode = instanceCode;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getCoodinateStatus() {
        return coodinateStatus;
    }

    public void setCoodinateStatus(String coodinateStatus) {
        this.coodinateStatus = coodinateStatus;
    }

    public Boolean getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(Boolean loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public AwsInstance getAws() {
        return aws;
    }

    public void setAws(AwsInstance aws) {
        this.aws = aws;
    }

    public VmwareInstance getVmware() {
        return vmware;
    }

    public void setVmware(VmwareInstance vmware) {
        this.vmware = vmware;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
