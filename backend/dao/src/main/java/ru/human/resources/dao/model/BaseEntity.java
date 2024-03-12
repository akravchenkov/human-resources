package ru.human.resources.dao.model;

/**
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface BaseEntity<D> extends ToData<D> {

    Long getId();

    void setId(Long id);

    long getCreatedTime();

    void setCreatedTime(long createdTime);

}
