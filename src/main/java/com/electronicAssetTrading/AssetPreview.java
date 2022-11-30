package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

/**
 *  The main purpose is to produce a confirmation page, allow the users to verify their
 *  buy or selling option before confirming the transaction.
 *
 *  The class comprises of large numbers of private fields, these include the JLabel, JPanel, JButton etc...
 *  which will form the GUI and there’s no need to go into details how this work.
 *
 */
public class AssetPreview extends JFrame {
    private JButton assetsButton;
    private JButton tradesButton;
    private JButton accountButton;
    private JButton signoutButton;
    private JButton buyButton;
    private JButton sellButton;
    private JLabel quantity;
    private JButton confirmButton;
    private JPanel assetPreview;
    private JLabel itemName;
    private JLabel quantityAvailable;
    private JLabel COSTLabel;
    private JLabel credits;

    public final String assetId ;
    public final String orgId ;
    private final int qtyBuy;
    private int quantityLeft;
    private int qtyAfter;
    private int sellerCredit;
    private int totalCost;
    private int buyerCredit;
    private String username;
    private String buyerOrg;
    private int cost;
    private String name;

    ResultSet rs = null;
    PreparedStatement pst = null;

    /**
     * @param title
     * @param assetId
     * @param orgId
     * @param qtyBuy
     */
    public AssetPreview(String title, String assetId, String orgId, int qtyBuy)  {
        // sets the title of the application
        super(title);

        //GET parameter to generate the GUI
        this.assetId = assetId;
        this.qtyBuy = qtyBuy;
        this.orgId = orgId;

        //Retrieve information
        //Prepare an sql
        String sql = "select asset_name, asset_quantity, asset_cost, is_buy, org_id FROM assets where asset_id = \"" + assetId + "\" and org_id =\"" + orgId + "\"";

        Login login = new Login("electronic Asset Trading");
        username = (String) Login.userName;

        //GET THE ASSET DATA FROM DB
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            this.name = rs.getString("asset_name");
            this.quantityLeft = rs.getInt("asset_quantity");
            this.cost = rs.getInt("asset_cost");
            this.totalCost = cost * qtyBuy;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Get seller credit
        sql = "select total_credits from orgs where org_id = \""+orgId+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            this.sellerCredit = rs.getInt("total_credits");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Get buyer credit
        sql = "select org_id from users where username = \""+username+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            this.buyerOrg = rs.getString("org_id");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sql = "select total_credits from orgs where org_id = \""+buyerOrg+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            this.buyerCredit = rs.getInt("total_credits");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // setup and packs the form
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(assetPreview);
        this.pack();

        // Signs the user out and closes the application
        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "The transaction will be cancelled. Are you sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response ==JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });

        // Button to return to the user account settings page
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to cancel the transaction?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION){
                    dispose();
                    AccountSettings newFrame = new AccountSettings("Electronic Asset Trading");
                    newFrame.setSize(500, 650);
                    newFrame.setVisible(true);
                }
            }
        });

        // Button to return to the trades overview page
        tradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to cancel the transaction?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION){
                    dispose();
                    Trades tradeHistoryFrame = new Trades("Electronic Asset Trading");
                    tradeHistoryFrame.setSize(500, 650);
                    tradeHistoryFrame.setVisible(true);
                }
            }
        });
        // Button to return to the asset listing page
        assetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to cancel the transaction?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION){
                    dispose();
                    Assets newFrame = null;
                    try {
                        newFrame = new Assets("Electronic Asset Trading");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    newFrame.setSize(1100, 550);
                    newFrame.setVisible(true);
                }
            }
        });
        // Button to complete the transaction
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION){
                    // BUY ASSET
                    if (!buyButton.isEnabled()) {
                        //Check for conditions before making transaction
                        if (buyerCredit < totalCost)
                        {
                            JOptionPane.showMessageDialog(null, "Not enough credit");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        else if (qtyBuy > quantityLeft) {
                            JOptionPane.showMessageDialog(null, "Not enough asset available");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        else if (buyerOrg.equals(orgId))
                        {
                            JOptionPane.showMessageDialog(null, "You can't make transaction with your own organization");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        //Conditions cleared, transaction allowed
                        else {
                            //Calculate Quantity remaining of the asset after purchase
                            qtyAfter = quantityLeft - qtyBuy;
                            //Update the assets table
                            updateAssetsBuy(qtyAfter,assetId,orgId,buyerOrg,name);

                            //Update the seller/Buyer credit
                            updateBuyCred(sellerCredit,totalCost,orgId,buyerOrg);

                            //Generate receipt
                            generateReceipt(buyerOrg,name,qtyBuy,cost, 1);

                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                            dispose();
                            Trades newTradesFrame = new Trades("Electronic Asset Trading");
                            newTradesFrame.setSize(500, 650);
                            newTradesFrame.setVisible(true);
                        }
                    }
                    //SELL ASSET
                    else if (!sellButton.isEnabled()) {
                        //Check pre-condition
                        String sql = "select asset_quantity from assets where org_id = \""+buyerOrg+"\" and asset_name=\""+name+"\"";
                        int quantity = 0;
                        try {
                            pst = ConnectToDb.con.prepareStatement(sql);
                            rs = pst.executeQuery();
                            rs.next();
                            quantity = rs.getInt("asset_quantity");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        if (buyerOrg.equals(orgId)) {
                            JOptionPane.showMessageDialog(null, "You can't make transaction with your own organization");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        else if (qtyBuy*cost > sellerCredit){
                            JOptionPane.showMessageDialog(null, "Buyer lack credit to buy");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        else if (qtyBuy > quantity)
                        {
                            JOptionPane.showMessageDialog(null, "Not enough asset to sell");
                            dispose();
                            Assets assets = null;
                            try {
                                assets = new Assets("Electronic Asset Trading");
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            assets.setSize(500,650);
                            assets.setVisible(true);
                        }
                        //Condition cleared
                        else {
                            //Seller and buyer quantity after selling
                            qtyAfter = quantity - qtyBuy;
                            updateAssetsSell(qtyAfter,quantityLeft,name,buyerOrg,qtyBuy,orgId);

                            //Update the buyer/seller credit
                            updateSellCred(sellerCredit,buyerCredit,totalCost,orgId,buyerOrg);

                            //Generate receipt
                            generateReceipt(buyerOrg,name,qtyBuy,cost,0);

                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                            dispose();
                            Trades newTradesFrame = new Trades("Electronic Asset Trading");
                            newTradesFrame.setSize(500, 650);
                            newTradesFrame.setVisible(true);

                        }
                    }
                }
            }


        });

        // Buy Button
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyButton.setEnabled(false);
                sellButton.setEnabled(true);
            }
        });

        // Sell Button
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyButton.setEnabled(true);
                sellButton.setEnabled(false);
            }
        });
    }

    /**
     * @param sellerCredit
     * The credit of the seller
     * @param buyerCredit
     * The credit of the buyers organisation
     * @param totalCost
     * Total cost of the transaction
     * @param orgId
     * Organisation ID of the seller
     * @param buyerOrg
     * Organisation ID of the buyer
     */
    private void updateSellCred(int sellerCredit, int buyerCredit, int totalCost, String orgId, String buyerOrg) {
        String sql = "";
        sql = "update orgs set total_credits = \""+ (sellerCredit-totalCost) +"\" where org_id =\""+ orgId+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        //Update the seller credit (buyerCred here refers to seller in this case)
        sql = "update orgs set total_credits = \""+ (buyerCredit+totalCost) +"\" where org_id =\""+ buyerOrg+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * @param qtyAfter
     * The quantity after the transaction has been processed
     * @param quantityLeft
     * The quantity that is left from the seller after the transaction has been processed
     * @param name
     * The name of the asset
     * @param buyerOrg
     * The buyers organisation
     * @param qtyBuy
     * The quantity of the of assets from the transaction
     * @param orgId
     * The organisations ID
     */
    private void updateAssetsSell(int qtyAfter, int quantityLeft, String name, String buyerOrg, int qtyBuy, String orgId) {
        String sql = "";
        sql = "Update assets set asset_quantity = \""+qtyAfter+"\" where asset_name = \"" + name + "\" and org_id= \""+ buyerOrg+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sql = "update assets set asset_quantity = \""+(quantityLeft+qtyBuy)+"\" where org_id = \""+orgId+"\" and asset_name = \""+name+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * @param buyerOrg
     * The buyers organisation
     * @param name
     * The name of the asset
     * @param qtyBuy
     * Quantity of the transaction
     * @param cost
     * The cost of the transaction
     * @param is_buy
     * Whether the asset has been bought or sold
     */
    private void generateReceipt(String buyerOrg, String name, int qtyBuy, int cost, int is_buy) {
        Random r = new Random();
        int low = 0;
        int high = 1000000;
        int result = r.nextInt(high-low) + low;
        String receipt = "r" + Integer.toString(result);
        try {
            pst = ConnectToDb.con.prepareStatement("insert into trades values (?,?,?,?,?,?,?)");
            pst.setObject(1,receipt);
            pst.setObject(2,buyerOrg);
            pst.setObject(3, LocalDateTime.now());
            pst.setObject(4,name);
            pst.setObject(5,qtyBuy);
            pst.setObject(6,cost);
            pst.setObject(7,is_buy);
            rs = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @param sellerCredit
     * The organisation credit of the seller
     * @param totalCost
     * Total cost of the transaction
     * @param orgId
     * Organisation ID of the seller
     * @param buyerOrg
     * Organisation ID of the buyer
     */
    private void updateBuyCred(int sellerCredit, int totalCost, String orgId, String buyerOrg) {
        String sql = "";
        sql = "update orgs set total_credits = \""+ (sellerCredit+totalCost) +"\" where org_id =\""+ orgId+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Update the buyer credit
        sql = "update orgs set total_credits = \""+ (buyerCredit-totalCost) +"\" where org_id =\""+ buyerOrg+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @param qtyAfter
     * Quantity of the asset after the transaction
     * @param assetId
     * The ID of the asset
     * @param orgId
     * The organisation ID of the seller
     * @param buyerOrg
     * The buyers organisation ID
     * @param name
     * Name of the assert
     */
    private void updateAssetsBuy(int qtyAfter, String assetId, String orgId, String buyerOrg, String name) {
        String sql = "Update assets set asset_quantity = \""+qtyAfter+"\" where asset_id = \"" + assetId + "\" and org_id= \""+ orgId+ "\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Add new asset for the buyer, first check if they already have the asset
        sql = "select asset_name, count(*) as rowcount from assets where org_id = \""+buyerOrg+"\" and asset_name = \""+name+"\"";
        int check = 0;
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            check = rs.getInt("rowcount");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Check if exist
        if (check == 0) {
            try {
                pst = ConnectToDb.con.prepareStatement("Insert into assets values (?,?,?,?,?,?)");
                pst.setObject(1,name+'_'+buyerOrg);
                pst.setObject(2,buyerOrg);
                pst.setObject(3,name);
                pst.setObject(4,qtyBuy);
                pst.setObject(5,cost);
                pst.setObject(6,1);
                rs = pst.executeQuery();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        //UPDATE ASSET IF ALREADY IN DATABASE
        else {
            //Get asset_quantity before adding
            int quantityBefore = 0;
            sql = "select asset_quantity from assets where org_id = \""+buyerOrg+"\" and asset_name = \""+name+"\"";
            try {
                pst = ConnectToDb.con.prepareStatement(sql);
                rs = pst.executeQuery();
                rs.next();
                quantityBefore = rs.getInt("asset_quantity");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                pst = ConnectToDb.con.prepareStatement("update assets set asset_quantity = ? where asset_name = ? and org_id = ?");
                pst.setObject(1,quantityBefore+qtyBuy);
                pst.setObject(2,name);
                pst.setObject(3,buyerOrg);
                rs = pst.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {

        // Create Application
        JFrame frame = new AssetPreview("Electronic Asset Trading", "a00001","org00001", 10);

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);

    }

    //Method to dynamically set Text in the GUI Page
    private void createUIComponents() {
        // Variables declaration
        String name = "";
        int quantityLeft = 0;
        int cost = 0;
        int cred = 0;


        //Prepare an sql
        String sql = "select asset_name, asset_quantity, asset_cost, is_buy, org_id FROM assets where asset_id = \"" + assetId + "\"";

        //GET THE DATA FROM DB
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            name = rs.getString("asset_name");
            quantityLeft = rs.getInt("asset_quantity");
            cost = rs.getInt("asset_cost");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Get current credit
        String sql_cred = "select total_credits from orgs where org_id = \""+orgId+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql_cred);
            rs = pst.executeQuery();
            rs.next();
            cred = rs.getInt("total_credits");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //Get buyerCredit
        Login login = new Login("electronic Asset Trading");
        username = (String) Login.userName;
        sql = "select org_id from users where username = \""+username+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            buyerOrg = rs.getString("org_id");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sql = "select total_credits from orgs where org_id = \""+buyerOrg+"\"";
        try {
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            buyerCredit = rs.getInt("total_credits");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        credits = new JLabel();
        credits.setText("Available Credits: " + Integer.toString(buyerCredit));

        itemName = new JLabel();
        itemName.setText(name);

        quantityAvailable = new JLabel();
        quantityAvailable.setText("Quantity Available: " + Integer.toString(quantityLeft));

        buyButton = new JButton();
        sellButton = new JButton();

        COSTLabel = new JLabel();
        COSTLabel.setText("Total Cost: " + Integer.toString(cost*qtyBuy));

        quantity = new JLabel();
        quantity.setText(Integer.toString((qtyBuy)));
    }
}
