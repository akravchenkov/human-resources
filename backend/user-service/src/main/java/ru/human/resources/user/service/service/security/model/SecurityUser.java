package ru.human.resources.user.service.service.security.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import ru.human.resources.common.data.UserDto;

/**
 * @author Anton Kravchenkov
 * @since 10.08.2021
 */
public class SecurityUser extends UserDto {

    private static final long serialVersionUID = -3477731280183033228L;

    private Collection<GrantedAuthority> authorities;
    private boolean enabled;
    private UserPrincipal userPrincipal;
}
