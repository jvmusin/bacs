@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
)

package istu.bacs.db;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.TypeDef;