package ru.human.resources.user.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Assert;
import ru.human.resources.common.data.User;

/**
 * @author Anton Kravchenkov
 * @since 17.07.2021
 */
@Slf4j
public abstract class BaseUserControllerTest extends AbstractControllerTest {

    @Test
    public void testSaveUser() throws Exception {
        String email = "test1@human-resources.ru";
        User user = new User();
        user.setEmail(email);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        User saveUser = doPost("/api/v1/user", user, User.class);
        Assert.assertNotNull(saveUser);
        Assert.assertNotNull(saveUser.getUserId());
        Assert.assertEquals(user.getEmail(), saveUser.getEmail());
    }

    @Test
    public void testFindUserById() throws Exception {
        String email = "test2@human-resources.ru";
        User user = new User();
        user.setEmail(email);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        User saveUser = doPost("/api/v1/user", user, User.class);
        User foundUser = doGet("/api/v1/user/" + saveUser.getUserId().toString(), User.class);
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(saveUser, foundUser);
    }
}
