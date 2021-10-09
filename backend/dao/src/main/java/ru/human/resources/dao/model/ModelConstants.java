package ru.human.resources.dao.model;

/**
 * @author Anton Kravchenkov
 * @since 16.07.2021
 */
public class ModelConstants {

    /**
     * Generic constants.
     */
    public static final String ID_PROPERTY = "id";
    public static final String CREATED_TIME_PROPERTY = "created_time";

    /**
     * User constants.
     */
    public static final String USER_NAME_TABLE = "hr_user";
    public static final String USER_EMAIL_PROPERTY = "email";
    public static final String USER_FIRST_NAME_PROPERTY = "first_name";
    public static final String USER_LAST_NAME_PROPERTY = "last_name";
    public static final String USER_AUTHORITY_PROPERTY = "authority";

    /**
     * User credentials constant.
     */
    public static final String USER_CREDENTIALS_COLUMN_FAMILY_NAME = "user_credentials";
    public static final String USER_CREDENTIALS_ENABLED_PROPERTY = "enabled";
    public static final String USER_CREDENTIALS_PASSWORD_PROPERTY = "password";
    public static final String USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY = "activate_token";
    public static final String USER_CREDENTIALS_RESET_TOKEN_PROPERTY = "reset_token";

}
