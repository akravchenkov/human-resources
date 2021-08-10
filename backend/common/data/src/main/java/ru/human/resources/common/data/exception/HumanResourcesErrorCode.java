package ru.human.resources.common.data.exception;

/**
 * @author Anton Kravchenkov
 * @since 20.07.2021
 */
public enum HumanResourcesErrorCode {

    GENERAL(2),
    AUTHENTICATION(10),
    BAD_REQUEST_PARAMS(31),
    ITEM_NIT_FOUND(32);

    private int errorCode;

    HumanResourcesErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
