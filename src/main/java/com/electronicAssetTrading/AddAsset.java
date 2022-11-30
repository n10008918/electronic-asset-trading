package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 *  AddAsset the page where an admin is able to add a new asset to the database
 */
public class AddAsset extends JFrame{
    private JPanel userAssetPanel;
    private JButton assetsButton;
    private JButton tradesButton;
    //private JButton signOutButton;
    private JPanel assetsToAdd;
    private JButton addAssetButton;
    private JButton signOutButton;
    private JTextField nameOfAsset;
    private JTextField quantityOfAsset;
    private JTextField organisationOfAsset;
    private JTextField costOfAsset;
    private JButton accountButton;

    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    ResultSet rs5 = null;
    ResultSet rs6 = null;
    ResultSet rs7 = null;


    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    PreparedStatement pst5 = null;
    PreparedStatement pst6 = null;
    PreparedStatement pst7 = null;


    public static Object ObjnameOfAsset;
    public static Object ObjquantityOfAsset;
    public static Object ObjorganisationOfAsset;
    public static Object ObjcostOfAsset;


    public static ArrayList<String> t1 = new ArrayList<>();
    public static ArrayList<Integer> inInt = new ArrayList<>();

    /**
     * @param title
     * Title of the window
     */
    public AddAsset(String title) {
        super (title);
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(userAssetPanel);
        this.pack();



        addAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameOfAsset.getText().isEmpty() || costOfAsset.getText().isEmpty() || quantityOfAsset.getText().isEmpty() || organisationOfAsset.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(addAssetButton, "Please fill out all the fields");

                    }
                    ObjnameOfAsset = nameOfAsset.getText().trim();


                    ObjcostOfAsset = costOfAsset.getText().trim();


                    ObjquantityOfAsset = quantityOfAsset.getText().trim();


                    ObjorganisationOfAsset = organisationOfAsset.getText().trim();


                    pst2 = ConnectToDb.con.prepareStatement("Select * from assets where asset_name = ? and org_id = ?");
                    pst2.setObject(1, ObjnameOfAsset);
                    pst2.setObject(2, ObjorganisationOfAsset);
                    rs2 = pst2.executeQuery();

                    if (rs2.next()) {
                        pst3 = ConnectToDb.con.prepareStatement("Update assets set asset_cost = ?, asset_quantity = ? where BINARY asset_name = ? and BINARY org_id = ?");
                        pst3.setObject(1, ObjcostOfAsset);
                        pst3.setObject(2, ObjquantityOfAsset);
                        pst3.setObject(3, ObjnameOfAsset);
                        pst3.setObject(4, ObjorganisationOfAsset);
                        rs3 = pst3.executeQuery();
                        if (rs3.next() == true) {
                            JOptionPane.showMessageDialog(addAssetButton, "Please enter correct value");
                        } else if (rs3.next() == false ) {
                            JOptionPane.showMessageDialog(addAssetButton, "Asset has been Updated");
                        }

                    } else if (rs2.next() == false) {
                        pst4 = ConnectToDb.con.prepareStatement("Select asset_id from assets");
                        rs4 = pst4.executeQuery();
                        while (rs4.next()) {
                            String asset_ids = rs4.getString("asset_id");
                            t1.add(asset_ids);
                            //System.out.println(asset_ids);

                        }
                        ArrayList<String[]> t2 = new ArrayList<>();
                        for (String stuff : t1) {
                            t2.add(stuff.split("a"));
                        }
                        for (String[] thing : t2) {
                            for (int i = 1; i < thing.length; i = i + 2) {
                                int x = Integer.valueOf(thing[i]);
                                inInt.add(x);
                                //System.out.println(x);
                            }
                        }

//                        for (Object thing : inInt) {
//                            System.out.println(thing);
//                        }
                        Integer max = Collections.max(inInt);
                        //System.out.println(max);
                        String maxVal = ("a0000" + (max + 1));
                        pst5 = ConnectToDb.con.prepareStatement("insert into assets values(?, ?, ?, ?, ?, ?)");
                        pst5.setObject(1, maxVal);
                        pst5.setObject(2, ObjorganisationOfAsset);

                        pst5.setObject(3, ObjnameOfAsset);
                        pst5.setObject(4, ObjquantityOfAsset);

                        pst5.setObject(5, ObjcostOfAsset);

                        pst5.setInt(6, 0);
                        rs5 = pst5.executeQuery();
                        if (rs5.next()== false){
                            JOptionPane.showMessageDialog(null, "Asset has been added");
                        }


                    }
                    //else create new one using insert query to the database
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

        // Sign Out Button
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response ==JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });

        // Trade button
        tradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Trades tradeHistoryFrame = new Trades("Electronic Asset Trading");
                tradeHistoryFrame.setSize(500, 650);
                tradeHistoryFrame.setVisible(true);

            }
        });

        // Account Button
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                AccountSettings userAccountFrame = new AccountSettings("Electronic Asset Trading");
                userAccountFrame.setSize(500, 650);
                userAccountFrame.setVisible(true);

            }
        });

        // Asset Button
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
                newFrame.setSize(500, 1100);
                newFrame.setVisible(true);

            }
        });

    }
    public static void main(String[] args){

        // Create Application
        JFrame frame = new AddAsset("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(700, 650);

        // Set frame visibility
        frame.setVisible(true);


    }
}
