import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

/**
 *  Delete Unit Tests
 */
public class DeleteTest {
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

    //Test Delete user
    @Test
    public void deleteUser() throws Exception {
        pst = mockCon.prepareStatement("delete from users where user_id='user00009'");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select *,count(*) as rowcount from users where user_id = 'user00009'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getInt("rowcount"),0);
    }

    //Test delete asset
    @Test
    public void deleteAsset() throws Exception {
        pst = mockCon.prepareStatement("delete from assets where asset_id='a000020'");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select *,count(*) as rowcount from assets where asset_id='a000020'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getInt("rowcount"),0);
    }

}
