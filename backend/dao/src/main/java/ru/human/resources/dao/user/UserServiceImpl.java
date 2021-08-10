package ru.human.resources.dao.user;

import java.util.ArrayList;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.human.resources.common.dao.api.UserService;
import ru.human.resources.common.data.UserDto;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.dao.model.sql.UserEntity;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PageData<UserDto> findAll(PageLink pageLink) {
        // TODO add validate pageLink
        return userDao.findAll(pageLink);
    }

    @Override
    public UserDto saveUser(UserDto user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public UserDto findUserById(Long userId) {
        log.info("Executing findUserById [{}]", userId);
        return userDao.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDao.findByEmail(username);
        return new User(
            userDto.getEmail(),
            userDto.getPassword(),
            true,
            true,
            true,
            true,
            new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByUserName(String email) {
        UserDto userDto = userDao.findByEmail(email);
        if (userDto == null) throw new UsernameNotFoundException(email);
        return userDto;
    }
}
