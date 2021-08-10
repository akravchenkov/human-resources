package ru.human.resources.common.data.model.request;

import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 08.08.2021
 */
@Data
public class LoginRequest {

    private String email;
    private String password;
}
