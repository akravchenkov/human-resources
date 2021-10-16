package ru.human.resources.user.service.service.security.auth;

import io.jsonwebtoken.Claims;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import ru.human.resources.common.data.security.model.JwtToken;
import ru.human.resources.user.service.config.JwtSettings;
import ru.human.resources.user.service.service.security.model.token.JwtTokenFactory;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
@Service
@RequiredArgsConstructor
public class TokenOutdatingService {

    private final JwtTokenFactory tokenFactory;
    private final JwtSettings jwtSettings;
    private Cache tokenOutdatageTimeCache;

    public boolean isOutdated(JwtToken token, Long id) {
        Claims claims = tokenFactory.parseTokenClaims(token).getBody();
        long issueTime = claims.getIssuedAt().getTime();
        return Optional.ofNullable(tokenOutdatageTimeCache.get(id, Long.class))
            .map(outdatageTime -> {
                if (System.currentTimeMillis() - outdatageTime <= SECONDS.toMillis(jwtSettings.getRefreshTokenExpTime())) {
                    return MILLISECONDS.toSeconds(issueTime) < MILLISECONDS.toSeconds(outdatageTime);
                } else {
                    tokenOutdatageTimeCache.evict(id.toString());
                    return false;
                }
            }).orElse(false);
    }
}
