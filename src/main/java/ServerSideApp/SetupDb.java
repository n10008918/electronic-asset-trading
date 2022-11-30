package ServerSideApp;

import com.electronicAssetTrading.ConnectToDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Activation of the database creating the schema and tables that will be utilised
 */
public class SetupDb {

    // SQL statement to create the database
    public static String createDb = "CREATE DATABASE IF NOT EXISTS asset_trading;";

    // SQL statement to use the table created
    public static String useDb = "USE asset_trading;";

    public static List<String> createTables = new ArrayList<>();

    public static List<String> createOrgTable = new ArrayList<>();

    public static List<String> createUserTable = new ArrayList<>();

    public static List<String> createAssetTable = new ArrayList<>();

    public static List<String> createTradesTable = new ArrayList<>();

    //Creates the tables for the database to be used
    public static void setupDbTables() {

        // Refresh the database with new tables
        createTables.add("DROP TABLE IF EXISTS `asset_trading`.`users`;");
        createTables.add("DROP TABLE IF EXISTS `asset_trading`.`assets`;");
        createTables.add("DROP TABLE IF EXISTS `asset_trading`.`trades`;");
        createTables.add("DROP TABLE IF EXISTS `asset_trading`.`orgs`;");

        // Create the Organisation table
        createOrgTable.add("""
                CREATE TABLE  IF NOT EXISTS `asset_trading`.`orgs` (
                              `org_id` varchar(255) NOT NULL,
                              `org_name` varchar(255) NOT NULL,
                              `total_credits` int NOT NULL,
                              `total_assets` int NOT NULL,
                              PRIMARY KEY  (`org_id`)
                            );""");

        // Create the User table
        createUserTable.add("""
                CREATE TABLE  IF NOT EXISTS `asset_trading`.`assets` (
                  `asset_id` varchar(255) NOT NULL,
                  `org_id` varchar(255) NOT NULL,
                  `asset_name` varchar(255) NOT NULL,
                  `asset_quantity` int(4) NOT NULL,
                  `asset_cost` int NOT NULL,
                  `is_buy` tinyint(1) NOT NULL,
                  PRIMARY KEY (`asset_id`),
                  FOREIGN KEY (`org_id`)
                  REFERENCES asset_trading.orgs (`org_id`)
                  ON DELETE NO ACTION ON UPDATE NO ACTION
                );""");

        // Create the Asset table
        createAssetTable.add("""
                CREATE TABLE  IF NOT EXISTS `asset_trading`.`users` (
                              `user_id` varchar(255) NOT NULL,
                              `org_id` varchar(255) NOT NULL,
                              `username` varchar(255) NOT NULL,
                              `user_password` varchar(255) NOT NULL,
                              `is_admin` tinyint(1) NOT NULL,
                              PRIMARY KEY  (`user_id`),
                              FOREIGN KEY (`org_id`)
                              REFERENCES asset_trading.orgs (`org_id`)
                              ON DELETE NO ACTION ON UPDATE NO ACTION
                            );""");

        // Create the Trade table
        createTradesTable.add("""
                CREATE TABLE  IF NOT EXISTS `asset_trading`.`trades` (
                              `receipt_number` varchar(255) NOT NULL,
                              `org_id` varchar(255) NOT NULL,
                              `transaction_date` datetime NOT NULL,
                              `asset_name` varchar(255) NOT NULL,
                              `asset_quantity` int(4) NOT NULL,
                              `asset_cost` int(4) NOT NULL,
                              `asset_buy` tinyint(1) NOT NULL,
                              PRIMARY KEY  (`receipt_number`),
                              FOREIGN KEY (`org_id`)
                              REFERENCES asset_trading.orgs (`org_id`)
                              ON DELETE NO ACTION ON UPDATE NO ACTION
                            );""");

    }

    /**
     * @throws SQLException if any of the sql statement are incorrect throws an error
     */
    public static void persistTables() throws SQLException {
        Statement stmt = ConnectToDb.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        SetupDb.setupDbTables();

        // Executes the SQL statements to create schema
        // and connect to the correct table
        stmt.execute(SetupDb.createDb);
        stmt.execute(SetupDb.useDb);

        for (String i : SetupDb.createTables) {
            stmt.execute(i);
        }
        for (String i : SetupDb.createOrgTable) {
            stmt.execute(i);
        }
        for (String i : SetupDb.createUserTable) {
            stmt.execute(i);
        }
        for (String i : SetupDb.createAssetTable) {
            stmt.execute(i);
        }
        for (String i : SetupDb.createTradesTable) {
            stmt.execute(i);
        }
    }


}