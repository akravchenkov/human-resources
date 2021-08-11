package ru.human.resources.dao.model.sql;

import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import ru.human.resources.common.data.UserDto;
import ru.human.resources.common.data.security.Authority;
import ru.human.resources.dao.model.BaseSqlEntity;
import ru.human.resources.dao.model.ModelConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Anton Kravchenkov
 * @since 16.07.2021
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = ModelConstants.USER_NAME_TABLE)
public class UserEntity extends BaseSqlEntity<UserDto> implements Serializable {

    private static final long serialVersionUID = -5272528517758260238L;

    @Column(name = ModelConstants.USER_EMAIL_PROPERTY, unique = true, nullable = false, length = 120)
    private String email;

    @Column(name = ModelConstants.USER_FIRST_NAME_PROPERTY, nullable = false, length = 50)
    private String firstName;

    @Column(name = ModelConstants.USER_LAST_NAME_PROPERTY, nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.USER_AUTHORITY_PROPERTY)
    private Authority authority;

    public UserEntity() {
    }

    public UserEntity(UserDto user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.encryptedPassword = user.getPassword();
        this.authority = user.getAuthority();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    /**
     * This method convert domain object to data transfer object.
     *
     * @return the dto object
     */
    @Override
    public UserDto toData() {
        UserDto user = new UserDto();
        user.setUserId(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(encryptedPassword);
        user.setAuthority(authority);
        return user;
    }
}
