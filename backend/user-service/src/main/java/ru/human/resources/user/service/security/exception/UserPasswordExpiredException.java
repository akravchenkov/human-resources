package ru.human.resources.user.service.security.exception;

import org.springframework.security.authentication.CredentialsExpiredException;

/**
 * @author Anton Kravchenkov
 * @since 16.10.2021
 */
public class UserPasswordExpiredException extends CredentialsExpiredException {

    private static final long serialVersionUID = -5442694433011479676L;

    private final String resetToken;

    public UserPasswordExpiredException(String msg, String resetToken) {
        super(msg);
        this.resetToken = resetToken;
    }

    public String getResetToken() {
        return resetToken;
    }

}
