package ru.human.resources.dao.util.mapping;

import java.util.Properties;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;
import ru.human.resources.common.util.JacksonUtil;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public class JsonTypeDescriptor
    extends AbstractTypeDescriptor<Object>
    implements DynamicParameterizedType {

    private static final long serialVersionUID = 5138544631954612213L;

    private Class<?> jsonObjectClass;

    public JsonTypeDescriptor() {
        super(Object.class, new MutableMutabilityPlan<Object>() {
            private static final long serialVersionUID = -501944120112977023L;

            @Override
            protected Object deepCopyNotNull(Object value) {
                return JacksonUtil.clone(value);
            }
        });
    }

    @Override
    public Object fromString(String s) {
        return JacksonUtil.fromString(s, jsonObjectClass);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(Object o, Class<X> aClass, WrapperOptions wrapperOptions) {
        if (o == null) {
            return null;
        }
        if (String.class.isAssignableFrom(aClass)) {
            return (X) toString(o);
        }
        if (Object.class.isAssignableFrom(aClass)) {
            return (X) JacksonUtil.toJsonNode(toString(o));
        }
        throw unknownUnwrap(aClass);
    }

    @Override
    public <X> Object wrap(X x, WrapperOptions wrapperOptions) {
        if (x == null) {
            return null;
        }
        return fromString(x.toString());
    }

    @Override
    public void setParameterValues(Properties properties) {
        jsonObjectClass = ((ParameterType) properties.get(PARAMETER_TYPE))
            .getReturnedClass();
    }
}
