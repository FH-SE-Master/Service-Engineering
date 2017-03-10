package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.SimpleCustomerDao;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.util.JdbcUtil;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Stateless()
// Variant 1a
@Resource(name = "jdbc/FhBayDS", mappedName = "java:jboss/datasources/FhBayDS", type = DataSource.class)
public class SimpleCustomerDaoBean2 implements SimpleCustomerDao {

    public DataSource getDataSource() {

        try {
            // Variant 1a
            Context ctx = new InitialContext();
            return (DataSource) ctx.lookup("java:comp/env/jdbc/FhBayDS");
        } catch (NamingException e) {
            throw new EJBException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
    }

    @Override
    public void persist(Customer cust) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // version 1 (comment out for version 2)
            // DataSource dataSource = getDataSource();
            conn = getDataSource().getConnection();
            stmt = conn
                    .prepareStatement("INSERT INTO SimpleCustomer (firstname, lastname, username, password, email) VALUES (?, ?, ?, ?, ?)",
                                      Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cust.getFirstname());
            stmt.setString(2, cust.getLastname());
            stmt.setString(3, cust.getUsername());
            stmt.setString(4, cust.getPassword());
            stmt.setString(5, cust.getEmail());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected != 1)
                throw new EJBException("Insert into table SimpleCustomer failed.");

            cust.setId(JdbcUtil.getUniqueKey(stmt));
        } catch (SQLException e) {
            throw new EJBException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new EJBException(e);
            }
        }
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }
}
