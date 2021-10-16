package ru.human.resources.user.service.service.security.exception;

import org.springframework.security.core.AuthenticationException;
import ru.human.resources.common.data.security.model.JwtToken;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
public class JwtExpiredTokenException extends AuthenticationException {

    private static final long serialVersionUID = 577761806674273746L;

    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
