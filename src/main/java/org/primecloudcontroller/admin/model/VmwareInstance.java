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
@Table(name = "VMWARE_INSTANCE")
public class VmwareInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "INSTANCE_NO")
    private Long instanceNo;

    @Column(name = "MACHINE_NAME")
    private String machineName;

    @Column(name = "INSTANCE_TYPE")
    private String instanceType;

    @Column(name = "COMPUTE_RESOURCE")
    private String computeResource;

    @Column(name = "RESOURCE_POOL")
    private String resourcePool;

    @Column(name = "DATASTORE")
    private String datastore;

    @Column(name = "KEY_PAIR_NO")
    private Long keyPairNo;

    @Column(name = "ROOT_SIZE")
    private Integer rootSize;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "PRIVATE_IP_ADDRESS")
    private String privateIpAddress;

    @Transient
    private VmwareKeyPair keyPair;

    public Long getInstanceNo() {
        return instanceNo;
    }

    public void setInstanceNo(Long instanceNo) {
        this.instanceNo = instanceNo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getComputeResource() {
        return computeResource;
    }

    public void setComputeResource(String computeResource) {
        this.computeResource = computeResource;
    }

    public String getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(String resourcePool) {
        this.resourcePool = resourcePool;
    }

    public String getDatastore() {
        return datastore;
    }

    public void setDatastore(String datastore) {
        this.datastore = datastore;
    }

    public Long getKeyPairNo() {
        return keyPairNo;
    }

    public void setKeyPairNo(Long keyPairNo) {
        this.keyPairNo = keyPairNo;
    }

    public Integer getRootSize() {
        return rootSize;
    }

    public void setRootSize(Integer rootSize) {
        this.rootSize = rootSize;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public VmwareKeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(VmwareKeyPair keyPair) {
        this.keyPair = keyPair;
    }

}
