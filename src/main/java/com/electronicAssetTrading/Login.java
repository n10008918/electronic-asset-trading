package com.electronicAssetTrading;

import ServerSideApp.HashPassword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The login page give the user access to the application if their user details
 * are in the database
 */
public class Login extends JFrame {
    public static ResultSet rs = null;
    public static ResultSet rs1 = null;
    public static PreparedStatement pst = null;
    public static PreparedStatement pst1 = null;
    public static ArrayList<Object> asset_name = new ArrayList<>();
    public static ArrayList<Object> asset_quantity = new ArrayList<>();
    public static Object userName;
    public static Object Org_ID;
    public static Object admin;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JLabel appTitle;


    /**
     * @param title
     * The title of the window
     */
    public Login(String title) {
        super(title);

        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(loginPanel);
        this.pack();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username
                String uname = usernameField.getText();

                // Get the password
                String psw = String.valueOf(passwordField.getPassword());

                // Check if password matches user password
                try {
                    ValidateUser(uname, psw);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }


            }
        });
    }

    /**
     * @throws SQLException
     */
    public static void assignGlobalVariables() throws SQLException {
        Org_ID = rs.getString("org_id");
        userName = rs.getString("username");
        admin = rs.getString("is_admin");

    }

    // Main operation function
    public static void main(String[] args) {

        // Create Application
        JFrame frame = new Login("Electronic Asset Trading");

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);

    }

    /**
     * @param uname This takes the username input
     * @param psw   This takes the password input
     * @throws SQLException Throws an error if the SQL statement is not correctly formed
     */
    public void ValidateUser(String uname, String psw) throws SQLException {
        try {
            //SQL Statement to select the users information
            String sql = "select username, user_password, org_id, is_admin from users where username = \"" + uname + "\"";

            // Connects to the DB and prepares the SQL statement
            pst = ConnectToDb.con.prepareStatement(sql);

            // Executes the SQL query
            rs = pst.executeQuery();

            // Goes to the first index of the result
            rs.next();

            // Check if the saved have is correct
            if (HashPassword.StringToHash(psw).equals(rs.getString("user_password"))) {

                // Pop to alert the user the the information inputted was correct
                JOptionPane.showMessageDialog(null, "Login Successful!");

                // Run the assign global variable function
                assignGlobalVariables();

                // Prepare the sql statement
                pst1 = ConnectToDb.con.prepareStatement("Select asset_name, asset_quantity from assets where org_id = ?");

                // Set parameters - Object ID
                pst1.setObject(1, Org_ID);

                // Execute Query
                rs1 = pst1.executeQuery();

                //A Run the query
                while (rs1.next()) {
                    Object assetName = rs1.getObject(1);
                    Object assetQuantity = rs1.getObject(2);
                    asset_name.add(assetName);
                    asset_quantity.add(assetQuantity);
                }

                MoveToNextForm();
            }

            // Pop up to alert user information entered was incorrect
            else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     *
     */
    public void MoveToNextForm() throws SQLException {
        dispose();

        //Set name of next window
        Assets newFrame = new Assets("Electronic Asset Trading");
        // Set the width and height
        newFrame.setSize(1100, 550);
        //Show window
        newFrame.setVisible(true);
    }
}
