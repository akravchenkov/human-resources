package ru.human.resources.common.data.security.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Data
public class SecuritySettings implements Serializable {

    private UserPasswordPolicy passwordPolicy;

    private Integer maxFailedLoginAttempts;
    private String userLockoutNotificationEmail;
}
