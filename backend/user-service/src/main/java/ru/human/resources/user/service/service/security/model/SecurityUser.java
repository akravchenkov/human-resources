package ru.human.resources.user.service.service.security.model;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = Stream.of(SecurityUser.this.getAuthority())
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
        }
        return authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }
}
