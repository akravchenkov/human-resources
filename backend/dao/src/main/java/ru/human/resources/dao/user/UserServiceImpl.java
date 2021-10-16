package ru.human.resources.dao.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.common.data.User;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.common.util.JacksonUtil;
import ru.human.resources.dao.exception.DataValidationException;
import ru.human.resources.dao.exception.IncorrectParameterException;
import ru.human.resources.dao.service.DataValidator;


import static ru.human.resources.dao.service.Validator.validateId;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_PASSWORD_HISTORY = "userPasswordHistory";
    private static final String USER_CREDENTIALS_ENABLED = "userCredentialsEnabled";

    public static final String INCORRECT_USER_ID = "Incorrect user id ";
    private static final String LAST_LOGIN_TS = "lastLoginTs";
    private static final String FAILED_LOGIN_ATTEMPTS = "failedLoginAttempts";
    private static final int DEFAULT_TOKEN_LENGTH = 30;

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCredentialsDao userCredentialsDao;

    @Override
    public PageData<User> findAll(PageLink pageLink) {
        // TODO add validate pageLink
        return userDao.findAll(pageLink);
    }

    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        log.info("Executing findUserById [{}]", userId);
        return userDao.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            true,
            true,
            true,
            true,
            new ArrayList<>());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);
        return user;
    }

    @Override
    public UserCredentials findUserCredentialsById(Long id) {
        log.trace("Executing findUserCredentialsById");
        validateId(id, INCORRECT_USER_ID + id);
        return null;
    }

    @Override
    public void onUserLoginSuccessful(Long id) {
        log.trace("Executing onUserLoginSuccessful [{}]", id);
        val user = findUserById(id);
        setLastLoginTs(user);
        resetFailedLoginAttempts(user);
        saveUser(user);
    }

    @Override
    public int onUserLoginIncorrectCredentials(Long id) {
        log.trace("Executing onUserLoginIncorrectCredentials [{}]", id);
        val user = findUserById(id);
        val failedLoginAttempts = increaseFailedLoginAttempts(user);
        saveUser(user);
        return failedLoginAttempts;
    }

    @Override
    public void setUserCredentialsEnabled(Long id, boolean enabled) {
        log.trace("Executing setUserCredentialsEnabled [{}], [{}]", id, enabled);
        validateId(id, INCORRECT_USER_ID + id);
        UserCredentials userCredentials = userCredentialsDao.findById(id);
        userCredentials.setEnabled(enabled);
        saveUserCredentials(id, userCredentials);

        val user = findUserById(id);
        JsonNode additionalInfo = user.getAdditionalInfo();
        if (!(additionalInfo instanceof ObjectNode)) {
            additionalInfo = JacksonUtil.newObjectNode();
        }
        ((ObjectNode) additionalInfo).put(USER_CREDENTIALS_ENABLED, enabled);
        user.setAdditionalInfo(additionalInfo);
        if (enabled) {
            resetFailedLoginAttempts(user);
        }
        userDao.save(user);
    }

    @Override
    public UserCredentials saveUserCredentials(Long id, UserCredentials userCredentials) {
        log.trace("Executing saveUserCredentials [{}]", userCredentials);
        userCredentialsValidator.validate(userCredentials, data -> id);
        return saveUserCredentialsAndPasswordHistory(id, userCredentials);
    }

    @Override
    public UserCredentials requestExpiredPasswordReset(Long id) {
        UserCredentials userCredentials = userCredentialsDao.findById(id);
        if (!userCredentials.isEnabled()) {
            throw new IncorrectParameterException("Unable to reset password for inactive user");
        }
        userCredentials.setResetToken(RandomStringUtils.randomAlphanumeric(DEFAULT_TOKEN_LENGTH));
        return saveUserCredentials(id, userCredentials);
    }

    private void setLastLoginTs(User user) {
        JsonNode additionalInfo = user.getAdditionalInfo();
        if (!(additionalInfo instanceof ObjectNode)) {
            additionalInfo = JacksonUtil.newObjectNode();
        }
        ((ObjectNode) additionalInfo).put(LAST_LOGIN_TS, System.currentTimeMillis());
        user.setAdditionalInfo(additionalInfo);
    }

    private void resetFailedLoginAttempts(User user) {
        JsonNode additionalInfo = user.getAdditionalInfo();
        if (!(additionalInfo instanceof ObjectNode)) {
            additionalInfo = JacksonUtil.newObjectNode();
        }
        ((ObjectNode) additionalInfo).put(FAILED_LOGIN_ATTEMPTS, 0);
        user.setAdditionalInfo(additionalInfo);
    }

    private int increaseFailedLoginAttempts(User user) {
        JsonNode additionalInfo = user.getAdditionalInfo();
        if (!(additionalInfo instanceof ObjectNode)) {
            additionalInfo = JacksonUtil.newObjectNode();
        }
        int failedLoginAttempts = 0;
        if (additionalInfo.has(FAILED_LOGIN_ATTEMPTS)) {
            failedLoginAttempts = additionalInfo.get(FAILED_LOGIN_ATTEMPTS).asInt();
        }
        failedLoginAttempts = failedLoginAttempts + 1;
        ((ObjectNode) additionalInfo).put(FAILED_LOGIN_ATTEMPTS, failedLoginAttempts);
        user.setAdditionalInfo(additionalInfo);
        return failedLoginAttempts;
    }

    private final DataValidator<UserCredentials> userCredentialsValidator =
        new DataValidator<UserCredentials>() {

            @Override
            protected void validateCreate(Long id, UserCredentials userCredentials) {
                throw new IncorrectParameterException("Creation of new user credentials is prohibited");
            }

            @Override
            protected void validateDataImpl(Long id, UserCredentials userCredentials) {
                if (userCredentials.getId() == null) {
                    throw new DataValidationException("User credentials should be assigned to user!");
                }
                if (userCredentials.isEnabled()) {
                    if (StringUtils.isEmpty(userCredentials.getPassword())) {
                        throw new DataValidationException("Enabled user credentials should have password!");
                    }
                    if (StringUtils.isNotEmpty(userCredentials.getActivateToken())) {
                        throw new DataValidationException("Enabled user credentials can't have active token!");
                    }
                }
                UserCredentials existingUserCredentialsEntity = userCredentialsDao.findById(id);
                if (existingUserCredentialsEntity == null) {
                    throw new DataValidationException("Unable to update non-existent user credentials!");
                }
                val user = findUserById(id);
                if (user == null) {
                    throw new DataValidationException("Can't assign user credentials to non-existent user!");
                }
            }
        };

    private UserCredentials saveUserCredentialsAndPasswordHistory(Long id, UserCredentials userCredentials) {
        val result = userCredentialsDao.save(userCredentials);
        val user = findUserById(userCredentials.getId());
        if (userCredentials.getPassword() != null) {
            updatePasswordHistory(user, userCredentials);
        }
        return result;
    }

    private void updatePasswordHistory(User user, UserCredentials userCredentials) {
        JsonNode additionalInfo = user.getAdditionalInfo();
        if (!(additionalInfo instanceof ObjectNode)) {
            additionalInfo = JacksonUtil.newObjectNode();
        }
        Map<String, String> userPasswordHistoryMap = null;
        JsonNode userPasswordHistoryJson;
        if (additionalInfo.has(USER_PASSWORD_HISTORY)) {
            userPasswordHistoryJson = additionalInfo.get(USER_PASSWORD_HISTORY);
            userPasswordHistoryMap = JacksonUtil.convertValue(userPasswordHistoryJson, new TypeReference<Map<String, String>>(){});
        }
        if (userPasswordHistoryMap != null) {
            userPasswordHistoryMap.put(Long.toString(System.currentTimeMillis()), userCredentials.getPassword());
            userPasswordHistoryJson = JacksonUtil.valueToTree(userPasswordHistoryMap);
            ((ObjectNode) additionalInfo).replace(USER_PASSWORD_HISTORY, userPasswordHistoryJson);
        } else {
            userPasswordHistoryMap = new HashMap<>();
            userPasswordHistoryMap.put(Long.toString(System.currentTimeMillis()), userCredentials.getPassword());
            userPasswordHistoryJson = JacksonUtil.valueToTree(userPasswordHistoryMap);
            ((ObjectNode) additionalInfo).set(USER_PASSWORD_HISTORY, userPasswordHistoryJson);
        }
        user.setAdditionalInfo(additionalInfo);
        saveUser(user);
    }
}
