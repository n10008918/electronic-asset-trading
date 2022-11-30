package setup;

import java.util.ArrayList;
import java.util.List;

/**
 *  Create all the entities to persist into the DB
 */
public class MockEntities {

    /* Organisation Departments */
    public static List<String> orgData = new ArrayList<>();

    /* Application Users */
    public static List<String> technologyAdmins = new ArrayList<>();
    public static List<String> financeDP = new ArrayList<>();
    public static List<String> marketingDP = new ArrayList<>();
    public static List<String> hrDP = new ArrayList<>();

    /* Trading Assets */
    public static List<String> technologyAssets = new ArrayList<>();
    public static List<String> financeAssets = new ArrayList<>();
    public static List<String> marketingAssets = new ArrayList<>();
    public static List<String> hrAssets = new ArrayList<>();

    /* Traded Receipts */
    public final static List<String> assetTrades = new ArrayList<>();

    /* Asset Trading Data */


    //Add all the entities to the lists created
    public static void addEntities(){

        //Departments
        orgData.add("INSERT IGNORE INTO mock_asset_trading.orgs(org_id,org_name,total_credits,total_assets) VALUES (\"org00001\",\"Technology Department\", 50000000, 5);");
        orgData.add("INSERT IGNORE INTO mock_asset_trading.orgs(org_id,org_name,total_credits,total_assets) VALUES (\"org00002\",\"Finance Department\", 50000000, 5);");
        orgData.add("INSERT IGNORE INTO mock_asset_trading.orgs(org_id,org_name,total_credits,total_assets) VALUES (\"org00003\",\"Marketing Department\", 50000000, 5);");
        orgData.add("INSERT IGNORE INTO mock_asset_trading.orgs(org_id,org_name,total_credits,total_assets) VALUES (\"org00004\",\"HR Department\", 50000000, 5);");

        //Users
        //Admins
        technologyAdmins.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00001\", \"org00001\",\"user1\", \"040b7cf4a55014e185813e0644502ea9\", 1);");
        technologyAdmins.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00002\", \"org00001\",\"user2\", \"040b7cf4a55014e185813e0644502ea9\", 1);");
        technologyAdmins.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00003\", \"org00001\",\"user3\", \"040b7cf4a55014e185813e0644502ea9\", 1);");

        //Finance DP
        financeDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00004\", \"org00002\",\"user4\", \"040b7cf4a55014e185813e0644502ea9\", 0);");
        financeDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00005\", \"org00002\",\"user5\", \"040b7cf4a55014e185813e0644502ea9\", 0);");

        //Marketing DP
        marketingDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00006\", \"org00003\",\"user6\", \"040b7cf4a55014e185813e0644502ea9\", 0);");
        marketingDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00007\", \"org00003\",\"user7\", \"040b7cf4a55014e185813e0644502ea9\", 0);");

        //HR DP
        hrDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00008\", \"org00004\",\"user8\", \"040b7cf4a55014e185813e0644502ea9\", 0);");
        hrDP.add("INSERT IGNORE INTO mock_asset_trading.users(user_id,org_id,username,user_password,is_admin) VALUES (\"u00009\", \"org00004\",\"user9\", \"040b7cf4a55014e185813e0644502ea9\", 0);");

        //Assets
        technologyAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00001\", \"org00001\", \"Asset 1\", 1 , 500, 0);");
        technologyAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00002\", \"org00001\", \"Asset 2\", 1 , 500, 0);");
        technologyAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00003\", \"org00001\", \"Asset 3\", 1 , 500, 0);");
        technologyAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00004\", \"org00001\", \"Asset 4\", 1 , 500, 0);");
        technologyAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00005\", \"org00001\", \"Asset 5\", 1 , 500, 0);");

        financeAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00006\", \"org00002\", \"Asset 6\", 1 , 500, 0);");
        financeAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00007\", \"org00002\", \"Asset 7\", 1 , 500, 0);");
        financeAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00008\", \"org00002\", \"Asset 8\", 1 , 500, 0);");
        financeAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a00009\", \"org00002\", \"Asset 9\", 1 , 500, 0);");
        financeAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000010\", \"org00002\", \"Asset 10\", 1 , 500, 0);");

        marketingAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000011\", \"org00003\", \"Asset 11\", 1 , 500, 0);");
        marketingAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000012\", \"org00003\", \"Asset 12\", 1 , 500, 0);");
        marketingAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000013\", \"org00003\", \"Asset 13\", 1 , 500, 0);");
        marketingAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000014\", \"org00003\", \"Asset 14\", 1 , 500, 0);");
        marketingAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000015\", \"org00003\", \"Asset 15\", 1 , 500, 0);");

        hrAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000016\", \"org00004\", \"Asset 16\", 1 , 500, 0);");
        hrAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000017\", \"org00004\", \"Asset 17\", 1 , 500, 0);");
        hrAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000018\", \"org00004\", \"Asset 18\", 1 , 500, 0);");
        hrAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000019\", \"org00004\", \"Asset 19\", 1 , 500, 0);");
        hrAssets.add("INSERT IGNORE INTO mock_asset_trading.assets(asset_id,org_id,asset_name,asset_quantity,asset_cost,is_buy) VALUES (\"a000020\", \"org00004\", \"Asset 20\", 1 , 500, 0);");

        //Trades
        assetTrades.add("INSERT IGNORE INTO mock_asset_trading.trades(receipt_number,org_id, transaction_date, asset_name, asset_quantity, asset_cost, asset_buy) VALUES (\"r00001\", \"org00001\", current_date(), \"Asset 16\", 1 , 500, 1);");
        assetTrades.add("INSERT IGNORE INTO mock_asset_trading.trades(receipt_number,org_id, transaction_date, asset_name, asset_quantity, asset_cost, asset_buy) VALUES (\"r00002\", \"org00001\", current_date(), \"Asset 17\", 1 , 500, 1);");
        assetTrades.add("INSERT IGNORE INTO mock_asset_trading.trades(receipt_number,org_id, transaction_date, asset_name, asset_quantity, asset_cost, asset_buy) VALUES (\"r00003\", \"org00001\", current_date(), \"Asset 18\", 1 , 500, 1);");
        assetTrades.add("INSERT IGNORE INTO mock_asset_trading.trades(receipt_number,org_id, transaction_date, asset_name, asset_quantity, asset_cost, asset_buy) VALUES (\"r00004\", \"org00001\", current_date(), \"Asset 19\", 1 , 500, 1);");



    }
}
