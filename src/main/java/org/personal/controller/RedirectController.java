package org.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("redirect")
public class RedirectController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("testRedirect")
    public String testRedirect(final ModelMap modelMap) {
        modelMap.addAttribute("redirectObject", "From testRedirect"); // 最终不再存在
        logger.info("RedirectController::testRedirect");
        return "redirect:/redirect/commonView";//注意路径, redirect, 是浏览器redirect
    }

    @GetMapping("commonView")
    public String commonView() {
        return "redirect";
    }

    @GetMapping("testForward")
    public String testForward(final ModelMap modelMap) {
        modelMap.addAttribute("testForward", "From testForward"); // 最终会被记录下来
        logger.info("RedirectController::testForward");
        return "forward:/redirect/commonView";//注意路径, forward, 是服务器forward
    }

}
