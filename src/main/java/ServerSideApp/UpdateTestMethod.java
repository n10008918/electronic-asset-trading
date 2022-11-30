package ServerSideApp;

import ServerSideApp.HashPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *  Update Test Method
 */
public class UpdateTestMethod {
    ResultSet rs16 = null;
    ResultSet rs17 = null;

    PreparedStatement pst16 = null;
    PreparedStatement pst17 = null;

    /**
     *
     * @param totalasset
     * @param orgid
     * @param orgname
     * @param con
     * @return
     * @throws SQLException
     */
    public Object updatetotalasset (Object totalasset, Object orgid, Object orgname, Connection con) throws SQLException {
        pst16 = con.prepareStatement ("Update orgs set total_assets = ? where org_id = ? and org_name = ?");
        pst16.setObject(1, totalasset);
        pst16.setObject(2, orgid);
        pst16.setObject(3, orgname);
        rs16 = pst16.executeQuery();
        rs16.next();

        pst17 = con.prepareStatement("Select * from orgs where org_id = ? and org_name = ?");
        pst17.setObject(1, orgid);
        pst17.setObject(2, orgname);
        rs17 = pst17.executeQuery();
        rs17.next();
        Object newassetnum =  rs17.getObject("total_assets");
        return newassetnum;
    }



    ResultSet rs2 = null;
    ResultSet rs9 = null;
    ResultSet rs10 = null;
    ResultSet rs11 = null;
    ResultSet rs12 = null;
    ResultSet rs13 = null;

    PreparedStatement pst2 = null;
    PreparedStatement pst9 = null;
    PreparedStatement pst10 = null;
    PreparedStatement pst11 = null;
    PreparedStatement pst12 = null;
    PreparedStatement pst13 = null;

    /**
     *
     * @param newPwd
     * @param username
     * @param con
     * @return
     * @throws SQLException
     */
    public Object updatePwd(Object newPwd, Object username, Connection con) throws SQLException {

        pst2 = con.prepareStatement("Update users set user_password = ? where username = ?");
        pst2.setObject(1, HashPassword.StringToHash((String) newPwd));
        pst2.setObject(2, username);
        rs2 = pst2.executeQuery();
        rs2.next();

        pst9 = con.prepareStatement("Select * from users where user_password = ? and username = ?");
        pst9.setObject(1, HashPassword.StringToHash((String) newPwd));
        pst9.setObject(2, username);
        rs9 = pst9.executeQuery();
        rs9.next();

        Object newpwd = rs9.getObject("user_password");
            //rs9.next();
        return newpwd;
    }

    /**
     *
     * @param assetname
     * @param asset_name1
     * @param orgid
     * @param con
     * @return
     * @throws SQLException
     */
    public Object UpdateAssetName(Object assetname, Object asset_name1, Object orgid, Connection con) throws SQLException {
        pst10 = con.prepareStatement("Update assets set asset_name = ? where asset_name = ? and org_id = ?");
        pst10.setObject(1, assetname);
        pst10.setObject(2, asset_name1);
        pst10.setObject(3, orgid);
        rs10 = pst10.executeQuery();
        rs10.next();

        pst11 = con.prepareStatement("Select * from assets where asset_name = ? and org_id = ?");
        pst11.setObject(1, assetname);
        pst11.setObject(2, orgid);
        rs11 = pst11.executeQuery();
        rs11.next();
        Object newasset = rs11.getObject("asset_name");
        return newasset;
    }

    /**
     *
     * @param updateadmin
     * @param username
     * @param password
     * @param con
     * @return
     * @throws SQLException
     */
    public Object updateAdmin (Object updateadmin, Object username, Object password, Connection con) throws SQLException {
        pst12 = con.prepareStatement("Update users set is_admin = ? where username = ? and user_password = ?");
        pst12.setObject(1, updateadmin);
        pst12.setObject(2, username);
        pst12.setObject(3, password);
        rs12 = pst12.executeQuery();
        rs12.next();

        pst13 = con.prepareStatement("Select * from users where username = ? and user_password = ?");
        pst13.setObject(1, username);
        pst13.setObject(2, password);
        rs13 = pst13.executeQuery();
        rs13.next();
        Object newAdmin = rs13.getObject("is_admin");
        return newAdmin;
    }

