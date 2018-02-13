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

import org.primecloudcontroller.admin.model.Image;
import org.primecloudcontroller.admin.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestImageController extends AbstractRestController {

    @Autowired
    protected ImageService imageService;

    @RequestMapping(path = "/rest/image/get", method = RequestMethod.GET)
    public RestResponse get(@RequestParam(name = "imageNo", required = false) String imageNo) {
        Image image = imageService.findOne(toLong(imageNo));

        return new RestResponse(image);
    }

    @RequestMapping(path = "/rest/image/saveAws", method = RequestMethod.POST)
    public RestResponse saveAws(@RequestParam Map<String, String> params) {
        Image image = imageService.saveAws(params);

        return new RestResponse(image);
    }

    @RequestMapping(path = "/rest/image/saveVmware", method = RequestMethod.POST)
    public RestResponse saveVmware(@RequestParam Map<String, String> params) {
        Image image = imageService.saveVmware(params);

        return new RestResponse(image);
    }

    @RequestMapping(path = "/rest/image/checkRemove", method = RequestMethod.GET)
    public RestResponse checkRemove(@RequestParam(name = "imageNo", required = false) String imageNo) {
        imageService.checkRemove(imageNo);

        return new RestResponse(Boolean.TRUE);
    }

    @RequestMapping(path = "/rest/image/remove", method = RequestMethod.POST)
    public RestResponse remove(@RequestParam(name = "imageNo", required = false) String imageNo) {
        imageService.remove(imageNo);

        return new RestResponse(Boolean.TRUE);
    }

}
