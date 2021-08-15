package ru.human.resources.user.service.service.security.auth;

import ru.human.resources.user.service.service.security.AbstractJwtAuthenticationToken;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.token.RawAccessJwtToken;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
public class RefreshAuthenticationToken extends AbstractJwtAuthenticationToken {

    private static final long serialVersionUID = 9162889507885055086L;

    public RefreshAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public RefreshAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
