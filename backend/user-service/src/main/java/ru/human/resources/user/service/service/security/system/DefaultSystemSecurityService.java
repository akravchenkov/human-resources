package ru.human.resources.user.service.service.security.system;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.human.resources.common.data.security.UserCredentials;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
@AllArgsConstructor
public class DefaultSystemSecurityService implements SystemSecurityService {

    private BCryptPasswordEncoder encoder;

    @Override
    public void validateUserCredentials(
        UserCredentials userCredentials,
        String username,
        String password
    ) {
        if (!encoder.matches(password, userCredentials.getPassword())) {

        }

        if (!userCredentials.isEnabled()) {
            throw new DisabledException("User is not active");
        }


    }
}
