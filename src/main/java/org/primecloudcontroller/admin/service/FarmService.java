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
import java.util.List;
import java.util.Map;

import org.primecloudcontroller.admin.model.Farm;
import org.primecloudcontroller.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FarmService extends AbstractService {

    @Autowired
    protected UserService userService;

    public List<Farm> findAll() {
        List<Farm> farms = farmRepository.findAll();

        {
            List<User> users = userService.findAll();
            Map<Long, User> userMap = new LinkedHashMap<Long, User>();
            for (User user : users) {
                userMap.put(user.getUserNo(), user);
            }

            for (Farm farm : farms) {
                farm.setUser(userMap.get(farm.getUserNo()));
            }
        }

        return farms;
    }

    public Farm findOne(Long farmNo) {
        Farm farm = farmRepository.findOne(farmNo);

        if (farm == null) {
            return null;
        }

        User user = userService.findOne(farm.getUserNo());
        farm.setUser(user);

        return farm;
    }

}
