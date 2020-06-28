package silupa.co.id.securityservice.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UserIdGenerator implements IdentifierGenerator {

@Override
public Serializable generate(SharedSessionContractImplementor session, Object object)
        throws HibernateException {

    String prefix = "US";
    Connection connection = session.connection();

    try {
        Statement statement=connection.createStatement();

        ResultSet rs=statement.executeQuery("select count(user_id) as Id from Users");

        if(rs.next())
        {
            int id=rs.getInt(1)+1;
            String generatedId = prefix + String.format("%03d", new Integer(id));
            return generatedId;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
	}
}