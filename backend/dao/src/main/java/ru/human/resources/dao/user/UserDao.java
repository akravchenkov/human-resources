package ru.human.resources.dao.user;

import ru.human.resources.common.data.UserDto;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.dao.Dao;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface UserDao extends Dao<UserDto> {

    /**
     * Save or update user object
     *
     * @param user the user object
     * @return saved user entity
     */
    UserDto save(UserDto user);

    PageData<UserDto> findAll(PageLink pageLink);

    UserDto findByEmail(String username);

}
