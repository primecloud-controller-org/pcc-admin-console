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
@Table(name = "PLATFORM")
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "PLATFORM_NAME")
    private String platformName;

    @Column(name = "PLATFORM_NAME_DISP")
    private String platformNameDisp;

    @Column(name = "PLATFORM_SIMPLENAME_DISP")
    private String platformSimplenameDisp;

    @Column(name = "INTERNAL")
    private Boolean internal;

    @Column(name = "PROXY")
    private Boolean proxy;

    @Column(name = "PLATFORM_TYPE")
    private String platformType;

    @Column(name = "SELECTABLE")
    private Boolean selectable;

    @Column(name = "VIEW_ORDER")
    private Integer viewOrder;

    @Transient
    private PlatformAws aws;

    @Transient
    private PlatformVmware vmware;

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformNameDisp() {
        return platformNameDisp;
    }

    public void setPlatformNameDisp(String platformNameDisp) {
        this.platformNameDisp = platformNameDisp;
    }

    public String getPlatformSimplenameDisp() {
        return platformSimplenameDisp;
    }

    public void setPlatformSimplenameDisp(String platformSimplenameDisp) {
        this.platformSimplenameDisp = platformSimplenameDisp;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Boolean getProxy() {
        return proxy;
    }

    public void setProxy(Boolean proxy) {
        this.proxy = proxy;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }

    public Integer getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }

    public PlatformAws getAws() {
        return aws;
    }

    public void setAws(PlatformAws aws) {
        this.aws = aws;
    }

    public PlatformVmware getVmware() {
        return vmware;
    }

    public void setVmware(PlatformVmware vmware) {
        this.vmware = vmware;
    }

}
