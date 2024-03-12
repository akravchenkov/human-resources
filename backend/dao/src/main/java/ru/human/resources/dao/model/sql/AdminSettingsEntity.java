package ru.human.resources.dao.model.sql;

import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.dao.model.BaseEntity;
import ru.human.resources.dao.model.BaseSqlEntity;
import ru.human.resources.dao.model.ModelConstants;
import ru.human.resources.dao.util.mapping.JsonStringType;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = ModelConstants.ADMIN_SETTINGS_COLUMN_FAMILY_NAME)
public final class AdminSettingsEntity extends BaseSqlEntity<AdminSettings> implements BaseEntity<AdminSettings> {

    @Column(name = ModelConstants.ADMIN_SETTINGS_KEY_PROPERTY)
    private String key;

    @Type(type = "json")
    @Column(name = ModelConstants.ADMIN_SETTINGS_JSON_VALUE_PROPERTY)
    private JsonNode jsonValue;

    /**
     * This method convert domain object to data transfer object.
     *
     * @return the dto object
     */
    @Override
    public AdminSettings toData() {
        AdminSettings adminSettings = new AdminSettings();
        adminSettings.setId(id);
        adminSettings.setKey(key);
        adminSettings.setCreatedTime(createdTime);
        adminSettings.setJsonValue(jsonValue);
        return adminSettings;
    }
}
