import ServerSideApp.UpdateTestMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import setup.MockDbConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 *  Tests updating entities in the DB
 */
public class UpdateTest {
    public UpdateTestMethod updateTestMethod;

    @BeforeAll
    @Test
    public void setup(){
        updateTestMethod = new UpdateTestMethod();
    }


    Connection mockCon;

    @Before
    public void setUp() throws Exception
    {
        mockCon = MockDbConnection.ConnectToMockDb();
        MockDbConnection.persistMockTables();
        MockDbConnection.persistMockEntities();
    }

    // verify that the password has been updated
    @Test
    public void verifyUpdatePassword() throws Exception {
        UpdateTestMethod updateTestMethod = new UpdateTestMethod();
        assertEquals("9a11883317fde3aef2e2432a58c86779",
                updateTestMethod.updatePwd("2756", "user1", mockCon)
                );
    }

    // test whether asset name has been updated
    @Test
    public void updateAssetNamed() throws SQLException {
        UpdateTestMethod updateTestMethod = new UpdateTestMethod();
        assertEquals("Asset 6",
                updateTestMethod.UpdateAssetName("Asset 6", "Asset 1", "org00001", mockCon));
    }

    // toggle admin status
    @Test
    public void updateAdmin() throws SQLException {
        UpdateTestMethod updateTestMethod = new UpdateTestMethod();
        assertEquals(Boolean.TRUE,
                updateTestMethod.updateAdmin("1", "user5", "040b7cf4a55014e185813e0644502ea9", mockCon)
        );
    }

    // update the organisations credit
    @Test
    public void updateCredit() throws SQLException {
        UpdateTestMethod updateTestMethod = new UpdateTestMethod();
        assertEquals(Integer.valueOf("5000"),
                updateTestMethod.updatecredit("5000", "org00001", "Technology Department", mockCon));
    }

    // update the total number of asset
    @Test
    public void updateTotalAsset() throws SQLException {
        UpdateTestMethod updateTestMethod = new UpdateTestMethod();
        assertEquals(Integer.valueOf("6"),
                updateTestMethod.updatetotalasset("6", "org00001", "Technology Department", mockCon));
    }

}
