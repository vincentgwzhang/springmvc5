package org.personal.data.entity;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "students")
public class StudentEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String password;

    @Column
    private int newsletter;

    @Column
    private String framework;

    @Column
    private String sex;

    @Column
    private String NUMBER;

    @Column
    private String COUNTRY;

    @Column
    private String SKILL;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentEntity that = (StudentEntity) o;
        return id == that.id && newsletter == that.newsletter && Objects.equal(email, that.email) && Objects.equal(name, that.name) && Objects.equal(address, that.address) && Objects.equal(password, that.password) && Objects.equal(framework, that.framework) && Objects.equal(sex, that.sex) && Objects.equal(NUMBER, that.NUMBER) && Objects.equal(COUNTRY, that.COUNTRY) && Objects.equal(SKILL, that.SKILL);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id, email, name, address, password, newsletter, framework, sex, NUMBER, COUNTRY, SKILL);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("id", id).add("email", email).add("name", name).add("address", address).add("password", password).add("newsletter", newsletter).add("framework", framework).add("sex", sex).add("NUMBER", NUMBER).add("COUNTRY", COUNTRY).add("SKILL", SKILL).toString();
    }
}
