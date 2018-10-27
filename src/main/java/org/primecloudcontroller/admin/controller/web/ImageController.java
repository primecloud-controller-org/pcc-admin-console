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

import org.primecloudcontroller.admin.model.Image;
import org.primecloudcontroller.admin.model.Platform;
import org.primecloudcontroller.admin.service.ImageService;
import org.primecloudcontroller.admin.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImageController extends AbstractWebController {

    @Autowired
    protected ImageService imageService;

    @Autowired
    protected PlatformService platformService;

    @RequestMapping(path = "/image", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Image> images = imageService.findAll();

        ModelMap model = new ModelMap();
        model.addAttribute("images", images);

        return new ModelAndView("image/list", model);
    }

    @RequestMapping(path = "/image/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(name = "imageNo", required = false) String imageNo) {
        ModelMap model = new ModelMap();

        if (toLong(imageNo) == null) {
            model.addAttribute("message", "not_selected");
            return new ModelAndView("redirect:/image", model);
        }

        Image image = imageService.findOne(toLong(imageNo));

        if (image == null) {
            model.addAttribute("message", "not_exist");
            return new ModelAndView("redirect:/image", model);
        }

        model.addAttribute("image", image);

        List<Platform> platforms = platformService.findAll();
        model.addAttribute("platforms", platforms);

        return new ModelAndView("image/show", model);
    }

    @RequestMapping(path = "/image/form", method = RequestMethod.GET)
    public ModelAndView form(@RequestParam(name = "imageNo", required = false) String imageNo,
            @RequestParam(name = "copyImageNo", required = false) String copyImageNo) {
        ModelMap model = new ModelMap();

        // Edit
        if (toLong(imageNo) != null) {
            Image image = imageService.findOne(toLong(imageNo));

            if (image == null) {
                model.addAttribute("message", "not_exist");
                return new ModelAndView("redirect:/image", model);
            }

            model.addAttribute("image", image);
        }
        // Copy
        else if (toLong(copyImageNo) != null) {
            Image image = imageService.findOne(toLong(copyImageNo));

            if (image == null) {
                model.addAttribute("message", "not_exist");
                return new ModelAndView("redirect:/image", model);
            }

            model.addAttribute("image", image);
            model.addAttribute("mode", "copy");
        }

        List<Platform> platforms = platformService.findAll();
        model.addAttribute("platforms", platforms);

        return new ModelAndView("image/form", model);
    }

}
