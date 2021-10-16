package ru.human.resources.dao.util.mapping;

import java.util.Properties;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public class JsonStringType
    extends AbstractSingleColumnStandardBasicType<Object>
    implements DynamicParameterizedType {

    private static final long serialVersionUID = -1881582739928677716L;

    public JsonStringType() {
        super(
            JsonStringSqlTypeDescriptor.INSTANCE,
            new JsonTypeDescriptor()
        );
    }

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public void setParameterValues(Properties properties) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(properties);
    }
}
