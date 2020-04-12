package org.personal.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.personal.aop.annotations.ActionType;
import org.personal.aop.annotations.EmployeeAction;
import org.personal.data.employee.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeAspect extends AbstractAspect
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.RequestMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)")
    private void endpointList() {}

    @Pointcut("@annotation(employeeAction)")
    private void isEmployeeActions(EmployeeAction employeeAction) {}

    @Pointcut("execution(* org.personal.controller.EmployeeController.*(..))")
    private void isEmployeeController() {}

    @Before("isEmployeeController() && isEmployeeActions(employeeAction) && endpointList()")
    public void checkEmployeeAction(final JoinPoint joinPoint, final EmployeeAction employeeAction) {
        if (employeeAction.ACTION_TYPE() == ActionType.SELECT || employeeAction.ACTION_TYPE() == ActionType.DELETE) {
            Integer employeeID = getParameterValue(joinPoint.getSignature(), joinPoint.getArgs(), employeeAction.id(), Integer.class);
            logger.info("Select or Delete on employee id = {}", employeeID);
        } else {
            Employee employee = getParameterValue(joinPoint.getSignature(), joinPoint.getArgs(), employeeAction.employee(), Employee.class);
            logger.info("Create or Update on employee id = {}", employee);
        }
    }

}
