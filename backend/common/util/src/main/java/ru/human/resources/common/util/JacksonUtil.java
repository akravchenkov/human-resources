package ru.human.resources.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Anton Kravchenkov
 * @since 09.10.2021
 */
public class JacksonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectNode newObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

}
