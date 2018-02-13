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

import org.primecloudcontroller.admin.model.Farm;
import org.primecloudcontroller.admin.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FarmController extends AbstractWebController {

    @Autowired
    protected FarmService farmService;

    @RequestMapping(path = "/farm", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Farm> farms = farmService.findAll();

        ModelMap model = new ModelMap();
        model.addAttribute("farms", farms);

        return new ModelAndView("farm/list", model);
    }

    @RequestMapping(path = "/farm/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(name = "farmNo", required = false) String farmNo) {
        ModelMap model = new ModelMap();

        if (toLong(farmNo) == null) {
            model.addAttribute("message", "not_selected");
            return new ModelAndView("redirect:/farm", model);
        }

        Farm farm = farmService.findOne(toLong(farmNo));

        if (farm == null) {
            model.addAttribute("message", "not_exist");
            return new ModelAndView("redirect:/farm", model);
        }

        model.addAttribute("farm", farm);

        return new ModelAndView("farm/show", model);
    }

}
