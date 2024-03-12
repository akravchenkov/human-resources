package ru.human.resources.dao.sql.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.human.resources.common.data.User;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.dao.DaoUtil;
import ru.human.resources.dao.model.sql.UserEntity;
import ru.human.resources.dao.sql.JpaAbstractDao;
import ru.human.resources.dao.user.UserDao;

/**
 * @author Anton Kravchenkov
 * @since 16.07.2021
 */
@Component
public class JpaUserDao extends JpaAbstractDao<UserEntity, User> implements UserDao {

    private final UserRepository userRepository;

    public JpaUserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    protected CrudRepository<UserEntity, Long> getCrudRepository() {
        return userRepository;
    }

    @Override
    public PageData<User> findAll(PageLink pageLink) {
        return DaoUtil.toPageData(userRepository.findAll(DaoUtil.toPageable(pageLink)));
    }

    @Override
    public User findByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return DaoUtil.getData(userEntity);
    }
}
