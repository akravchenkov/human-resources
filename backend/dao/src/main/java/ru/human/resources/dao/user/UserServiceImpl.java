package ru.human.resources.dao.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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


import static ru.human.resources.dao.service.Validator.validateId;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String INCORRECT_USER_ID = "Incorrect user id ";

    private static final String LAST_LOGIN_TS = "lastLoginTs";
    private static final String FAILED_LOGIN_ATTEMPTS = "failedLoginAttempts";

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
