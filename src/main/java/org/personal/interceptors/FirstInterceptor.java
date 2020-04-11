package org.personal.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FirstInterceptor implements HandlerInterceptor
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * Consider to do the log, transaction, authentication
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        logger.info("FirstInterceptor::preHandle");
        return true;
    }

    /**
     * Consider view rendering
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        logger.info("FirstInterceptor::postHandle");
    }

    /**
     * If one of preHandle is false, then when run afterCompletion in sequence, it will no more sequence revert execute.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        logger.info("FirstInterceptor::afterCompletion");
    }
}
