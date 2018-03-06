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

import org.primecloudcontroller.admin.model.ApiCertificate;
import org.primecloudcontroller.admin.service.ApiCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiCertificateController extends AbstractRestController {

    @Autowired
    protected ApiCertificateService apiCertificateService;

    @RequestMapping(path = "/rest/apiCertificate/create", method = RequestMethod.POST)
    public RestResponse create(@RequestParam(name = "userNo", required = false) String userNo,
            @RequestParam(name = "apiAccessId", required = false) String apiAccessId,
            @RequestParam(name = "apiSecretKey", required = false) String apiSecretKey) {
        ApiCertificate apiCertificate = apiCertificateService.create(userNo, apiAccessId, apiSecretKey);

        return new RestResponse(apiCertificate);
    }

    @RequestMapping(path = "/rest/apiCertificate/delete", method = RequestMethod.POST)
    public RestResponse delete(@RequestParam(name = "userNo", required = false) String userNo) {
        apiCertificateService.delete(userNo);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/apiCertificate/enable", method = RequestMethod.POST)
    public RestResponse enable(@RequestParam(name = "userNo", required = false) String userNo) {
        apiCertificateService.enable(userNo);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/apiCertificate/disable", method = RequestMethod.POST)
    public RestResponse disable(@RequestParam(name = "userNo", required = false) String userNo) {
        apiCertificateService.disable(userNo);

        return new RestResponse(Boolean.TRUE);
    }

}
