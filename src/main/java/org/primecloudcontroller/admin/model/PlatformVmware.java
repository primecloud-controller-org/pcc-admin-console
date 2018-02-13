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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PLATFORM_VMWARE")
public class PlatformVmware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "URL")
    private String url;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DATACENTER")
    private String datacenter;

    @Column(name = "PUBLIC_NETWORK")
    private String publicNetwork;

    @Column(name = "PRIVATE_NETWORK")
    private String privateNetwork;

    @Column(name = "COMPUTE_RESOURCE")
    private String computeResource;

    @Transient
    private List<PlatformVmwareInstanceType> instanceTypes;

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getPublicNetwork() {
        return publicNetwork;
    }

    public void setPublicNetwork(String publicNetwork) {
        this.publicNetwork = publicNetwork;
    }

    public String getPrivateNetwork() {
        return privateNetwork;
    }

    public void setPrivateNetwork(String privateNetwork) {
        this.privateNetwork = privateNetwork;
    }

    public String getComputeResource() {
        return computeResource;
    }

    public void setComputeResource(String computeResource) {
        this.computeResource = computeResource;
    }

    public List<PlatformVmwareInstanceType> getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(List<PlatformVmwareInstanceType> instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

}
