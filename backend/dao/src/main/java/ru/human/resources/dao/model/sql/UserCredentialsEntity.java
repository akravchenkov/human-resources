package ru.human.resources.dao.model.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import ru.human.resources.common.data.security.UserCredentials;
import ru.human.resources.dao.model.BaseEntity;
import ru.human.resources.dao.model.BaseSqlEntity;
import ru.human.resources.dao.model.ModelConstants;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.USER_CREDENTIALS_COLUMN_FAMILY_NAME)
public class UserCredentialsEntity extends BaseSqlEntity<UserCredentials> implements BaseEntity<UserCredentials> {

    @Column(name = ModelConstants.USER_CREDENTIALS_ENABLED_PROPERTY)
    private boolean enabled;

    @Column(name = ModelConstants.USER_CREDENTIALS_PASSWORD_PROPERTY)
    private String password;

    @Column(name = ModelConstants.USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY, unique = true)
    private String activateToken;

    @Column(name = ModelConstants.USER_CREDENTIALS_RESET_TOKEN_PROPERTY, unique = true)
    private String resetToken;

    /**
     * This method convert domain object to data transfer object.
     *
     * @return the dto object
     */
    @Override
    public UserCredentials toData() {
        val userCredentials = new UserCredentials();
        userCredentials.setCreatedTime(createdTime);
        userCredentials.setEnabled(enabled);
        userCredentials.setPassword(password);
        userCredentials.setActivateToken(activateToken);
        userCredentials.setResetToken(resetToken);
        return userCredentials;
    }
}
