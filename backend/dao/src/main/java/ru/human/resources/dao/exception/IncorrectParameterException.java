package ru.human.resources.dao.exception;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
public class IncorrectParameterException extends RuntimeException {

    private static final long serialVersionUID = -6122768794738668740L;

    public IncorrectParameterException(String message) {
        super(message);
    }
}
