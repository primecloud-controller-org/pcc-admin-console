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
@Table(name = "IMAGE")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IMAGE_NO")
    private Long imageNo;

    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Column(name = "IMAGE_NAME_DISP")
    private String imageNameDisp;

    @Column(name = "OS")
    private String os;

    @Column(name = "OS_DISP")
    private String osDisp;

    @Column(name = "SELECTABLE")
    private Boolean selectable;

    @Column(name = "COMPONENT_TYPE_NOS")
    private String componentTypeNos;

    @Column(name = "ZABBIX_TEMPLATE")
    private String zabbixTemplate;

    @Column(name = "ZABBIX_DISABLED")
    private Boolean zabbixDisabled;

    @Column(name = "PUPPET_DISABLED")
    private Boolean puppetDisabled;

    @Column(name = "VIEW_ORDER")
    private Integer viewOrder;

    @Transient
    private ImageAws aws;

    @Transient
    private ImageVmware vmware;

    @Transient
    private Platform platform;

    public Long getImageNo() {
        return imageNo;
    }

    public void setImageNo(Long imageNo) {
        this.imageNo = imageNo;
    }

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageNameDisp() {
        return imageNameDisp;
    }

    public void setImageNameDisp(String imageNameDisp) {
        this.imageNameDisp = imageNameDisp;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsDisp() {
        return osDisp;
    }

    public void setOsDisp(String osDisp) {
        this.osDisp = osDisp;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }

    public String getComponentTypeNos() {
        return componentTypeNos;
    }

    public void setComponentTypeNos(String componentTypeNos) {
        this.componentTypeNos = componentTypeNos;
    }

    public String getZabbixTemplate() {
        return zabbixTemplate;
    }

    public void setZabbixTemplate(String zabbixTemplate) {
        this.zabbixTemplate = zabbixTemplate;
    }

    public Boolean getZabbixDisabled() {
        return zabbixDisabled;
    }

    public void setZabbixDisabled(Boolean zabbixDisabled) {
        this.zabbixDisabled = zabbixDisabled;
    }

    public Boolean getPuppetDisabled() {
        return puppetDisabled;
    }

    public void setPuppetDisabled(Boolean puppetDisabled) {
        this.puppetDisabled = puppetDisabled;
    }

    public Integer getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }

    public ImageAws getAws() {
        return aws;
    }

    public void setAws(ImageAws aws) {
        this.aws = aws;
    }

    public ImageVmware getVmware() {
        return vmware;
    }

    public void setVmware(ImageVmware vmware) {
        this.vmware = vmware;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

}
