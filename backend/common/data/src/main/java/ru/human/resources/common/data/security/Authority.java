package ru.human.resources.common.data.security;

/**
 * @author Anton Kravchenkov
 * @since 10.08.2021
 */
public enum Authority {

    SYS_ADMIN(0),
    EMPLOYEE_USER(1),
    HR_USER(2),
    REFRESH_TOKEN(10);

    private final int code;

    Authority(int code) {
        this.code = code;
    }
}
