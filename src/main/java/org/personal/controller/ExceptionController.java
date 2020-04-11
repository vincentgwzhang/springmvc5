package org.personal.controller;

import org.personal.system.exception.SimpleMappingExceptionResolverDefaultException;
import org.personal.system.exception.StudentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * #错误解决#
 * 方法1, 直接使用ExceptionHandler, 注意，用 handleArithmeticException 的时候不能传入 ModelMap
 */
@RequestMapping("exception")
@Controller
public class ExceptionController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "exception";

    @GetMapping
    public String index()
    {
        logger.info("ExceptionController::index");
        return BASE_URL;
    }

    @ExceptionHandler({ ArithmeticException.class })
    public ModelAndView handleArithmeticException(final Exception ex)
    {
        logger.info("ExceptionController::handleArithmeticException, exception = {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("ex", ex);
        return modelAndView;
    }

    @GetMapping("internalTriggerNonJSONException")
    public String internalTriggerNonJSONException()
    {
        int errorResult = 10 / 0;
        return BASE_URL;
    }

    @GetMapping("internalTriggerNumberFormatException")
    public String internalTriggerNumberFormatException()
    {
        logger.info("ExceptionController::internalTriggerNumberFormatException");
        boolean flag = true;
        if (flag)
            throw new NumberFormatException();
        return BASE_URL;
    }

    @GetMapping("triggerStudentNotFoundException")
    public String triggerStudentNotFoundException()
    {
        logger.info("ExceptionController::triggerStudentNotFoundException");
        boolean flag = true;
        if (flag)
            throw new StudentNotFoundException();
        return BASE_URL;
    }

    @GetMapping("triggerArrayIndexOutOfBoundsException")
    public String triggerArrayIndexOutOfBoundsException()
    {
        logger.info("ExceptionController::triggerArrayIndexOutOfBoundsException");
        String[] args = new String[10];
        System.out.println(args[21]);
        return BASE_URL;
    }

    @GetMapping("triggerSimpleMappingExceptionResolverDefaultErrorView")
    public String triggerSimpleMappingExceptionResolverDefaultErrorView()
    {
        logger.info("ExceptionController::triggerSimpleMappingExceptionResolverDefaultErrorView");
        boolean flag = true;
        if (flag)
            throw new SimpleMappingExceptionResolverDefaultException();
        return BASE_URL;
    }
}
