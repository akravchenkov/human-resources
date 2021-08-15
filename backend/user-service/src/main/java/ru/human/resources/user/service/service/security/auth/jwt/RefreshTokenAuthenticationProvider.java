package ru.human.resources.user.service.service.security.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.human.resources.user.service.service.security.auth.RefreshAuthenticationToken;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.token.RawAccessJwtToken;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provider");
        System.out.println("It is work method authenticate!");

        RawAccessJwtToken rawAccessJwtToken = (RawAccessJwtToken) authentication.getCredentials();
        SecurityUser unsafeUser = tokenFactory.parseRefreshToken(rawAccessTokeb);
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (RefreshAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
