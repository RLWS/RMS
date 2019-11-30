package com.rlws.rms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author rlws
 * @date 2019/11/28  11:38
 */

@Controller
public class IndexController {

    @RequestMapping(value = "index")
    public String index(ModelAndView modelAndView) {
        modelAndView.addObject("name","test");
        return "index";
    }
}
