import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 *  Hash password testing
 */
public class HashPasswordTest {

    final String hash = "9a11883317fde3aef2e2432a58c86779";

    PreparedStatement pst = null;
    public static ResultSet rs = null;
    Connection mockCon;

    @Before
    public void setUp() throws Exception
    {
        mockCon = MockDbConnection.ConnectToMockDb();
        MockDbConnection.persistMockTables();
        MockDbConnection.persistMockEntities();
    }

    // Verify the hash password is correct
    @Test
    public void verifyCorrectPassword() throws SQLException {
        String sql = "select username, user_password from users where username = \"user1\"";
        pst = mockCon.prepareStatement(sql);

        rs = pst.executeQuery();

        rs.next();

        assertEquals(hash, rs.getString("user_password"));
    }
}
