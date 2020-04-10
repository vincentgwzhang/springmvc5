package org.personal.data.employee.converters;

import org.personal.data.employee.dao.DepartmentDao;
import org.personal.data.employee.entity.Department;
import org.personal.data.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter implements Converter<String, Employee>
{
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Employee convert(String source)
    {
        Employee employee = new Employee();

        if (source != null) {
            //lastname - email - gender - department.id
            String[] vals = source.split("-");
            String lastName = vals[0].trim();
            String email = vals[1].trim();
            int gender = Integer.parseInt(vals[2].trim());
            int departmentId = Integer.parseInt(vals[3].trim());

            Department department = departmentDao.getDepartment(departmentId);

            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setGender(gender);
            employee.setDepartment(department);
        }
        return employee;
    }
}
