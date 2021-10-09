package ru.human.resources.dao.service;

import ru.human.resources.dao.exception.IncorrectParameterException;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
public class Validator {

    /**
     * This method validate <code>id</code> id. If id is null then throw.
     * <code>IncorrectParameterException</code> exception
     * 
     * @param id            the id
     * @param errorMessage  te error message for exception
     */
    public static void validateId(Long id, String errorMessage) {
        if (id == null) {
            throw new IncorrectParameterException(errorMessage);
        }
    }
}
