import com.electronicAssetTrading.ConnectToDb;
import org.junit.Before;
import org.junit.Test;
import setup.MockDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 *  Test boundary cases of the application
 */
public class BoundaryTests {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    public static ResultSet rs = null;
    public static ResultSet rs1 = null;
    Connection mockCon;

    @Before
    public void setUp() throws Exception
    {
        mockCon = MockDbConnection.ConnectToMockDb();
        MockDbConnection.persistMockTables();
        MockDbConnection.persistMockEntities();
    }

    // check if the user is an admin
    @Test
    public void userIsAdmin() throws SQLException {
        String sql = "select username, user_password, org_id, is_admin from users where username = \"user1\"";
        pst = MockDbConnection.mockCon.prepareStatement(sql);
        rs = pst.executeQuery();
        rs.next();

        assertTrue(rs.getString("is_admin"),true );
    }

    // check if the user owns asset
    @Test
    public void userOwnAsset() throws SQLException {
        String sql = "select username, user_password, org_id, is_admin from users where username = \"user1\"";
        pst = MockDbConnection.mockCon.prepareStatement(sql);
        rs = pst.executeQuery();
        rs.next();

        String sql1 = "select asset_name, asset_quantity, asset_cost, is_buy, org_id FROM assets where asset_id = \"a00001\"";
        pst1 = MockDbConnection.mockCon.prepareStatement(sql1);
        rs1 = pst1.executeQuery();
        rs1.next();

        assertEquals(rs1.getString("org_id"), rs.getString("org_id"));
    }

    // check if the asset is not already in the DB
    @Test
    public void newAssetUnique() throws SQLException {
        String sql = "select * FROM assets where asset_id = \"a00001\"";
        pst = MockDbConnection.mockCon.prepareStatement(sql);
        rs = pst.executeQuery();
        rs.next();

        assertTrue(rs.getRow() > 0);
    }

    // Check if there is enough assets
    @Test
    public void quantitySufficientToPurchase() throws SQLException {
        String sql = "select total_credits from orgs where org_id = \"org00001\"";
        pst = MockDbConnection.mockCon.prepareStatement(sql);
        rs = pst.executeQuery();
        rs.next();

        String sql1 = "select asset_name, asset_quantity, asset_cost, is_buy, org_id FROM assets where asset_id = \"a00001\"";
        pst1 = MockDbConnection.mockCon.prepareStatement(sql1);
        rs1 = pst1.executeQuery();
        rs1.next();

        assertTrue("Asset Cost less than organisation credits",
                Integer.parseInt(rs1.getString("asset_cost"))
                < Integer.parseInt(rs.getString("total_credits")));
    }


}
