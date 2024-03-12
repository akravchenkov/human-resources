package ru.human.resources.user.service.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.token.RawAccessJwtToken;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
public class AbstractJwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 6100787820694648150L;

    private RawAccessJwtToken rawAccessJwtToken;
    private SecurityUser securityUser;

    public AbstractJwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessJwtToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public AbstractJwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser.getAuthorities());
        this.eraseCredentials();
        this.securityUser = securityUser;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token tu trusted - use constructor witch takes a GrantedAuthority list instead"
            );
        }
    }

    @Override
    public Object getCredentials() {
        return rawAccessJwtToken;
    }

    @Override
    public Object getPrincipal() {
        return this.securityUser;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessJwtToken = null;
    }
}
