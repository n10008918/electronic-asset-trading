package setup;

import ServerSideApp.CreateEntities;
import ServerSideApp.DbConnection;
import ServerSideApp.SetupDb;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.sql.*;

/**
 *  Creates the connection to the mockDB for unit testing
 */
public class MockDbConnection {

    public static Connection mockCon;

    public static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    public static String USER = "root"; // Change to your login for MariaDb
    public static String PASS = "ADD-PASSWORD-HERE"; // Change to your password for MariaDb
    public static String SERVER = "127.0.0.1"; // Change to your connection server for MariaDb
    public static String PORT = "3306";  // Change to your connection port for MariaDb

    @Before
    public void setUp() throws Exception
    {
        mockCon = ConnectToMockDb();
        persistMockTables();
        persistMockEntities();
    }

    public static Connection ConnectToMockDb(){

        try {
            Class.forName(JDBC_DRIVER);

            String DB_URL = "jdbc:mariadb://" + MockDbConnection.SERVER + ":"  + MockDbConnection.PORT + "/asset_trading";
            mockCon = DriverManager.getConnection( DB_URL, MockDbConnection.USER, MockDbConnection.PASS);

            DbConnection.MariaDbCredentials.SUCCESS = true;
            Statement stmt = mockCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql1 = SetupDb.useDb;
            stmt.execute(sql1);

            return mockCon;

        } catch (Exception e) {
            DbConnection.MariaDbCredentials.SUCCESS = false;
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @throws SQLException
     * if any of the sql statement are incorrect throws an error
     */
    public static void persistMockTables() throws SQLException {
        Statement stmt = mockCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        SetupDb.setupDbTables();

        stmt.execute(SetupDb.createDb);
        stmt.execute(SetupDb.useDb);

        for (String i : MockDbSetup.createTables){
            stmt.execute(i);
        }
        for (String i : MockDbSetup.createOrgTable){
            stmt.execute(i);
        }
        for (String i : MockDbSetup.createUserTable){
            stmt.execute(i);
        }
        for (String i : MockDbSetup.createAssetTable){
            stmt.execute(i);
        }
        for (String i : MockDbSetup.createTradesTable){
            stmt.execute(i);
        }
    }

    /**
     * @throws SQLException
     * if any of the sql statement are incorrect throws an error
     */
    public static void persistMockEntities() throws SQLException {
        Statement stmt = mockCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        CreateEntities.addEntities();

        for (String i : MockEntities.orgData) {
            stmt.execute(i);
        }
        for (String i : MockEntities.technologyAdmins) {
            stmt.execute(i);
        }
        for (String i : MockEntities.financeDP) {
            stmt.execute(i);
        }
        for (String i : MockEntities.marketingDP) {
            stmt.execute(i);
        }
        for (String i : MockEntities.hrDP) {
            stmt.execute(i);
        }
        for (String i : MockEntities.technologyAssets) {
            stmt.execute(i);
        }
        for (String i : MockEntities.financeAssets) {
            stmt.execute(i);
        }
        for (String i : MockEntities.marketingAssets) {
            stmt.execute(i);
        }
        for (String i : MockEntities.hrAssets) {
            stmt.execute(i);
        }
        for (String i : MockEntities.assetTrades) {
            stmt.execute(i);
        }
    }


}
