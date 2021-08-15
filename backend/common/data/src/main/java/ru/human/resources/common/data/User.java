package ru.human.resources.common.data;

import java.io.Serializable;
import lombok.Data;
import ru.human.resources.common.data.security.Authority;

/**
 * @author Anton Kravchenkov
 * @since 06.08.2021
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -445515076557193596L;

    private String userId;
    private String employeeId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Authority authority;

    public User() {
    }

    public User(String userId, String email, String firstName, String lastName,
        String password) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
