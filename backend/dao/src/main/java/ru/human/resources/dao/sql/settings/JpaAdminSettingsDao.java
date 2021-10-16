package ru.human.resources.dao.sql.settings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.human.resources.common.data.AdminSettings;
import ru.human.resources.dao.DaoUtil;
import ru.human.resources.dao.model.sql.AdminSettingsEntity;
import ru.human.resources.dao.settings.AdminSettingsDao;
import ru.human.resources.dao.sql.JpaAbstractDao;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Component
@AllArgsConstructor
@Slf4j
public class JpaAdminSettingsDao extends JpaAbstractDao<AdminSettingsEntity, AdminSettings> implements AdminSettingsDao {

    private final AdminSettingsRepository adminSettingsRepository;

    @Override
    public AdminSettings findByKey(String key) {
        return DaoUtil.getData(adminSettingsRepository.findByKey(key));
    }

    @Override
    protected Class<AdminSettingsEntity> getEntityClass() {
        return AdminSettingsEntity.class;
    }

    @Override
    protected CrudRepository<AdminSettingsEntity, Long> getCrudRepository() {
        return adminSettingsRepository;
    }
}
