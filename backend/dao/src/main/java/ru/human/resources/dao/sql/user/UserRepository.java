package ru.human.resources.dao.sql.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.human.resources.dao.model.sql.UserEntity;

/**
 * @author Anton Kravchenkov
 * @since 17.07.2021
 */
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String username);
}
