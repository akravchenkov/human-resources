package ru.human.resources.dao.sql.user;

import org.springframework.data.repository.CrudRepository;
import ru.human.resources.dao.model.sql.UserCredentialsEntity;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
public interface UserCredentialsRepository extends CrudRepository<UserCredentialsEntity, Long> {

}
