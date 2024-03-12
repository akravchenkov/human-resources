package ru.human.resources.user.service.service.security.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import ru.human.resources.common.data.security.Authority;
import ru.human.resources.common.data.security.model.JwtToken;
import ru.human.resources.user.service.config.JwtSettings;
import ru.human.resources.user.service.service.security.exception.JwtExpiredTokenException;
import ru.human.resources.user.service.service.security.model.SecurityUser;
import ru.human.resources.user.service.service.security.model.UserPrincipal;
import ru.human.resources.user.service.service.security.model.UserPrincipal.Type;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenFactory {

    private static final String SCOPES = "scopes";
    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String ENABLED = "enabled";
    private static final String IS_PUBLIC = "isPublic";

    private final JwtSettings settings;

    public SecurityUser parseAccessJwtToken(RawAccessJwtToken rawAccessToken) {
        Jws<Claims> jwsClaims = parseTokenClaims(rawAccessToken);
        Claims claims = jwsClaims.getBody();
        String subject = claims.getSubject();
        List<String> scopes = claims.get(SCOPES, List.class);
        if (scopes == null || scopes.isEmpty()) {
            throw new IllegalArgumentException("JWT Token doesn't have any scopes");
        }
        SecurityUser securityUser = new SecurityUser(claims.get(USER_ID, Long.class));
        securityUser.setEmail(subject);
        securityUser.setAuthority(Authority.parse(scopes.get(0)));
        securityUser.setFirstName(claims.get(FIRST_NAME, String.class));
        securityUser.setLastName(claims.get(LAST_NAME, String.class));
        securityUser.setEnabled(claims.get(ENABLED, Boolean.class));
        boolean isPublic = claims.get(IS_PUBLIC, Boolean.class);
        UserPrincipal principal = new UserPrincipal(isPublic ? Type.PUBLIC_ID : Type.USER_NAME, subject);
        securityUser.setUserPrincipal(principal);
        String userId = claims.get(USER_ID, String.class);
        if (userId != null) {
            securityUser.setUserId(userId);
        }
        return securityUser;
    }

    public Jws<Claims> parseTokenClaims(JwtToken token) {
        try {
            return Jwts.parser()
                .setSigningKey(settings.getTokenSigningKey())
                .parseClaimsJws(token.getToken());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.debug("Invalid JWT token: ", ex);
            throw new BadCredentialsException("Invalid JWT Token", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.debug("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(token, "JWT Token expired", expiredEx);
        }
    }
}
