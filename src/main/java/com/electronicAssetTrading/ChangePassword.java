package com.electronicAssetTrading;


import ServerSideApp.HashPassword;
import ServerSideApp.UpdateTestMethod;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Change Password as the name implies is the page where a user will change their password
 * this password is stored as a hash in the database
 */
public class ChangePassword extends JFrame {
    public static Object newPwd;
    ResultSet rs2 = null;
    PreparedStatement pst2 = null;
    private JTextField textField1;
    private JButton enterButton;
    private JPanel newPassword;
    private JButton backButton;

    /**
     * @param title
     * The title of the window
     */
    public ChangePassword(String title) {
        super(title);
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(newPassword);
        this.pack();
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login("Electronic Asset Trading");
                newPwd = textField1.getText().trim();
                System.out.println(newPwd);
                System.out.println("Hash Password is: " + HashPassword.StringToHash((String) newPwd));

                ConnectToDb connectToDb = new ConnectToDb("Electronic Asset Trading");
                UpdateTestMethod updateTestMethod = new UpdateTestMethod();
                try {

                    Object newPassword = updateTestMethod.updatePwd(newPwd, login.userName, connectToDb.con);
                    pst2 = ConnectToDb.con.prepareStatement("Select * from users where username = ? and user_password = ?");
                    pst2.setObject(1, login.userName);
                    pst2.setObject(2, HashPassword.StringToHash((String) newPwd));
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    Object changedPassword = rs2.getObject("user_password");

                    if (newPassword.equals(changedPassword)){
                        JOptionPane.showMessageDialog(enterButton, "Password has been changed");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
        // The Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountSettings userAccountFrame = new AccountSettings("Electronic Asset Trading");
                userAccountFrame.setSize(500, 650);
                userAccountFrame.setVisible(true);
            }
        });


    }


    public static void main(String[] args) {

        // Create Application
        JFrame frame = new ChangePassword("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(500, 200);

        // Set frame visibility
        frame.setVisible(true);


    }
}
