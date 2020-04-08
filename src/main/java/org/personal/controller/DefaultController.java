package org.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController
{
    @GetMapping
    public String defaultMethod() {
        return "index";
    }
}
