package ru.human.resources.dao.util.mapping;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

/**
 * @author Anton Kravchenkov
 * @since 10.10.2021
 */
public abstract class AbstractJsonSqlTypeDescriptor implements SqlTypeDescriptor {

    private static final long serialVersionUID = 172215998494562275L;


    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public boolean canBeRemapped() {
        return true;
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            @Override
            protected X doExtract(ResultSet resultSet, String s, WrapperOptions wrapperOptions)
                throws SQLException {
                return javaTypeDescriptor.wrap(
                    resultSet.getObject(s), wrapperOptions
                );
            }

            @Override
            protected X doExtract(CallableStatement callableStatement, int i,
                WrapperOptions wrapperOptions) throws SQLException {
                return javaTypeDescriptor.wrap(
                    callableStatement.getObject(i), wrapperOptions
                );
            }

            @Override
            protected X doExtract(CallableStatement callableStatement, String s,
                WrapperOptions wrapperOptions) throws SQLException {
                return javaTypeDescriptor.wrap(
                    callableStatement.getObject(s), wrapperOptions
                );
            }
        };
    }
}
