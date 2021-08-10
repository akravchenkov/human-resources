package ru.human.resources.dao.sql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.human.resources.dao.Dao;
import ru.human.resources.dao.DaoUtil;
import ru.human.resources.dao.model.BaseEntity;

import java.util.Optional;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
@Slf4j
public abstract class JpaAbstractDao<E extends BaseEntity<D>, D> implements Dao<D> {

    protected abstract Class<E> getEntityClass();

    protected abstract CrudRepository<E, Long> getCrudRepository();

    @Override
    @Transactional
    public D save(D domain) {
        E entity;
        try {
            entity = getEntityClass().getConstructor(domain.getClass()).newInstance(domain);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Can't create entity for domain object {" + domain + "}", e);
        }

        entity = getCrudRepository().save(entity);

        return DaoUtil.getData(entity);
    }

    @Override
    public D findById(Long id) {
        log.debug("get entity by id {}", id);
        Optional<E> entity = getCrudRepository().findById(id);
        return DaoUtil.getData(entity);
    }
}
