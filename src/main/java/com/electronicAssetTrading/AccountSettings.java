package com.electronicAssetTrading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 *  Account Settings pages gives the user an overview of the all the assets their organisation owns
 *  Additionally, the user can change their password,
 *  and if the user is an admin they are able to add an asset to their organisation
 */
public class AccountSettings extends JFrame {

    private JPanel userAccountPanel;
    private JButton assetsButton;
    private JButton tradesButton;
    private JButton accountButton;
    private JButton signOutButton;
    private JButton changePasswordButton;
    private JButton addAssetButton;
    private JLabel username;
    private JLabel organisationName;
    private JLabel adminType;
    private JPanel assets;
    private JPanel assetName;
    private JPanel assetQuantity;

    /**
     * @param title
     * Title of the window
     */
    public AccountSettings(String title) {
        super(title);

        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(userAccountPanel);
        this.pack();

        // Checks if user is an admin or not
        username.setText((String) Login.userName);
        organisationName.setText("Organisation of User: " + Login.Org_ID);
        if (Login.admin.equals("1")) {
            adminType.setText("ADMIN");
        } else if (Login.admin.equals("0")) {
            adminType.setText("NOT ADMIN");
        }

        // GUI boarder
        assets.setBorder(BorderFactory.createTitledBorder("Assets of the Organisation"));

        assetName.setLayout(new GridLayout(0, 1));
        for (Object i : Login.asset_name) {
            assetName.add(new JLabel((String) i));
        }

        // Assets that have been purchased by the organisation
        assetQuantity.setLayout(new GridLayout(0, 1));
        for (Object i : Login.asset_quantity) {
            assetQuantity.add(new JLabel("      Quantity Available: " + i));
        }
        assetName.validate();
        assetName.repaint();

        assetQuantity.validate();
        assetQuantity.repaint();


        // Change password button
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ChangePassword changePassword = new ChangePassword("Electronic Asset Trading");
                changePassword.setSize(500, 200);
                changePassword.setVisible(true);


            }
        });

        //Account button
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                AccountSettings userAccountFrame = new AccountSettings("Electronic Asset Trading");
                userAccountFrame.setSize(500, 650);
                userAccountFrame.setVisible(true);

            }
        });

        // Sign Out button
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Trade history button
        tradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Trades tradeHistoryFrame = new Trades("Electronic Asset Trading");
                tradeHistoryFrame.setSize(500, 650);
                tradeHistoryFrame.setVisible(true);

            }
        });

        // Asset listing page
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

        // Add asset button
        addAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login("Electronic Asset Trading");
                if (Login.admin.equals("1")) {
                    dispose();
                    AddAsset addAsset = null;
                    addAsset = new AddAsset("Electronic Asset Trading");
                    addAsset.setSize(1100, 550);
                    addAsset.setVisible(true);
                } else if (Login.admin.equals("0")) {
                    JOptionPane.showMessageDialog(null, "You are not ADMIN");
                }


            }
        });
    }

    public static void main(String[] args) {

        // Create Application
        JFrame frame = new AccountSettings("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);


    }
}
