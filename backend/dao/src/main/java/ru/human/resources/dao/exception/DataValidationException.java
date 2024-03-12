package ru.human.resources.dao.exception;

/**
 * @author Anton Kravchenkov
 * @since 12.10.2021
 */
public class DataValidationException extends RuntimeException {

    private static final long serialVersionUID = 520796700431101268L;

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
