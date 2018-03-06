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

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primecloudcontroller.admin.exception.ApplicationException;
import org.primecloudcontroller.admin.model.ApiCertificate;
import org.primecloudcontroller.admin.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiCertificateService extends AbstractService {

    public ApiCertificate findOne(Long userNo) {
        ApiCertificate apiCertificate = apiCertificateRepository.findOne(userNo);

        if (apiCertificate == null) {
            return null;
        }

        return apiCertificate;
    }

    public ApiCertificate create(String userNoStr, String apiAccessId, String apiSecretKey) {
        apiAccessId = StringUtils.trim(apiAccessId);
        apiSecretKey = StringUtils.trim(apiSecretKey);

        ApiCertificate apiCertificate = new ApiCertificate();

        // UserNo
        {
            Long userNo;
            try {
                userNo = Long.valueOf(userNoStr);
            } catch (NumberFormatException e) {
                throw new ApplicationException("message.apiCertificate.illegalUserNo");
            }

            User user = userRepository.findOne(userNo);
            if (user == null) {
                throw new ApplicationException("message.apiCertificate.notExistUserNo");
            }

            boolean exists = apiCertificateRepository.exists(userNo);
            if (exists) {
                throw new ApplicationException("message.apiCertificate.existUserNo");
            }

            apiCertificate.setUserNo(userNo);
        }

        // ApiAccessId
        {
            int length = StringUtils.length(apiAccessId);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.apiCertificate.illegalApiAccessId");
            }

            boolean exists = apiCertificateRepository.existsByApiAccessId(apiAccessId);
            if (exists) {
                throw new ApplicationException("message.apiCertificate.existApiAccessId");
            }

            apiCertificate.setApiAccessId(apiAccessId);
        }

        // ApiSecretKey
        {
            int length = StringUtils.length(apiSecretKey);
            if (length == 0 || length > 100) {
                throw new ApplicationException("message.apiCertificate.illegalApiSecretKey");
            }

            boolean exists = apiCertificateRepository.existsByApiSecretKey(apiSecretKey);
            if (exists) {
                throw new ApplicationException("message.apiCertificate.existApiSecretKey");
            }

            apiCertificate.setApiSecretKey(apiSecretKey);
        }

        apiCertificate.setEnabled(true);
        apiCertificate.setLastUseDate(null);
        apiCertificateRepository.save(apiCertificate);

        return apiCertificate;
    }

    public void delete(String userNoStr) {
        Long userNo;
        try {
            userNo = Long.valueOf(userNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.apiCertificate.illegalUserNo");
        }

        ApiCertificate apiCertificate = apiCertificateRepository.findOne(userNo);
        if (apiCertificate == null) {
            throw new ApplicationException("message.apiCertificate.notExist");
        }

        apiCertificateRepository.delete(apiCertificate);
    }

    public void enable(String userNoStr) {
        Long userNo;
        try {
            userNo = Long.valueOf(userNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.apiCertificate.illegalUserNo");
        }

        ApiCertificate apiCertificate = apiCertificateRepository.findOne(userNo);
        if (apiCertificate == null) {
            throw new ApplicationException("message.apiCertificate.notExist");
        }

        if (BooleanUtils.isTrue(apiCertificate.getEnabled())) {
            return;
        }

        apiCertificate.setEnabled(true);
        apiCertificateRepository.save(apiCertificate);
    }

    public void disable(String userNoStr) {
        Long userNo;
        try {
            userNo = Long.valueOf(userNoStr);
        } catch (NumberFormatException e) {
            throw new ApplicationException("message.apiCertificate.illegalUserNo");
        }

        ApiCertificate apiCertificate = apiCertificateRepository.findOne(userNo);
        if (apiCertificate == null) {
            throw new ApplicationException("message.apiCertificate.notExist");
        }

        if (BooleanUtils.isFalse(apiCertificate.getEnabled())) {
            return;
        }

        apiCertificate.setEnabled(false);
        apiCertificateRepository.save(apiCertificate);
    }

}
