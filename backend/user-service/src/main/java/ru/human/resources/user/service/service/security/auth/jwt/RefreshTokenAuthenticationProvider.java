package ru.human.resources.user.service.service.security.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.common.data.User;
import ru.human.resources.user.service.service.security.auth.RefreshAuthenticationToken;
import ru.human.resources.user.service.service.security.auth.TokenOutdatingService;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.UserPrincipal;
import ru.human.resources.user.service.service.security.model.UserPrincipal.Type;
import ru.human.resources.user.service.service.security.model.token.JwtTokenFactory;
import ru.human.resources.user.service.service.security.model.token.RawAccessJwtToken;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenFactory tokenFactory;
    private final UserService userService;
    private final TokenOutdatingService tokenOutdatingService;

    @Override
    public Authentication authenticate(final Authentication authentication)
        throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provider");
        val rawAccessJwtToken = (RawAccessJwtToken) authentication.getCredentials();
        val unsafeUser = tokenFactory.parseAccessJwtToken(rawAccessJwtToken);
        val principal = unsafeUser.getUserPrincipal();
        val securityUser = authenticateByUserid(unsafeUser.getId());

        if (tokenOutdatingService.isOutdated(rawAccessJwtToken, securityUser.getId())) {
            throw new CredentialsExpiredException("Token is outdated");
        }

        return new RefreshAuthenticationToken(securityUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
//        return (RefreshAuthenticationToken.class.isAssignableFrom(authentication));
    }

    // TODO create this method
    private SecurityUser authenticateByUserid(Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by refresh token");
        }

        if (user.getAuthority() == null) {
            throw new InsufficientAuthenticationException("User has no assigned");
        }

        UserPrincipal userPrincipal = new UserPrincipal(Type.USER_NAME, user.getEmail());
        return new SecurityUser(user, true, userPrincipal);
    }
}
