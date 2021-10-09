package ru.human.resources.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.human.resources.common.data.security.Authority;

/**
 * @author Anton Kravchenkov
 * @since 06.08.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends SearchTextBasedWithAdditionalInfo implements HasName {

    private static final long serialVersionUID = -445515076557193596L;

    private Long id;
    private String userId;
    private String employeeId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Authority authority;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.employeeId = user.getEmployeeId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authority = user.getAuthority();
    }

    public User(String userId, String email, String firstName, String lastName,
        String password) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    @Override
    @JsonProperty(access = Access.READ_ONLY)
    public String getName() {
        return email;
    }
}
