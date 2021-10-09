package ru.human.resources.dao.user;

import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.dao.Dao;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
public interface UserCredentialsDao extends Dao<UserCredentials> {

    /**
     * Find user credentials by user id.
     *
     * @param id the user id
     * @return the user credentials object
     */
    UserCredentials findById(Long id);

}
