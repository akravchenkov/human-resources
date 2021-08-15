package ru.human.resources.common.data.security.model;

import java.io.Serializable;

/**
 * @author Anton Kravchenkov
 * @since 15.08.2021
 */
public interface JwtToken extends Serializable {
    String getToken();
}
