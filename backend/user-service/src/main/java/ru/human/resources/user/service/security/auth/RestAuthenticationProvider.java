package ru.human.resources.user.service.security.auth;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.UserPrincipal;
import ru.human.resources.user.service.service.security.system.SystemSecurityService;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
@Component
@AllArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final SystemSecurityService systemSecurityService;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provider");

        val principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal)) {
            throw new BadCredentialsException("Authentication Failed. Bad user principal.");
        }

        UserPrincipal userPrincipal = (UserPrincipal) principal;
        val username = userPrincipal.getValue();
        val password = (String) authentication.getCredentials();

        return authenticationByUsernameAndPassword(
            authentication,
            userPrincipal,
            username,
            password
        );
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    private Authentication authenticationByUsernameAndPassword(
        final Authentication authentication,
        final UserPrincipal userPrincipal,
        final String username,
        final String password
    ) {
        val user = userService.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        try {
            val userCredentials = userService.findUserCredentialsById(user.getId());
            if (userCredentials == null) {
                throw new UsernameNotFoundException("User credentials not found");
            }

            try {
                systemSecurityService
                    .validateUserCredentials(user.getId(), userCredentials, username, password);
            } catch (LockedException e) {
                throw e;
            }

            if (user.getAuthority() == null) {
                throw new InsufficientAuthenticationException("User has no authority assigned");
            }

            SecurityUser securityUser = new SecurityUser(user, userCredentials.isEnabled(), userPrincipal);
            // TODO Add an entry to the log
            return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        } catch (Exception e) {
            // TODO Add an entry ti the log
            throw e;
        }
    }
}
