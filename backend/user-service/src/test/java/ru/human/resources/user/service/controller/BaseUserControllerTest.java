package ru.human.resources.user.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Assert;
import ru.human.resources.common.data.UserDto;

/**
 * @author Anton Kravchenkov
 * @since 17.07.2021
 */
@Slf4j
public abstract class BaseUserControllerTest extends AbstractControllerTest {

    @Test
    public void testSaveUser() throws Exception {
        String email = "test1@human-resources.ru";
        UserDto user = new UserDto();
        user.setEmail(email);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        UserDto saveUser = doPost("/api/v1/user", user, UserDto.class);
        Assert.assertNotNull(saveUser);
        Assert.assertNotNull(saveUser.getUserId());
        Assert.assertEquals(user.getEmail(), saveUser.getEmail());
    }

    @Test
    public void testFindUserById() throws Exception {
        String email = "test2@human-resources.ru";
        UserDto user = new UserDto();
        user.setEmail(email);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        UserDto saveUser = doPost("/api/v1/user", user, UserDto.class);
        UserDto foundUser = doGet("/api/v1/user/" + saveUser.getUserId().toString(), UserDto.class);
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(saveUser, foundUser);
    }
}
