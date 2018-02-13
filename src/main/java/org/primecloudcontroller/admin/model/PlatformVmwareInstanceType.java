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
@Table(name = "PLATFORM_VMWARE_INSTANCE_TYPE")
public class PlatformVmwareInstanceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "INSTANCE_TYPE_NO")
    private Long instanceTypeNo;

    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "INSTANCE_TYPE_NAME")
    private String instanceTypeName;

    @Column(name = "CPU")
    private Integer cpu;

    @Column(name = "MEMORY")
    private Long memory;

    public Long getInstanceTypeNo() {
        return instanceTypeNo;
    }

    public void setInstanceTypeNo(Long instanceTypeNo) {
        this.instanceTypeNo = instanceTypeNo;
    }

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getInstanceTypeName() {
        return instanceTypeName;
    }

    public void setInstanceTypeName(String instanceTypeName) {
        this.instanceTypeName = instanceTypeName;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

}
