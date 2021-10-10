package ru.human.resources.common.dao.api.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.human.resources.common.data.User;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.common.data.security.UserCredentials;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface UserService extends UserDetailsService {

    User saveUser(User user);

    User findUserById(Long userId);

    PageData<User> findAll(PageLink pageLink);

    User findUserByEmail(String email);

    UserCredentials findUserCredentialsById(Long id);

    void onUserLoginSuccessful(Long id);

    int onUserLoginIncorrectCredentials(Long id);

}
