package ru.human.resources.dao;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface Dao<T> {

    T save(T t);

    T findById(Long id);

}
