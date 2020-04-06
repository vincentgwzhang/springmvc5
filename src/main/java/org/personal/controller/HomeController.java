package org.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        logger.info("Home Controller access");
        model.addAttribute("message", "This is Java configuration case sample");
        return "index";
    }

}
