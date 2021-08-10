package ru.human.resources.common.data.model.request;

import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 06.08.2021
 */
@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
