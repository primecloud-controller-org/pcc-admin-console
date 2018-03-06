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
package org.primecloudcontroller.admin.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primecloudcontroller.admin.repository.ApiCertificateRepository;
import org.primecloudcontroller.admin.repository.AwsInstanceRepository;
import org.primecloudcontroller.admin.repository.FarmRepository;
import org.primecloudcontroller.admin.repository.ImageAwsRepository;
import org.primecloudcontroller.admin.repository.ImageRepository;
import org.primecloudcontroller.admin.repository.ImageVmwareRepository;
import org.primecloudcontroller.admin.repository.InstanceRepository;
import org.primecloudcontroller.admin.repository.PlatformAwsRepository;
import org.primecloudcontroller.admin.repository.PlatformRepository;
import org.primecloudcontroller.admin.repository.PlatformVmwareInstanceTypeRepository;
import org.primecloudcontroller.admin.repository.PlatformVmwareRepository;
import org.primecloudcontroller.admin.repository.UserRepository;
import org.primecloudcontroller.admin.repository.VmwareInstanceRepository;
import org.primecloudcontroller.admin.repository.VmwareKeyPairRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected FarmRepository farmRepository;

    @Autowired
    protected InstanceRepository instanceRepository;

    @Autowired
    protected AwsInstanceRepository awsInstanceRepository;

    @Autowired
    protected VmwareInstanceRepository vmwareInstanceRepository;

    @Autowired
    protected VmwareKeyPairRepository vmwareKeyPairRepository;

    @Autowired
    protected PlatformRepository platformRepository;

    @Autowired
    protected PlatformAwsRepository platformAwsRepository;

    @Autowired
    protected PlatformVmwareRepository platformVmwareRepository;

    @Autowired
    protected PlatformVmwareInstanceTypeRepository platformVmwareInstanceTypeRepository;

    @Autowired
    protected ImageRepository imageRepository;

    @Autowired
    protected ImageAwsRepository imageAwsRepository;

    @Autowired
    protected ImageVmwareRepository imageVmwareRepository;

    @Autowired
    protected ApiCertificateRepository apiCertificateRepository;

    protected Map<String, String> trim(Map<String, String> params) {
        Map<String, String> params2 = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = StringUtils.trim(entry.getValue());
            params2.put(entry.getKey(), value);
        }
        return params2;
    }
}
