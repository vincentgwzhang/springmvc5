package org.personal.data.employee.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee
{

    private Integer id;

    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    //1 male, 0 female
    //写 customize message 的时候，要遵从 NotNull.employee.gender 这样的格式
    @NotNull
    private Integer gender;

    private Department department;

    public Employee(Integer id, String lastName, String email, Integer gender, Department department)
    {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.department = department;
    }

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NumberFormat(pattern = "#,###,###.#")
    private Float salary;
}
