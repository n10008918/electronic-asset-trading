package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 *  Trade confirmation is the page that shows a user the asset they have successfully traded
 */
public class TradeConfirmation extends JFrame{
    private JButton TRADESButton;
    private JButton ACCOUNTButton;
    private JButton SIGNOUTButton;
    private JPanel tradePanel;
    private JButton assetButton;
    private JLabel date;
    private JLabel assetItem;
    private JLabel quantity;
    private JLabel cost;
    private JLabel receipt;
    private JLabel buyerSeller;
    private JLabel credits;
    private String receiptID;

    ResultSet rs = null;
    PreparedStatement pst = null;


    /**
     * @param title
     * The title of the window
     * @param receipt
     * The Receipt number from the transaction
     */
    public TradeConfirmation(String title , String receipt) {

        super(title);
        this.receiptID = receipt;

        // setup and packs the form
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(tradePanel);
        this.pack();

        //Button to go to Trade Page
        TRADESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                Trades tradeHistoryFrame = new Trades("Electronic Asset Trading");
                tradeHistoryFrame.setSize(500, 650);
                tradeHistoryFrame.setVisible(true);
            }
        });

        //Button to go to Account page
        ACCOUNTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                AccountSettings userAccountFrame = new AccountSettings("Electronic Asset Trading");
                userAccountFrame.setSize(500, 650);
                userAccountFrame.setVisible(true);
            }
        });

        //Button to signout
        SIGNOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response ==JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });

        //Button to go to asset page
        assetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Assets newFrame = null;
                try {
                    newFrame = new Assets("Electronic Asset Trading");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                newFrame.setSize(500, 650);
                newFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {

        // Create Application
        JFrame frame = new TradeConfirmation("Electronic Asset Trading", "test");

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);


    }

    // Creating the components in the window
    private void createUIComponents() {
        // variable declarations
        String orgId = "";
        String actualDate = "";
        String assetName = "";
        int assetQuantity = 0;
        int assetCost = 0;
        int BuyerCredit = 0;


        //Get information of receipt
        try {
            String sql = "select * from trades where receipt_number = \""+this.receiptID+"\"";
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            //Get OrgID of the receipt
            orgId = rs.getString("org_id");
            //Get date of receipt
            Date date = rs.getDate("transaction_date");
            String pattern = "MM/dd/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            actualDate = df.format(date);
            //Get name of asset
            assetName = rs.getString("asset_name");
            //Get quantity
            assetQuantity = rs.getInt("asset_quantity");
            //Get cost
            assetCost = rs.getInt("asset_cost");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        receipt = new JLabel("Receipt: "+ receiptID);
        date = new JLabel("Date of Transaction: " + actualDate);
        assetItem = new JLabel("Name of Asset: "+ assetName);
        quantity = new JLabel("Quantity: "+ Integer.toString(assetQuantity));
        cost = new JLabel("Cost of Asset: "+ Integer.toString(assetCost));
        buyerSeller = new JLabel("Buyer Organization: " + orgId);


        try {
            String sql = "select total_credits from orgs where org_id = \""+orgId+"\"";
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            BuyerCredit = rs.getInt("total_credits");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        credits = new JLabel("Available Credit: "+ Integer.toString(BuyerCredit));


    }
}
