/*
 * Copyright 2019 by PrimeCloud Controller/OSS Community.
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
import javax.persistence.Embeddable;

@Embeddable
public class PlatformVmwareInstanceTypePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "INSTANCE_TYPE_NAME")
    private String instanceTypeName;

    public PlatformVmwareInstanceTypePK() {
    }

    public PlatformVmwareInstanceTypePK(Long platformNo, String instanceTypeName) {
        this.platformNo = platformNo;
        this.instanceTypeName = instanceTypeName;
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

}
