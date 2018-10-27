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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.model.PlatformVmwareInstanceType;
import org.primecloudcontroller.admin.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlatformController extends AbstractWebController {

    @Autowired
    protected PlatformService platformService;

    @RequestMapping(path = { "/platform", "/platform/list" }, method = RequestMethod.GET)
    public ModelAndView list() {
        List<Platform> platforms = platformService.findAll();

        ModelMap model = new ModelMap();
        model.addAttribute("platforms", platforms);

        return new ModelAndView("platform/list", model);
    }

    @RequestMapping(path = "/platform/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(name = "platformNo", required = false) String platformNo) {
        ModelMap model = new ModelMap();

        if (toLong(platformNo) == null) {
            model.addAttribute("message", "not_selected");
            return new ModelAndView("redirect:/platform", model);
        }

        Platform platform = platformService.findOne(toLong(platformNo));

        if (platform == null) {
            model.addAttribute("message", "not_exist");
            return new ModelAndView("redirect:/platform", model);
        }

        // Sort by cpu, memory
        if (platform.getVmware() != null) {
            Collections.sort(platform.getVmware().getInstanceTypes(), new Comparator<PlatformVmwareInstanceType>() {
                public int compare(PlatformVmwareInstanceType o1, PlatformVmwareInstanceType o2) {
                    int c = o1.getCpu().compareTo(o2.getCpu());
                    if (c != 0) {
                        return c;
                    }
                    return o1.getMemory().compareTo(o2.getMemory());
                };
            });
        }

        model.addAttribute("platform", platform);

        return new ModelAndView("platform/show", model);
    }

    @RequestMapping(path = "/platform/form", method = RequestMethod.GET)
    public ModelAndView form(@RequestParam(name = "platformNo", required = false) String platformNo,
            @RequestParam(name = "copyPlatformNo", required = false) String copyPlatformNo) {
        ModelMap model = new ModelMap();

        // Edit
        if (toLong(platformNo) != null) {
            Platform platform = platformService.findOne(toLong(platformNo));

            if (platform == null) {
                model.addAttribute("message", "not_exist");
                return new ModelAndView("redirect:/platform", model);
            }

            model.addAttribute("platform", platform);
        }
        // Copy
        else if (toLong(copyPlatformNo) != null) {
            Platform platform = platformService.findOne(toLong(copyPlatformNo));

            if (platform == null) {
                model.addAttribute("message", "not_exist");
                return new ModelAndView("redirect:/platform", model);
            }

            model.addAttribute("platform", platform);
            model.addAttribute("mode", "copy");
        }

        return new ModelAndView("platform/form", model);
    }

}
