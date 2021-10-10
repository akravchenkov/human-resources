package ru.human.resources.dao.sql.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.human.resources.dao.settings.AdminSettingsDao;
import ru.human.resources.dao.sql.JpaAbstractDao;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Component
@Slf4j
public class JpaAdminSettingsDao extends JpaAbstractDao<> implements AdminSettingsDao {

}
