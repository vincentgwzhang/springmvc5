package org.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("home")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping({"", "default","link"})
    public String defaultMethod(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        logger.info("Home Controller access");
        model.addAttribute("message", "This is Java configuration case sample");
        return "home";
    }

    /**
     *Without params = {"username", "age!=10"} can not claim that age != 10
     */
    @GetMapping(value = "parametertest", params = {"username", "age!=10"})
    public String testParamsAndHeaders(ModelMap model, @RequestParam("username") String username, @RequestParam("age") int age){
        logger.info("Home Controller username = {}, age = {}", username, age);
        model.addAttribute("message", "This is Java configuration case sample");
        return "home";
    }



}
