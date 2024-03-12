package ru.human.resources.user.service.utils.convertor;

import org.springframework.stereotype.Component;
import ru.human.resources.common.data.User;
import ru.human.resources.common.data.model.request.UserRequest;
import ru.human.resources.common.data.model.response.UserResponse;

/**
 * @author Anton Kravchenkov
 * @since 12.08.2021
 */
@Component
public class UserConvertor extends Convertor<UserResponse, UserRequest, User> {

    @Override
    protected Class<UserResponse> getClassResponse() {
        return UserResponse.class;
    }

    @Override
    protected Class<User> getClassDto() {
        return User.class;
    }
}
