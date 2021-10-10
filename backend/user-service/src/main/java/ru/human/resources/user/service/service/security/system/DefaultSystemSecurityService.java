package ru.human.resources.user.service.service.security.system;

import static ru.human.resources.common.data.CacheConstants.SECURITY_SETTINGS_CACHE;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.human.resources.common.dao.api.settings.AdminSettingsService;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.common.data.security.model.SecuritySettings;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
@Service
@AllArgsConstructor
public class DefaultSystemSecurityService implements SystemSecurityService {

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final AdminSettingsService adminSettingsService;

    @Override
    public void validateUserCredentials(
        Long id,
        UserCredentials userCredentials,
        String username,
        String password
    ) throws AuthenticationException {
        if (!encoder.matches(password, userCredentials.getPassword())) {
            int failedLoginAttempts = userService.onUserLoginIncorrectCredentials(id);
        }

        if (!userCredentials.isEnabled()) {
            throw new DisabledException("User is not active");
        }


    }

    @Cacheable(cacheNames = SECURITY_SETTINGS_CACHE, key = "'securitySettings'")
    @Override
    public SecuritySettings getSecuritySettings(Long id) {
        SecuritySettings securitySettings = null;
        AdminSettings adminSettings = adminSettingsService.findAdminSettingsByKey(id, "securitySettings");
        return null;
    }
}
