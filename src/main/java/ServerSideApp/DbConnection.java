package ServerSideApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *  DbConnection takes the details entered by the user to connect to the server
 */
public class DbConnection {

    //Database Connection
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    public static Connection ConnectToDb() {

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");

            String DB_URL = "jdbc:mariadb://" + MariaDbCredentials.SERVER + ":" + MariaDbCredentials.PORT + "/asset_trading";
            Connection con = DriverManager.getConnection(DB_URL, DbConnection.MariaDbCredentials.USER, DbConnection.MariaDbCredentials.PASS);

            MariaDbCredentials.SUCCESS = true;
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql1 = SetupDb.useDb;
            stmt.execute(sql1);

            return con;

        } catch (Exception e) {
            MariaDbCredentials.SUCCESS = false;
            e.printStackTrace();
            return null;
        }
    }

    //  Database credentials
    public static class MariaDbCredentials {
        public static String USER = null;
        public static String PASS = null;
        public static String SERVER = null;
        public static String PORT = null;
        public static Boolean SUCCESS = false;
    }

}
