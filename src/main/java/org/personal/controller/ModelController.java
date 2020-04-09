package org.personal.controller;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("model")
public class ModelController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "model";

    @GetMapping
    public String index() {
        return BASE_URL;
    }

    @GetMapping("modelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView modelAndView = new ModelAndView(BASE_URL);
        modelAndView.addObject("time", new Date());
        logger.info("ModelController::testModelAndView");
        return modelAndView;
    }

    @GetMapping("map")
    public String testMap(Map<String, Object> map) {
        map.put("time", new Date());
        logger.info("ModelController::testMap");
        return BASE_URL;
    }

    @GetMapping("modelMap")
    public String testModelMap(ModelMap modelMap) {
        modelMap.put("time", new Date());
        logger.info("ModelController::testModelMap");
        return BASE_URL;
    }

    @GetMapping("model")
    public String testModel(Model model) {
        model.addAttribute("time", new Date());
        logger.info("ModelController::testModel");
        return BASE_URL;
    }
}
