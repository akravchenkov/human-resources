package ru.human.resources.user.service.service.security.auth.jwt.extractor;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
public class JwtHeaderTokenExtractor implements  TokenExtractor {

    public static final String HEADER_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

    @Override
    public String extractor(HttpServletRequest request) {
        String header = request.getHeader(JWT_TOKEN_HEADER_PARAM);
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
