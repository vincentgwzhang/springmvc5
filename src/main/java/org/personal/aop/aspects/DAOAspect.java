package org.personal.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DAOAspect
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("within(org.personal.data.employee.dao.*Dao)")
    private void findAllDBDAO() {}

    @Pointcut("@annotation(org.personal.aop.annotations.DBOperation)")
    private void findAllDBOpertion() {}

    @Before("findAllDBDAO() && findAllDBOpertion()")
    public void logDBOperation(final JoinPoint joinPoint) {
        final String className = joinPoint.getSignature().getDeclaringTypeName();
        final String functionName = joinPoint.getSignature().getName();
        logger.info("Execute DAO class = {}, function name = {}", className, functionName);
    }

    @Around("findAllDBDAO() && findAllDBOpertion()")
    public Object checkSpendTime(final ProceedingJoinPoint joinPoint) throws Throwable
    {
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally
        {
            long stopTime = System.nanoTime();
            final String className = joinPoint.getSignature().getDeclaringTypeName();
            final String functionName = joinPoint.getSignature().getName();

            double SpendTime = (double) (stopTime - startTime) / 1_000_000_000;
            logger.info("{}.{}, spend time {}s", className, functionName, SpendTime);
        }

    }
}
