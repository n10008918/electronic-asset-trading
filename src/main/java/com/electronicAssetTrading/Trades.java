package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *  Trades is the page where a user can see all the trades that have been processed over a period of time
 */
public class Trades extends  JFrame{

    private JButton assetsButton;
    private JButton tradesButton;
    private JButton accountButton;
    private JButton signoutButton;
    private JPanel tradeHistoryPanel;
    private JPanel item;
    private JPanel itemInfo;
    private JPanel itemName;
    private JButton detailButton;
    private String username;
    private String orgId;

    ResultSet rs = null;
    PreparedStatement pst = null;

    /**
     * @param title
     * The title of the window
     */
    public Trades(String title)  {
        super(title);

        // Package the window
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(tradeHistoryPanel);
        this.pack();

        // Set the border
        item.setBorder(BorderFactory.createTitledBorder("Information of Past Trades"));

        // Variable declarations
        username = (String) Login.userName;
        orgId = (String) Login.Org_ID;
        System.out.print(orgId);

        // Layout for the values that will be inserted
        itemName.setLayout(new GridLayout(0,1));
//        for (Object stuff : login.asset_name){
//            itemName.add(new JLabel((String) stuff));
//        };

        // Getting all the trades from the organisation
        String sql = "select * from trades where org_id =\""+ orgId +"\"";
        try {
            // Connection to the DB to send query
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();

            // Get the variables to display the trade history
            while (rs.next()) {
                String receipt = rs.getString("receipt_number");
                String assetName = rs.getString("asset_name");
                Date date = rs.getDate("transaction_date");
                String pattern = "MM/dd/yyyy";
                DateFormat df = new SimpleDateFormat(pattern);
                String actualDate = df.format(date);
                itemName.add(new JLabel("Receipt: "+receipt));
                itemName.add(new JLabel("Asset Name: " + assetName));
                itemName.add(new JLabel("Transaction Date: "+actualDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Set the layout grid
        itemInfo.setLayout(new GridLayout(0,1));
        try {
            // Connect to the DB to send query
            pst = ConnectToDb.con.prepareStatement(sql);
            rs = pst.executeQuery();

            // Retrieve all trades within the result
            while (rs.next()) {
                String receipt = rs.getString("receipt_number");
                JButton btn = new JButton("View Detail");
                itemInfo.add(btn);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        JFrame frame = new TradeConfirmation("Electronic Asset Trading", receipt);
                        frame.setSize(500, 650);
                        frame.setVisible(true);

                    }
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Item Declaration
        itemName.validate();
        itemName.repaint();
        itemInfo.validate();
        itemInfo.repaint();


        //Sign out button
        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response ==JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });

        // Asset button
        assetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        // Account button
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountSettings newFrame = new AccountSettings("Electronic Asset Trading");
                newFrame.setSize(500, 650);
                newFrame.setVisible(true);

            }
        });

    }

    public static void main(String[] args) {

        // Create Application
        JFrame frame = new Trades("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);


    }
}
