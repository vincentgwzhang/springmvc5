package org.personal.controller;

import org.personal.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Getter;

@Controller
@RequestMapping("user")
public class UserController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "user";

    @GetMapping
    public String index() {
        return BASE_URL;
    }

    /**
     * 由 form 表格传进来的话，不能够使用 @RequestBody
     */
    @PostMapping("/post")
    public String testUser(final User user, final Model model) {
        logger.info("UserController::testUser user = {}", user);
        model.addAttribute("userAttribute", user);
        return BASE_URL;
    }
}
