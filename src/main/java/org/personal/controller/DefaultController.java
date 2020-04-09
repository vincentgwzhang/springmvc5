package org.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class DefaultController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public String defaultMethod()
    {
        return "index";
    }

    @GetMapping("rest")
    public String restfulPage()
    {
        return "rest";
    }

    @PutMapping("rest/postformtest")
    public String restfulPostPage()
    {
        logger.info("DefaultController::restfulPostPage");
        return "redirect:/rest";//This is equal to value in @GetMapping("rest")
    }
}
