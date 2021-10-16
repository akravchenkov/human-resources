package ru.human.resources.user.service.service.security.auth.jwt.extractor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Anton Kravchenkov
 * @since 16.08.2021
 */
public interface TokenExtractor {
    String extractor(HttpServletRequest request);
}
