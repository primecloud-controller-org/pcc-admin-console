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
package org.primecloudcontroller.admin.controller.rest;

import java.util.Map;

import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.model.PlatformVmwareInstanceType;
import org.primecloudcontroller.admin.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPlatformController extends AbstractRestController {

    @Autowired
    protected PlatformService platformService;

    @RequestMapping(path = "/rest/platform/get", method = RequestMethod.GET)
    public RestResponse get(@RequestParam(name = "platformNo", required = false) String platformNo) {
        Platform platform = platformService.findOne(toLong(platformNo));

        return new RestResponse(platform);
    }

    @RequestMapping(path = "/rest/platform/saveAws", method = RequestMethod.POST)
    public RestResponse saveAws(@RequestParam Map<String, String> params) {
        Platform platform = platformService.saveAws(params);

        return new RestResponse(platform);
    }

    @RequestMapping(path = "/rest/platform/saveVmware", method = RequestMethod.POST)
    public RestResponse saveVmware(@RequestParam Map<String, String> params) {
        Platform platform = platformService.saveVmware(params);

        return new RestResponse(platform);
    }

    @RequestMapping(path = "/rest/platform/checkRemove", method = RequestMethod.GET)
    public RestResponse checkRemove(@RequestParam(name = "platformNo", required = false) String platformNo) {
        platformService.checkRemove(platformNo);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/platform/remove", method = RequestMethod.POST)
    public RestResponse remove(@RequestParam(name = "platformNo", required = false) String platformNo) {
        platformService.remove(platformNo);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/platform/addVmwareInstanceType", method = RequestMethod.POST)
    public RestResponse addVmwareInstanceType(@RequestParam Map<String, String> params) {
        PlatformVmwareInstanceType instanceType = platformService.addVmwareInstanceType(params);

        return new RestResponse(instanceType);
    }

    @RequestMapping(path = "/rest/platform/checkRemoveVmareInstanceType", method = RequestMethod.GET)
    public RestResponse checkRemoveVmwareInstanceType(
            @RequestParam(name = "platformNo", required = false) String platformNo,
            @RequestParam(name = "instanceTypeName", required = false) String instanceTypeName) {
        platformService.checkRemoveVmwareInstanceType(platformNo, instanceTypeName);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/platform/removeVmareInstanceType", method = RequestMethod.POST)
    public RestResponse removeVmwareInstanceType(@RequestParam(name = "platformNo", required = false) String platformNo,
            @RequestParam(name = "instanceTypeName", required = false) String instanceTypeName) {
        platformService.removeVmwareInstanceType(platformNo, instanceTypeName);

        return new RestResponse(Boolean.TRUE);
    }

}
