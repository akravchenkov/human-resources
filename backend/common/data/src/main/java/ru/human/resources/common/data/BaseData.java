package ru.human.resources.common.data;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Anton Kravchenkov
 * @since 07.10.2021
 */
@Data
public abstract class BaseData implements Serializable {

    private static final long serialVersionUID = -2732635732137855551L;

    protected Long id;
    protected long createdTime;
}
