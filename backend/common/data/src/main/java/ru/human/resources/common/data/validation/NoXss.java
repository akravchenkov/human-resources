package ru.human.resources.common.data.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Anton Kravchenkov
 * @since 11.10.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
public @interface NoXss {
    String message() default "field value is malformed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
