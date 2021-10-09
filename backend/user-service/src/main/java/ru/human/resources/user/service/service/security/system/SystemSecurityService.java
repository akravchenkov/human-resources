package ru.human.resources.user.service.service.security.system;

import ru.human.resources.common.data.security.UserCredentials;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
public interface SystemSecurityService {

    void validateUserCredentials(UserCredentials userCredentials, String username, String password);

}
