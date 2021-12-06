package org.personal.controller;

import java.util.HashMap;
import java.util.Map;
import org.personal.aop.annotations.ActionType;
import org.personal.aop.annotations.EmployeeAction;
import org.personal.data.employee.dao.DepartmentDao;
import org.personal.data.employee.dao.EmployeeDao;
import org.personal.data.employee.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("employee")
@Controller
public class EmployeeController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_PATH = "employee";

    private static final String INSERT_PAGE = BASE_PATH + "/input";

    private static final String LIST_PAGE = BASE_PATH + "/list";

    private static final String INDEX_PAGE = BASE_PATH + "/index";

    private static final String REDIRECT_GET = "redirect:/employee/get";

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @ModelAttribute
    public void getEmployeeModel(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map)
    {
        if (id != null)
        {
            map.put("employee", employeeDao.get(id));
        }
    }

    @ModelAttribute
    public void getDepartments(Map<String, Object> map)
    {
        map.put("departments", departmentDao.getDepartments());
    }

    @ModelAttribute
    public void getGenders(Map<String, Object> map)
    {
        Map<String, String> genders = new HashMap<>();
        genders.put("1", "Male");
        genders.put("0", "Female");
        map.put("genders", genders);
    }

    /**
     * Redirect to id edit page
     */
    @GetMapping(value = "{id}")
    @EmployeeAction(ACTION_TYPE = ActionType.SELECT)
    public String getEmployee(@PathVariable("id") Integer id, Map<String, Object> map)
    {
        logger.info("EmployeeController::getEmployee");
        map.put("employee", employeeDao.get(id));
        return INSERT_PAGE;
    }

    @DeleteMapping(value = "/{id}")
    @EmployeeAction(ACTION_TYPE = ActionType.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id)
    {
        logger.info("EmployeeController::deleteEmployee, deleteEmployee = {}", id);
        employeeDao.delete(id);
        return REDIRECT_GET;
    }

    @PostMapping("update")
    @EmployeeAction(ACTION_TYPE = ActionType.UPDATE)
    public String updateEmployee(@Valid Employee employee, Errors result, Map<String, Object> map)
    {
        logger.info("EmployeeController::updateEmployee employee = {}", employee);

        if (result.getErrorCount() > 0)
        {
            logger.warn("EmployeeController::save, submit form has error");
            for (FieldError error : result.getFieldErrors())
            {
                logger.warn("EmployeeController::save, error field = {}, error message = {}", error.getField(), error.getDefaultMessage());
            }
            return INSERT_PAGE;
        }

        employeeDao.save(employee);
        return REDIRECT_GET;
    }

    /**
     * Create employee page action
     */
    @PostMapping(value = "save")
    @EmployeeAction(ACTION_TYPE = ActionType.CREATE)
    public String saveEmployee(@Valid Employee employee, Errors result, Map<String, Object> map)
    {
        logger.info("EmployeeController::saveEmployee, employee = {}", employee);

        if (result.getErrorCount() > 0)
        {
            logger.warn("EmployeeController::save, submit form has error");
            for (FieldError error : result.getFieldErrors())
            {
                logger.warn("EmployeeController::save, error field = {}, error message = {}", error.getField(), error.getDefaultMessage());
            }
            return INSERT_PAGE;
        }

        employeeDao.save(employee);
        return REDIRECT_GET;
    }

    /**
     * Redirect to create jsp page
     */
    @GetMapping(value = "/create")
    public String createEmployee(Map<String, Object> map)
    {
        logger.info("EmployeeController::input");
        map.put("employee", new Employee());
        return INSERT_PAGE;
    }

    /**
     * Redirect to employees page
     */
    @GetMapping("/get")
    public String listEmployees(Map<String, Object> map)
    {
        logger.info("EmployeeController::list");
        map.put("employees", employeeDao.getAll());
        return LIST_PAGE;
    }

    /**
     * Redirect to total page
     */
    @GetMapping
    public String index()
    {
        return INDEX_PAGE;
    }

    //	@InitBinder
    //	public void initBinder(WebDataBinder binder){
    //		binder.setDisallowedFields("lastName");
    //	}

}
