package ru.human.resources.user.service.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
@Configuration
public class JwtSettings {

    private String tokenSigningKey;

    private Integer refreshTokenExpTime;

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }
}
