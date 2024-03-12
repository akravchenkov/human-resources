package ru.human.resources.common.data.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.human.resources.common.data.BaseData;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCredentials extends BaseData {

    private static final long serialVersionUID = -585297467674826822L;

    private Long id;
    private boolean enabled;
    private String password;
    private String activateToken;
    private String resetToken;
}
