package ru.human.resources.user.service.config;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
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
