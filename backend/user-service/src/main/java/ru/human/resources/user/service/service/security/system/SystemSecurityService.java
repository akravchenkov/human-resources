package ru.human.resources.user.service.service.security.system;

import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.common.data.security.model.SecuritySettings;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
public interface SystemSecurityService {

    void validateUserCredentials(Long id, UserCredentials userCredentials, String username, String password);

    SecuritySettings getSecuritySettings(Long id);

}
