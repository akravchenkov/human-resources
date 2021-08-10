package ru.human.resources.user.service.service.security.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 10.08.2021
 */
@Data
public class UserPrincipal implements Serializable {

    private static final long serialVersionUID = 4203338692699537713L;

    private final Type type;
    private final String value;

    public enum Type {
        USER_NAME,
        PUBLIC_ID
    }
}