    ResultSet rs14 = null;
    ResultSet rs15 = null;

    PreparedStatement pst14 = null;
    PreparedStatement pst15 = null;

    /**
     *
     * @param totalcredit
     * @param orgid
     * @param orgname
     * @param con
     * @return
     * @throws SQLException
     */
    public Object updatecredit (Object totalcredit, Object orgid, Object orgname, Connection con) throws SQLException {
        pst14 = con.prepareStatement ("Update orgs set total_credits = ? where org_id = ? and org_name = ?");
        pst14.setObject(1, totalcredit);
        pst14.setObject(2, orgid);
        pst14.setObject(3, orgname);
        rs14 = pst14.executeQuery();
        rs14.next();

        pst15 = con.prepareStatement("Select * from orgs where org_id = ? and org_name = ?");
        pst15.setObject(1, orgid);
        pst15.setObject(2, orgname);
        rs15 = pst15.executeQuery();
        rs15.next();
        Object newcrdit =  rs15.getObject("total_credits");
        return newcrdit;
    }

    ResultSet rs18 = null;
    ResultSet rs19 = null;

    PreparedStatement pst18 = null;
    PreparedStatement pst19 = null;

    /**
     *
     * @param assetcost
     * @param assetname
     * @param orgid
     * @param con
     * @return
     * @throws SQLException
     */
    public Object Updateprice(Object assetcost, Object assetname, Object orgid, Connection con) throws SQLException {
        pst18 = con.prepareStatement("Update assets set asset_cost = ? where asset_name = ? and org_id = ?");
        pst18.setObject(1, assetcost);
        pst18.setObject(2, assetname);
        pst18.setObject(3, orgid);
        rs18 = pst18.executeQuery();
        rs18.next();

        pst19 = con.prepareStatement("Select * from assets where asset_name = ? and org_id = ?");
        pst19.setObject(1, assetname);
        pst19.setObject(2, orgid);
        rs19 = pst19.executeQuery();
        rs19.next();
        Object newprice = rs19.getObject("asset_cost");
        return newprice;
    }



    ResultSet rs20 = null;
    ResultSet rs21 = null;

    PreparedStatement pst20 = null;
    PreparedStatement pst21 = null;

    /**
     *
     * @param assetquantity
     * @param assetname
     * @param orgid
     * @param con
     * @return
     * @throws SQLException
     */
    public Object updateQnty(Object assetquantity, Object assetname, Object orgid, Connection con) throws SQLException {
        pst20 = con.prepareStatement("Update assets set asset_quantity = ? where asset_name = ? and org_id = ?");
        pst20.setObject(1, assetquantity);
        pst20.setObject(2, assetname);
        pst20.setObject(3, orgid);
        rs20 = pst20.executeQuery();
        rs20.next();

        pst21 = con.prepareStatement("Select * from assets where asset_name = ? and org_id = ?");
        pst21.setObject(1, assetname);
        pst21.setObject(2, orgid);
        rs21 = pst21.executeQuery();
        rs21.next();
        Object newquantity = rs21.getObject("asset_quantity");
        return newquantity;
    }


    ResultSet rs22 = null;
    ResultSet rs23 = null;

    PreparedStatement pst22 = null;
    PreparedStatement pst23 = null;

    /**
     *
     * @param neworgid
     * @param assetname
     * @param orgid
     * @param con
     * @return
     * @throws SQLException
     */

    public Object updateOrg(Object neworgid, Object assetname, Object orgid, Connection con) throws SQLException {
        pst22 = con.prepareStatement("Update assets set org_id = ? where asset_name = ? and org_id = ?");
        pst22.setObject(1, neworgid);
        pst22.setObject(2, assetname);
        pst22.setObject(3, orgid);
        rs22 = pst22.executeQuery();
        rs22.next();

        pst23 = con.prepareStatement("Select * from assets where asset_name = ? and org_id = ?");
        pst23.setObject(1, assetname);
        pst23.setObject(2, orgid);
        rs23 = pst23.executeQuery();
        rs23.next();
        Object neworg = rs23.getObject("org_id");
        //rs23.next();

        return neworg;
    }



}
