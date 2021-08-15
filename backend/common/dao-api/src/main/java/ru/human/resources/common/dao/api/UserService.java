package ru.human.resources.common.dao.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.human.resources.common.data.User;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface UserService extends UserDetailsService {

    User saveUser(User user);

    User findUserById(Long userId);

    PageData<User> findAll(PageLink pageLink);

    User getUserDetailsByUserName(String email);

}
