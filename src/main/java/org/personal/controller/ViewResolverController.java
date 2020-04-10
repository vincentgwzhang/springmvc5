package org.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view")
public class ViewResolverController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "view";

    @GetMapping
    public String index()
    {
        logger.info("ViewResolverController::index");
        return BASE_URL;
    }

    @GetMapping("helloView")
    public String helloView() {
        logger.info("ViewResolverController::helloView");
        return "helloView";
    }

}
