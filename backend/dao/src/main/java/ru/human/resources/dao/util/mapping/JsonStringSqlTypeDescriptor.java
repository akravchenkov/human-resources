package ru.human.resources.dao.util.mapping;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public class JsonStringSqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {

    private static final long serialVersionUID = -2068240862262296584L;

    public static final JsonStringSqlTypeDescriptor INSTANCE =
        new JsonStringSqlTypeDescriptor();

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(
                PreparedStatement preparedStatement,
                X x,
                int i,
                WrapperOptions wrapperOptions) throws SQLException {
                preparedStatement.setString(i, javaTypeDescriptor.unwrap(x, String.class, wrapperOptions));
            }

            @Override
            protected void doBind(
                CallableStatement callableStatement,
                X x,
                String s,
                WrapperOptions wrapperOptions) throws SQLException {
                callableStatement.setString(s, javaTypeDescriptor.unwrap(x, String.class, wrapperOptions));
            }
        };
    }
}
