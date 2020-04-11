package org.personal.system.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * #错误解决#
 * 方法1, 直接使用ControllerAdvice, 在 @ControllerAdvice 类里面写 @ExceptionHandler
 * 注意：如果配置了 SimpleMappingExceptionResolver, 那么这个 SystemExceptionHandler 就完全不起作用了
 */
@ControllerAdvice
public class SystemExceptionHandler
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ NumberFormatException.class })
    public ModelAndView handleNumberFormatException(final NumberFormatException ex)
    {
        logger.info("SystemExceptionHandler::handleNumberFormatException, exception = {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("ex", ex);
        return modelAndView;
    }

    @ExceptionHandler({ RuntimeException.class })
    public ModelAndView handleRuntimeException(final RuntimeException ex)
    {
        logger.info("ExceptionController::handleRuntimeException, exception = {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("ex", ex);
        return modelAndView;
    }
}
