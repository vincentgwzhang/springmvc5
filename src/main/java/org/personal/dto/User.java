package org.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    private int id;
    private String username;
    private String password;
    private String email;
    private int age;
    private Address address;

    public User(int id, String username, String password, String email, int age)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }
}
