package ru.human.resources.user.service.service.security.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import ru.human.resources.common.data.security.model.JwtToken;
import ru.human.resources.user.service.service.security.model.SecurityUser;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
@Component
public class JwtTokenFactory {

    public SecurityUser parseAccessJwtToken(RawAccessJwtToken rawAccessToken) {
        Jws<Claims> jwsClaims = parseTokenClaims(rawAccessToken);
    }

    public Jws<Claims> parseTokenClaims(JwtToken token) {

    }
}
