package ru.human.resources.common.dao.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.human.resources.common.data.UserDto;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface UserService extends UserDetailsService {

    UserDto saveUser(UserDto user);

    UserDto findUserById(Long userId);

    PageData<UserDto> findAll(PageLink pageLink);

    UserDto getUserDetailsByUserName(String email);

}
