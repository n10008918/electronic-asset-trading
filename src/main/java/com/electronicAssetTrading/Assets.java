package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  This class’s job is to generate a GUI that display a list of all available assets that are currently up for transaction.
 *  It consists of fields as followed: JPanel assetPanel, JButton accountButton, JButton signoutButton, JButton buyButton,
 *  JButton sellButton, JButton updateButton and JButton tradesButton.
 *
 *  Each asset on display will consist of the asset name, its cost, the quantity available and a long with it Buy and Sell button
 *  and a text field for users to input the quantity of asset they want.
 *
 *  On the top of GUI, users will be presented with Assets button, which refer to the current class/screen users are in,
 *  the Trades Button will direct users to another screen that shows history of transactions.
 *  The Account button will transfer users to a screen showing several other information.
 *
 * A Sign Out button which on clicked will create a popup asking if user want to sign out with Yes or No option. In the bottom left corner,
 * an Update button is presented, and its function is to refresh current asset store to see if there’s new transaction added.
 * The Buy/Sell button will transfer users to AssetOverview page where they can confirm their transaction.
 * All these buttons have ActionListener added to perform their described functionality.
 */
public class Assets extends JFrame {
    private JPanel assetPanel;
    private JButton assetsButton;
    private JButton accountButton;
    private JButton signoutButton;
    private JButton buyButton;
    private JButton sellButton;
    private JButton updateButton;
    private JButton tradesButton;
    private JScrollPane displayAssets;
    private JPanel displayAssetsPanel;
    private JLabel totalCredit;
    private JPanel BuySellButtons;
    private JPanel assetQnty;



    ResultSet rs = null;
    PreparedStatement pst = null;

    ResultSet rs1 = null;
    PreparedStatement pst1 = null;

    public static ArrayList<String> a1 = new ArrayList<>();
    public static ArrayList<String> a2 = new ArrayList<>();
    public static ArrayList<Integer> a3 = new ArrayList<>();
    public static ArrayList<Integer> a4 = new ArrayList<>();
    public static ArrayList<Boolean> a5 = new ArrayList<>();

    public static ArrayList<String> specificAsset = new ArrayList<>();


    /**
     * @param title
     * The title of the window
     */

    public Assets(String title) throws SQLException {
        // sets the title of the application
        super(title);

        // setup and packs the form
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(assetPanel);
        this.pack();

        displayAssets.setBorder(BorderFactory.createTitledBorder("Showing List of Assets"));



        pst1 = ConnectToDb.con.prepareStatement("select * from assets where org_id = ?");
        pst1.setObject(1, Login.Org_ID);
        rs1 = pst1.executeQuery();
        while(rs1.next())
        {
            String obj = rs1.getString("asset_name");
            specificAsset.add(obj);
        }

        BuySellButtons.setLayout(new GridLayout(0,6));
        pst = ConnectToDb.con.prepareStatement("select * from assets");
//        pst.setObject(1, login.Org_ID);
        rs = pst.executeQuery();
        while(rs.next()){
            String assetid = rs.getString("asset_id");


            String orgid = rs.getString("org_id");
            a1.add(orgid);

            String assetname = rs.getString("asset_name");
            a2.add(assetname);

            Integer assetquantity = rs.getInt("asset_quantity");
            a3.add(assetquantity);

            Integer assetcost = rs.getInt("asset_cost");
            a4.add(assetcost);

            Boolean isbuy = rs.getBoolean("is_buy");
            a5.add(isbuy);

            JLabel assetName = new JLabel(assetname);
            JLabel QuantityAvail = new JLabel("Quantity of Asset: " + String.valueOf(assetquantity));
            JLabel assetCost = new JLabel("Cost: " + assetcost);

            JButton buybutton = new JButton("BUY");
            JButton sellbutton = new JButton("SELL");


            BuySellButtons.add(assetName);
            BuySellButtons.add(QuantityAvail);
            BuySellButtons.add(assetCost);

            BuySellButtons.add(buybutton);
            BuySellButtons.add(sellbutton);

            JTextField text = new JTextField("0");
            BuySellButtons.add(text);

            if(specificAsset.contains(assetname)){
                buybutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(buybutton, "You cannot buy Asset you own");

                    }
                });
                sellbutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = text.getText().trim();
                        Integer toInt = Integer.parseInt(s);
                        dispose();
                        AssetPreview assetPreview = new AssetPreview("Electronic Asset Trading", assetid, orgid, toInt);
                        assetPreview.setSize(500, 650);
                        assetPreview.setVisible(true);
                    }
                });

            }
            else{
                buybutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = text.getText().trim();
                        Integer toInt = Integer.parseInt(s);
                        dispose();
                        AssetPreview assetPreview = new AssetPreview("Electronic Asset Trading", assetid, orgid, toInt);
                        assetPreview.setSize(500, 650);
                        assetPreview.setVisible(true);


                    }
                });
                sellbutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(sellbutton, "You cannot sell Asset you do not Own");

                    }
                });
            }


        }
        BuySellButtons.validate();
        BuySellButtons.repaint();





        // Signs the user out and closes the application
        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Button to return to the trades overview page
        tradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Close current window
                dispose();

                //Set name of next window
                Trades tradeHistoryFrame = new Trades("Electronic Asset Trading");

                //Set Width and Height of the window
                tradeHistoryFrame.setSize(500, 650);

                //Show window
                tradeHistoryFrame.setVisible(true);
            }
        });

        // Button to return to the user account settings page
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Close current window
                dispose();

                //Set name of next window
                AccountSettings userAccountFrame = new AccountSettings("Electronic Asset Trading");

                //Set Width and Height of the window
                userAccountFrame.setSize(500, 650);

                //Show window
                userAccountFrame.setVisible(true);

            }
        });

        // Retrieves the current data stored in the DB
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Assets frame = null;
                try {
                    frame = new Assets("Electronic Asset Trading");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                // Set the width and height
                frame.setSize(550, 1200);

                // Set frame visibility
                frame.setVisible(true);
            }
        });

    }

    /**
     * @param assetName
     * The name of the asset
     * @param assetQuantity
     * The total quantity of the asset in the DB
     * @param assetCost
     * Cost of the asset
     * @param isBuy
     * Whether the asset is being bought or sold
     */
    public static void displayAsset(String assetName, Integer assetQuantity, Integer assetCost, Boolean isBuy) {
    }


    public static void main(String[] args) throws SQLException {

        // Create Application
        JFrame frame = new Assets("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(1200, 550);

        // Set frame visibility
        frame.setVisible(true);
    }
}
