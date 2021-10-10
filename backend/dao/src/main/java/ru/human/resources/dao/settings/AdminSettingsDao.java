package ru.human.resources.dao.settings;

import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.dao.Dao;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public interface AdminSettingsDao extends Dao<AdminSettings> {

    AdminSettings findByKey(Long id, String key);

}
