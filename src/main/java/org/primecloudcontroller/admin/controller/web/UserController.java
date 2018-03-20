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
package org.primecloudcontroller.admin.controller.web;

import java.util.List;

import org.primecloudcontroller.admin.model.ApiCertificate;
import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.model.User;
import org.primecloudcontroller.admin.service.ApiCertificateService;
import org.primecloudcontroller.admin.service.PlatformService;
import org.primecloudcontroller.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends AbstractWebController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected ApiCertificateService apiCertificateService;

    @Autowired
    protected PlatformService platformService;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ModelAndView index() {
        List<User> users = userService.findAll();

        ModelMap model = new ModelMap();
        model.addAttribute("users", users);

        return new ModelAndView("user/list", model);
    }

    @RequestMapping(path = "/user/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(name = "userNo", required = false) String userNo) {
        ModelMap model = new ModelMap();

        if (toLong(userNo) == null) {
            model.addAttribute("message", "not_selected");
            return new ModelAndView("redirect:/user", model);
        }

        User user = userService.findOne(toLong(userNo));

        if (user == null) {
            model.addAttribute("message", "not_exist");
            return new ModelAndView("redirect:/user", model);
        }

        model.addAttribute("user", user);

        // ApiCertificate
        ApiCertificate apiCertificate = apiCertificateService.findOne(user.getUserNo());
        model.addAttribute("apiCertificate", apiCertificate);

        // AvailablePlatforms
        List<Platform> availablePlatforms = platformService.findAvailables(user.getUserNo());
        model.addAttribute("availablePlatforms", availablePlatforms);

        return new ModelAndView("user/show", model);
    }

}
