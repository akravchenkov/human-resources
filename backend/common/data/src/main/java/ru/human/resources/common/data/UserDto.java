package ru.human.resources.common.data;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 06.08.2021
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -445515076557193596L;

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public UserDto() {
    }

    public UserDto(String userId, String email, String firstName, String lastName,
        String password) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
