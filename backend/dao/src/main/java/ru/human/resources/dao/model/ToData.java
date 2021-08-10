package ru.human.resources.dao.model;

/**
 * The interface to dto
 *
 * @author Anton Kravchenkov
 * @since 15.07.2021
 */
public interface ToData<T> {

    /**
     * This method convert domain object to data transfer object.
     *
     * @return the dto object
     */
    T toData();

}
