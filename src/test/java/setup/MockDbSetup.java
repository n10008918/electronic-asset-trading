package setup;

import java.util.ArrayList;
import java.util.List;

/**
 *  Creates the schema and tables for mock DB
 */
public class MockDbSetup {

    public static String createMockDb = "CREATE DATABASE IF NOT EXISTS mock_asset_trading;";

    public static String useMockDb = "USE mock_asset_trading;";

    public static List<String> createTables = new ArrayList<>();

    public static List<String> createOrgTable = new ArrayList<>();

    public static List<String> createUserTable = new ArrayList<>();

    public static List<String> createAssetTable = new ArrayList<>();

    public static List<String> createTradesTable = new ArrayList<>();

    public static void setupDbTables() {

        createTables.add("DROP TABLE IF EXISTS `mock_asset_trading`.`users`;");
        createTables.add("DROP TABLE IF EXISTS `mock_asset_trading`.`assets`;");
        createTables.add("DROP TABLE IF EXISTS `mock_asset_trading`.`trades`;");
        createTables.add("DROP TABLE IF EXISTS `mock_asset_trading`.`orgs`;");

        createOrgTable.add("""
                CREATE TABLE  IF NOT EXISTS `mock_asset_trading`.`orgs` (
                              `org_id` varchar(255) NOT NULL,
                              `org_name` varchar(255) NOT NULL,
                              `total_credits` int NOT NULL,
                              `total_assets` int NOT NULL,
                              PRIMARY KEY  (`org_id`)
                            );""");


        createUserTable.add("""
                CREATE TABLE  IF NOT EXISTS `mock_asset_trading`.`assets` (
                  `asset_id` varchar(255) NOT NULL,
                  `org_id` varchar(255) NOT NULL,
                  `asset_name` varchar(255) NOT NULL,
                  `asset_quantity` int(4) NOT NULL,
                  `asset_cost` int NOT NULL,
                  `is_buy` tinyint(1) NOT NULL,
                  PRIMARY KEY (`asset_id`),
                  FOREIGN KEY (`org_id`)
                  REFERENCES mock_asset_trading.orgs (`org_id`)
                  ON DELETE NO ACTION ON UPDATE NO ACTION
                );""");

        createAssetTable.add("""
                CREATE TABLE  IF NOT EXISTS `mock_asset_trading`.`users` (
                              `user_id` varchar(255) NOT NULL,
                              `org_id` varchar(255) NOT NULL,
                              `username` varchar(255) NOT NULL,
                              `user_password` varchar(255) NOT NULL,
                              `is_admin` tinyint(1) NOT NULL,
                              PRIMARY KEY  (`user_id`),
                              FOREIGN KEY (`org_id`)
                              REFERENCES mock_asset_trading.orgs (`org_id`)
                              ON DELETE NO ACTION ON UPDATE NO ACTION
                            );""");

        createTradesTable.add("""
                CREATE TABLE  IF NOT EXISTS `mock_asset_trading`.`trades` (
                              `receipt_number` varchar(255) NOT NULL,
                              `org_id` varchar(255) NOT NULL,
                              `transaction_status` varchar(255) NOT NULL,
                              `transaction_date` datetime NOT NULL,
                              `asset_name` int NOT NULL,
                              `asset_quantity` int(4) NOT NULL,
                              `asset_cost` int(4) NOT NULL,
                              `asset_buy` tinyint(1) NOT NULL,
                              PRIMARY KEY  (`receipt_number`),
                              FOREIGN KEY (`org_id`)
                              REFERENCES mock_asset_trading.orgs (`org_id`)
                              ON DELETE NO ACTION ON UPDATE NO ACTION
                            );""");


    }

}
