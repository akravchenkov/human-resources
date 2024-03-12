package ru.human.resources.common.data;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Anton Kravchenkov
 * @since 08.10.2021
 */
public interface HasAdditionalInfo {

    JsonNode getAdditionalInfo();

}
