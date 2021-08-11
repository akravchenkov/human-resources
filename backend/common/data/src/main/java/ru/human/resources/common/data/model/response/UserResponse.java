package ru.human.resources.common.data.model.response;

import lombok.Data;
import ru.human.resources.common.data.security.Authority;

/**
 * @author Anton Kravchenkov
 * @since 07.08.2021
 */
@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private Authority authority;
}
