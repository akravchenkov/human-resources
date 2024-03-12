package ru.human.resources.user.service.service.security.system;

import static ru.human.resources.common.data.CacheConstants.SECURITY_SETTINGS_CACHE;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.human.resources.common.dao.api.settings.AdminSettingsService;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.common.data.security.model.SecuritySettings;
import ru.human.resources.common.data.security.model.UserPasswordPolicy;
import ru.human.resources.common.util.JacksonUtil;
import ru.human.resources.user.service.security.exception.UserPasswordExpiredException;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
@Slf4j
@Service
public class DefaultSystemSecurityService implements SystemSecurityService {

    @Resource
    private SystemSecurityService self;

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final AdminSettingsService adminSettingsService;

    @Autowired
    public DefaultSystemSecurityService(
        BCryptPasswordEncoder encoder,
        UserService userService,
        AdminSettingsService adminSettingsService) {
        this.encoder = encoder;
        this.userService = userService;
        this.adminSettingsService = adminSettingsService;
    }

    @Override
    public void validateUserCredentials(
        Long id,
        UserCredentials userCredentials,
        String username,
        String password
    ) throws AuthenticationException {
        if (!encoder.matches(password, userCredentials.getPassword())) {
            int failedLoginAttempts = userService.onUserLoginIncorrectCredentials(id);
            SecuritySettings securitySettings = getSecuritySettings(id);
            if (securitySettings.getMaxFailedLoginAttempts() != null
                && securitySettings.getMaxFailedLoginAttempts() > 0) {
                if (failedLoginAttempts > securitySettings.getMaxFailedLoginAttempts() && userCredentials.isEnabled()) {
                    userService.setUserCredentialsEnabled(userCredentials.getId(), false);
                    if (StringUtils.isNotBlank(securitySettings.getUserLockoutNotificationEmail())) {
                        // TODO finished here
                        // TODO Create method mailService
                    }
                    throw new LockedException("Authentication Failed. Username was locked due to security policy.");
                }
            }
            throw new LockedException("Authentication Failed. Username or Password not valid.");
        }

        if (!userCredentials.isEnabled()) {
            throw new DisabledException("User is not active");
        }

        userService.onUserLoginSuccessful(id);

        SecuritySettings securitySettings = self.getSecuritySettings(id);
        if (isPositiveInteger(securitySettings.getPasswordPolicy().getPasswordExpirationPeriodDays())) {
            if ((userCredentials.getCreatedTime()
                + TimeUnit.DAYS.toMillis(securitySettings.getPasswordPolicy().getPasswordExpirationPeriodDays())
                < System.currentTimeMillis()
            )) {
                userCredentials = userService.requestExpiredPasswordReset(userCredentials.getId());
                throw new UserPasswordExpiredException("User password expired!", userCredentials.getResetToken());
            }
        }
    }

    @Cacheable(cacheNames = SECURITY_SETTINGS_CACHE, key = "'securitySettings'")
    @Override
    public SecuritySettings getSecuritySettings(Long id) {
        SecuritySettings securitySettings = null;
        AdminSettings adminSettings = adminSettingsService
            .findAdminSettingsByKey(id, "securitySettings");
        if (adminSettings != null) {
            try {
                securitySettings = JacksonUtil
                    .convertValue(adminSettings.getJsonValue(), SecuritySettings.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load security settings!", e);
            }
        } else {
            securitySettings = new SecuritySettings();
            securitySettings.setPasswordPolicy(new UserPasswordPolicy());
            securitySettings.getPasswordPolicy().setMinimumLength(6);
        }
        return securitySettings;
    }

    private static boolean isPositiveInteger(Integer val) {
        return val != null && val > 0;
    }
}
