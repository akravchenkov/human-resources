package ru.human.resources.user.service.controller;

import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ru.human.resources.common.data.exception.HumanResourcesErrorCode;
import ru.human.resources.common.data.exception.HumanResourcesException;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.common.data.page.SortOrder;

import java.util.Locale;

/**
 * @author Anton Kravchenkov
 * @since 20.07.2021
 */
@Slf4j
public abstract class BaseController {

    // TODO Add exception
    PageLink createPageLink(int pageSize, int page, String textSearch, String sortProperty,
        String sortOrder) throws Exception {
        if (!StringUtils.isEmpty(sortProperty)) {
            SortOrder.Direction direction = SortOrder.Direction.ASC;
            if (!StringUtils.isEmpty(sortOrder)) {
                try {
                    direction = SortOrder.Direction.valueOf(sortOrder.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new Exception();
                }
            }
            SortOrder sort = new SortOrder(sortProperty, direction);
            return new PageLink(pageSize, page, textSearch, sort);
        } else {
            return new PageLink(pageSize, page, textSearch);
        }
    }

    <T> T checkNotNull(T reference) throws HumanResourcesException {
        if (reference == null) {
            throw new HumanResourcesException("Request item wasn't found!",
                HumanResourcesErrorCode.ITEM_NIT_FOUND);
        }
        return reference;
    }

    HumanResourcesException handleException(Exception exception) {
        return handleException(exception, true);
    }

    private HumanResourcesException handleException(Exception exception, boolean logException) {
        if (logException) {
            log.error("Error [{}]", exception.getMessage(), exception);
        }

        String cause = "";
        if (exception.getCause() != null) {
            cause = exception.getCause().getClass().getCanonicalName();
        }

        if (exception instanceof HumanResourcesException) {
            return (HumanResourcesException) exception;
        } else if (exception instanceof IllegalArgumentException) {
            return new HumanResourcesException(exception.getMessage(),
                HumanResourcesErrorCode.BAD_REQUEST_PARAMS);
        } else {
            return new HumanResourcesException(exception.getMessage(),
                HumanResourcesErrorCode.GENERAL);
        }

    }

    protected User getCurrentUser() throws HumanResourcesException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().getClass().getName());
        if (authentication != null) {
            return (User) authentication.getPrincipal();
        } else {
            throw new HumanResourcesException("You aren't authorizednto perform this operation",
                HumanResourcesErrorCode.AUTHENTICATION);
        }
    }

}
