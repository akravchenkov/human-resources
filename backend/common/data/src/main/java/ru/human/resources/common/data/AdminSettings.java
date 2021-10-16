package ru.human.resources.common.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminSettings extends BaseData {

    private static final long serialVersionUID = -8099136441326078263L;

    private String key;
    private transient JsonNode jsonValue;
}
