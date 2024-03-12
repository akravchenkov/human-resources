package ru.human.resources.dao.service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import ru.human.resources.common.data.BaseData;
import ru.human.resources.common.data.validation.NoXss;
import ru.human.resources.dao.exception.DataValidationException;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Slf4j
public abstract class DataValidator<D extends BaseData> {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private static Validator fieldsValidator;

    static {
        initializeFieldsValidator();
    }

    public void validate(D data, Function<D, Long> idFunction) {
        try {
            if (data == null) {
                throw new DataValidationException("Data object can't be null!");
            }

            List<String> validationErrors = validateFields(data);
            if (!validationErrors.isEmpty()) {
                throw new IllegalArgumentException(
                    "Validation error: " + String.join(", ", validationErrors));
            }

            Long id = idFunction.apply(data);
            validateDataImpl(id, data);
            if (data.getId() == null) {
                validateCreate(id, data);
            } else {
                validateUpdate(id, data);
            }
        } catch (DataValidationException e) {
            log.error("Data object is invalid: [{}]", e.getMessage());
            throw e;
        }
    }

    protected void validateDataImpl(Long id, D data) {
    }

    protected void validateCreate(Long id, D data) {
    }

    protected void validateUpdate(Long id, D data) {
    }

    private List<String> validateFields(D data) {
        Set<ConstraintViolation<D>> constraintsViolations = fieldsValidator.validate(data);
        return constraintsViolations.stream()
            .map(ConstraintViolation::getMessage)
            .distinct()
            .collect(Collectors.toList());
    }

    private static void initializeFieldsValidator() {
        HibernateValidatorConfiguration validatorConfiguration = Validation
            .byProvider(HibernateValidator.class).configure();
        ConstraintMapping constraintMapping = validatorConfiguration.createConstraintMapping();
        constraintMapping.constraintDefinition(NoXss.class).validatedBy(NoXssValidator.class);
        validatorConfiguration.addMapping(constraintMapping);
        fieldsValidator = validatorConfiguration.buildValidatorFactory().getValidator();
    }
}
