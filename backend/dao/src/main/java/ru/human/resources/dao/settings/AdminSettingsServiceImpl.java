package ru.human.resources.dao.settings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.human.resources.common.dao.api.settings.AdminSettingsService;
import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.dao.service.Validator;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Service
@AllArgsConstructor
@Slf4j
public class AdminSettingsServiceImpl implements AdminSettingsService {

    private final AdminSettingsDao adminSettingsDao;

    @Override
    public AdminSettings findAdminSettingsByKey(Long id, String key) {
        log.trace("Executing findAdminSettingsByKey [{}]", key);
        Validator.validateString(key, "Incorrect key " + key);
        return adminSettingsDao.findByKey(key);
    }
}
