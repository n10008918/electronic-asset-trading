import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.Assert.*;

/**
 *  Select Tests
 */
public class SelectTest {
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

    //Test Select all asset and check if there's any
    @Test
    public void SelectAll_Assets() throws Exception {
        pst = mockCon.prepareStatement("select * from assets");
        rs = pst.executeQuery();
        rs.next();
        assertNotNull(rs.getString("asset_id"));
    }

    //Test select all asset from one organization and check all their asset matches
    @Test
    public void SelectAll_oneOrg() throws Exception  {
        pst = mockCon.prepareStatement("select asset_id from assets where org_id = 'org00001'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("asset_id"), "a00001");
        rs.next();
        assertEquals(rs.getString("asset_id"), "a00002");
        rs.next();
        assertEquals(rs.getString("asset_id"), "a00003");
        rs.next();
        assertEquals(rs.getString("asset_id"), "a00004");
    }

    //Select detail of a user
    @Test
    public void Select_userDetail() throws Exception {
        pst = mockCon.prepareStatement("select * from users where user_id = 'u00001'");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("org_id"), "org00001");
        assertEquals(rs.getString("username"), "user1");
        assertEquals(rs.getInt("is_admin"), 1);

    }

    //Select all users and check if theres any
    @Test
    public void Select_allUser() throws Exception {
        pst = mockCon.prepareStatement("select * from users");
        rs = pst.executeQuery();
        rs.next();
        assertNotNull(rs.getString("user_id"));
    }

    //Select all users admin
    @Test
    public void Select_admin() throws Exception {
        pst = mockCon.prepareStatement("select * from users where is_admin = 1");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("user_id"), "u00001");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00002");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00003");
    }

    //Select all users not admin
    @Test
    public void Select_notAdmin() throws Exception {
        pst = mockCon.prepareStatement("select * from users where is_admin = 0");
        rs = pst.executeQuery();
        rs.next();
        assertEquals(rs.getString("user_id"), "u00004");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00006");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00007");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00008");
        rs.next();
        assertEquals(rs.getString("user_id"), "u00009");
    }

    //Test if we can select all trades
    @Test
    public void Select_Trades() throws Exception {
        pst = mockCon.prepareStatement("select *,count(*) as rowcount from trades");
        rs = pst.executeQuery();
        rs.next();
        assertTrue(rs.getInt("rowcount")>=0);
    }
}
