
import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *  Server Connection Test
 */
public class ServerConnectionTest {

    Connection mockCon;

    @Before
    public void setUp() throws Exception
    {
        mockCon = MockDbConnection.ConnectToMockDb();
        MockDbConnection.persistMockTables();
        MockDbConnection.persistMockEntities();
    }

    // Verify the connection to the DB is open
    @Test
    public void checkConnectionEstablished() throws SQLException {
        assertFalse(mockCon.isClosed());
    }

}
