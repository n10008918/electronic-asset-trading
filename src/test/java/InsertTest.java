import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

/**
 *  Insert Tests
 */
public class InsertTest {
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

    //Test insert asset that isn't in database yet
    @Test
    public void insertAsset() throws Exception {
        pst = mockCon.prepareStatement("insert ignore into assets values ('aTest','org00002','Asset Test',150,500,0)");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select * from assets where asset_id='aTest'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("org_id"), "org00002");
        assertEquals(rs.getString("asset_name"), "Asset Test");
        assertEquals(rs.getInt("asset_quantity"), 150);
        assertEquals(rs.getInt("asset_cost"), 500);
        assertEquals(rs.getInt("is_buy"), 0);
    }

    //Test insert user that isn't in database yet
    @Test
    public void insertUser() throws Exception {
        pst = mockCon.prepareStatement("insert ignore into users values ('uTest','org00002','userTest','password',1)");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select * from users where user_id='uTest'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("org_id"), "org00002");
        assertEquals(rs.getString("username"), "userTest");
        assertEquals(rs.getString("user_password"), "password");
        assertEquals(rs.getInt("is_admin"), 1);
    }

    //Insert organization that isn't in database
    @Test
    public void insertOrg() throws Exception {
        pst = mockCon.prepareStatement("insert ignore into orgs values ('orgTest','Test Organization',500,5)");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select * from orgs where org_id='orgTest'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("org_name"), "Test Organization");
        assertEquals(rs.getInt("total_credits"), 500);
        assertEquals(rs.getInt("total_assets"), 5);
    }

    //Insert a trade that isn't in database
    @Test
    public void insertTrade() throws Exception {
        pst = mockCon.prepareStatement("insert ignore into trades values ('receipt','org00002','2020-12-1',500,50,5,1)");
        rs = pst.executeQuery();
        pst = mockCon.prepareStatement("select * from trades where receipt_number='receipt'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("org_id"), "org00002");
        assertEquals(rs.getInt("asset_name"), 500);
        assertEquals(rs.getInt("asset_quantity"), 50);
        assertEquals(rs.getInt("asset_cost"), 5);
        assertEquals(rs.getInt("asset_buy"), 1);
    }


}
