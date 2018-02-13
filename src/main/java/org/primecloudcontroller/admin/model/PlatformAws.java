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
@Table(name = "PLATFORM_AWS")
public class PlatformAws implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "HOST")
    private String host;

    @Column(name = "PORT")
    private Integer port;

    @Column(name = "SECURE")
    private Boolean secure;

    @Column(name = "EUCA")
    private Boolean euca;

    @Column(name = "VPC")
    private Boolean vpc;

    @Column(name = "REGION")
    private String region;

    @Column(name = "AVAILABILITY_ZONE")
    private String availabilityZone;

    @Column(name = "VPC_ID")
    private String vpcId;

    @Column(name = "SUBNET_ID")
    private String subnetId;

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean getEuca() {
        return euca;
    }

    public void setEuca(Boolean euca) {
        this.euca = euca;
    }

    public Boolean getVpc() {
        return vpc;
    }

    public void setVpc(Boolean vpc) {
        this.vpc = vpc;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

}
