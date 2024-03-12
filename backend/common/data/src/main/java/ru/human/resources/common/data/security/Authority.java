package ru.human.resources.common.data.security;

/**
 * @author Anton Kravchenkov
 * @since 10.08.2021
 */
public enum Authority {

    SYS_ADMIN(0),
    EMPLOYEE_USER(1),
    EMPLOYEE_ADMIN(3),
    REFRESH_TOKEN(10);

    private final int code;

    Authority(int code) {
        this.code = code;
    }

    public static Authority parse(String value) {
        Authority authority = null;
        if (value != null && value.length() != 0) {
            for (Authority current : Authority.values()) {
                if (current.name().equalsIgnoreCase(value)) {
                    authority = current;
                    break;
                }
            }
        }
        return authority;
    }
}
