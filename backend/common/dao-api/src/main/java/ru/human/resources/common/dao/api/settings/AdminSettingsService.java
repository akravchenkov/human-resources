package ru.human.resources.common.dao.api.settings;

import ru.human.resources.common.data.AdminSettings;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public interface AdminSettingsService {

    AdminSettings findAdminSettingsByKey(Long id, String key);
}
