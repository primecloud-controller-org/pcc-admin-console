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
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "AWS_CERTIFICATE")
@IdClass(AwsCertificatePK.class)
public class AwsCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_NO")
    private Long userNo;

    @Id
    @Column(name = "PLATFORM_NO")
    private Long platformNo;

    @Column(name = "AWS_ACCESS_ID")
    private String awsAccessId;

    @Column(name = "AWS_SECRET_KEY")
    private String awsSecretKey;

    @Column(name = "DEF_KEYPAIR")
    private String defKeypair;

    @Column(name = "DEF_SUBNET")
    private String defSubnet;

    @Column(name = "DEF_LB_SUBNET")
    private String defLbSubnet;

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Long getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    public String getAwsAccessId() {
        return awsAccessId;
    }

    public void setAwsAccessId(String awsAccessId) {
        this.awsAccessId = awsAccessId;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public String getDefKeypair() {
        return defKeypair;
    }

    public void setDefKeypair(String defKeypair) {
        this.defKeypair = defKeypair;
    }

    public String getDefSubnet() {
        return defSubnet;
    }

    public void setDefSubnet(String defSubnet) {
        this.defSubnet = defSubnet;
    }

    public String getDefLbSubnet() {
        return defLbSubnet;
    }

    public void setDefLbSubnet(String defLbSubnet) {
        this.defLbSubnet = defLbSubnet;
    }

}
