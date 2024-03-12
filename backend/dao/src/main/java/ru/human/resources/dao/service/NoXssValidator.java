package ru.human.resources.dao.service;

import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import ru.human.resources.common.data.validation.NoXss;
import org.owasp.validator.html.AntiSamy;

/**
 * @author Anton Kravchenkov
 * @since 11.10.2021
 */
public class NoXssValidator implements ConstraintValidator<NoXss, Object> {

    private static final AntiSamy xssChecker = new AntiSamy();
    private static Policy xssPolicy;

    @Override
    public void initialize(NoXss constraintAnnotation) {
        if (xssPolicy == null) {
            xssPolicy = Optional.ofNullable(getClass().getClassLoader().getResourceAsStream("xss-policy.xml"))
                .map(inputStream -> {
                    try {
                        return Policy.getInstance(inputStream);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new IllegalStateException("XSS policy file not found"));
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (!(o instanceof String) || ((String) o).isEmpty()) {
            return true;
        }
        try {
            return xssChecker.scan((String) o, xssPolicy).getNumberOfErrors() == 0;
        } catch (ScanException | PolicyException e) {
            return false;
        }
    }
}
