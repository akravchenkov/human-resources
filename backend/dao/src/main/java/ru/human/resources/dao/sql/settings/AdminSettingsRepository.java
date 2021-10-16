package ru.human.resources.dao.sql.settings;

import org.springframework.data.repository.CrudRepository;
import ru.human.resources.dao.model.sql.AdminSettingsEntity;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public interface AdminSettingsRepository extends CrudRepository<AdminSettingsEntity, Long> {

    AdminSettingsEntity findByKey(String key);
}
