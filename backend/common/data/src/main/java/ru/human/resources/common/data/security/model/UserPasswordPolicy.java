package ru.human.resources.common.data.security.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Data
public class UserPasswordPolicy implements Serializable {

    private static final long serialVersionUID = -8682271827244295187L;

    private Integer minimumLength;
    private Integer minimumUppercaseLetters;
    private Integer minimumLowercaseLetters;
    private Integer minimumDigits;
    private Integer minimumSpecialCharacters;

    private Integer passwordExpirationPeriodDays;
    private Integer passwordReuseFrequencyDays;
}
