package ru.human.resources.dao.sql.user;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.dao.model.sql.UserCredentialsEntity;
import ru.human.resources.dao.sql.JpaAbstractDao;
import ru.human.resources.dao.user.UserCredentialsDao;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
@Component
@AllArgsConstructor
public class JpaUserCredentialsDao extends
    JpaAbstractDao<UserCredentialsEntity, UserCredentials> implements
    UserCredentialsDao {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    protected Class<UserCredentialsEntity> getEntityClass() {
        return UserCredentialsEntity.class;
    }

    @Override
    protected CrudRepository<UserCredentialsEntity, Long> getCrudRepository() {
        return userCredentialsRepository;
    }
}
