package org.personal.controller;

import org.personal.data.employee.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * When data send from frontend(submit a form, for example) to backend(an endpoint in a controller accept the data),
 * there is a ConversionService, which could call a customize converter to change it
 *
 *
 * Steps:
 * 1, Create a converter (org.personal.data.employee.converters.EmployeeConverter)
 * 2, In org.personal.WebMvcConfig class, implement in addFormatters function, for example:
 *
 * ***********************************************************************************************************
 *     @Autowired
 *     private EmployeeConverter employeeConverter;
 *
 *     @Override
 *     public void addFormatters(FormatterRegistry registry)
 *     {
 *         registry.addConverter(employeeConverter);
 *     }
 * ***********************************************************************************************************
 */
@RequestMapping("convert")
@Controller
public class ConverterDemoController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "convert";

    @GetMapping
    public String index()
    {
        logger.info("ConverterDemoController::index");
        return BASE_URL;
    }

    /**
     * Create a custom converter to change from String to Employee
     */
    @PostMapping
    public String displayConvertObject(@RequestParam("employee") Employee employee, ModelMap modelMap)
    {
        logger.info("ConverterDemoController::displayConvertObject, employee = {}", employee);
        modelMap.addAttribute("employee", employee);
        return BASE_URL;
    }
}
