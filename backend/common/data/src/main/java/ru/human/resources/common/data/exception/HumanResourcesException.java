package ru.human.resources.common.data.exception;

/**
 * @author Anton Kravchenkov
 * @since 20.07.2021
 */
public class HumanResourcesException extends Exception {

    private static final long serialVersionUID = -4101544429047641056L;

    private HumanResourcesErrorCode errorCode;

    public HumanResourcesException() {
        super();
    }

    public HumanResourcesException(HumanResourcesErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HumanResourcesException(String message, HumanResourcesErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
