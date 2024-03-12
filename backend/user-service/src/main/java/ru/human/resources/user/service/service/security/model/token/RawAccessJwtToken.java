package ru.human.resources.user.service.service.security.model.token;

import ru.human.resources.common.data.security.model.JwtToken;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
public class RawAccessJwtToken implements JwtToken {

    private static final long serialVersionUID = -1920723638295886404L;

    private final String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
