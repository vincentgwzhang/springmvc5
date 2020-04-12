package org.personal.controller;

import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("home")
public class HomeController
{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "home";

    @GetMapping({ "", "default", "link" })
    public String defaultMethod(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, Principal principal, Locale locale, Reader reader, Writer writer)
    {
        logger.info("Home Controller access");
        logger.info("request class = {}, response class = {}", request.getClass().getName(), response.getClass().getName());
        logger.info("session class = {}, locale language = {}", session.getClass().getName(), locale);
        logger.info("reader class = {}, writer class = {}", reader.getClass().getName(), writer.getClass().getName());

        model.addAttribute("message", "This is Java configuration case sample");
        return BASE_URL;
    }

    /**
     *Without params = {"username", "age!=10"} can not claim that age != 10
     */
    @GetMapping(value = "parametertest", params = { "username", "age!=10" })
    public String testRequestParams(ModelMap model, @RequestParam("username") String username, @RequestParam("age") int age)
    {
        logger.info("Home Controller username = {}, age = {}", username, age);
        model.addAttribute("message", "This is Java configuration case sample");
        return BASE_URL;
    }

    @GetMapping(value = "parametertest2")
    public String testRequestParams2(ModelMap model, @RequestParam(value = "username", required = false, defaultValue = "defaultName") String username, @RequestParam(value = "age", required = false, defaultValue = "0") Integer age)
    {
        logger.info("Home Controller username = {}, age = {}", username, age);
        model.addAttribute("message", "This is Java configuration case sample");
        return BASE_URL;
    }

    @GetMapping(value = "headertest")
    public String testHeader(ModelMap model, @RequestHeader(value = "accept-language", required = false) String headerValue)
    {
        logger.info("HomeController::testHeader, accept-language={}", headerValue);
        model.addAttribute("message", "This is Java configuration case sample");
        return BASE_URL;
    }

    @GetMapping("/requestMapping/*/regx")
    public String testRequestRegexStyle()
    {
        logger.info("testRequestRegexStyle access");
        return BASE_URL;
    }

    @GetMapping("/path/{username}/age/{age}")
    public String testPathVariable(@PathVariable String username, @PathVariable String age)
    {
        logger.info("HomeController::testPathVariable username = {}, age = {}", username, age);
        return BASE_URL;
    }

    @GetMapping("/cookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID", required = false) String cookieValue)
    {
        logger.info("HomeController::testCookieValue cookieValue = {}", cookieValue);
        return BASE_URL;
    }

}
