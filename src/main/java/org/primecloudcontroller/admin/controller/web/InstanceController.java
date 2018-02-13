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

import org.primecloudcontroller.admin.model.Instance;
import org.primecloudcontroller.admin.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstanceController extends AbstractWebController {

    @Autowired
    protected InstanceService instanceService;

    @RequestMapping(path = "/instance", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Instance> instances = instanceService.findAll();

        ModelMap model = new ModelMap();
        model.addAttribute("instances", instances);

        return new ModelAndView("instance/list", model);
    }

    @RequestMapping(path = "/instance/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(name = "instanceNo", required = false) String instanceNo) {
        ModelMap model = new ModelMap();

        if (toLong(instanceNo) == null) {
            model.addAttribute("message", "not_selected");
            return new ModelAndView("redirect:/instance", model);
        }

        Instance instance = instanceService.findOne(toLong(instanceNo));

        if (instance == null) {
            model.addAttribute("message", "not_exist");
            return new ModelAndView("redirect:/instance", model);
        }

        model.addAttribute("instance", instance);

        return new ModelAndView("instance/show", model);
    }

}
