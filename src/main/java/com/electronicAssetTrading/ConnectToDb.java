package com.electronicAssetTrading;

import ServerSideApp.CreateEntities;
import ServerSideApp.DbConnection;
import ServerSideApp.SetupDb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * The first page that is built to run and then use the Electronic Asset Trading application
 * here the user connects to the database inserting the server address, port number and credentials to the
 * database. These credentials are separate for those needed to use the assets.
 */
public class ConnectToDb extends JFrame {
    public static Connection con;
    private JTextField userField;
    private JPanel connectToDbPanel;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel connectionLabel;
    private JLabel connectionPassword;
    private JTextField serverField;
    private JTextField portField;

    /**
     * @param title
     * The title of the window
     */
    public ConnectToDb(String title) {
        super(title);

        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setContentPane(connectToDbPanel);
        this.pack();


        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get server number
                String serverNum = serverField.getText();

                // Get Port Number
                String portNum = portField.getText();

                // Get the username
                String uname = userField.getText();

                // Get the password
                String psw = String.valueOf(passwordField.getPassword());

                // Sets the server address to the value entered by user
                DbConnection.MariaDbCredentials.SERVER = serverNum;

                // Sets the port number to the value entered by user
                DbConnection.MariaDbCredentials.PORT = portNum;

                // Sets the user name to the value entered by user
                DbConnection.MariaDbCredentials.USER = uname;

                // Sets the password to the value entered by user
                DbConnection.MariaDbCredentials.PASS = psw;

                // Establish connection with the DB
                try {
                    // Utilised the DbConnection class to connect to MariaDB
                    con = DbConnection.ConnectToDb();

                    // If the value inputted is correct persists the data to the DB
                    if (DbConnection.MariaDbCredentials.SUCCESS) {

                        // Pop up to prompt the user that the connection had been established
                        JOptionPane.showMessageDialog(null, "Connected!");
                        System.out.println("Connected database successfully...");

                        //Creates the tables in the DB and persists the entities
                        SetupDb.persistTables();
                        CreateEntities.persistEntities();

                        // Moves to the next form
                        MoveToNextForm();
                    }
                    // If inputted data is incorrect pop up notifier for the user
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or password");
                    }
                }
                // Exception to catch and connecting errors e.g. incorrect password
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    // Main operation function
    public static void main(String[] args) {
        // Create Application
        JFrame frame = new ConnectToDb("Credentials to MariaDb");

        // Set the width and height
        frame.setSize(500, 650);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Function to move to the next window
    public void MoveToNextForm() {
        //Close current window
        dispose();

        //Set name of next window
        Login newFrame = new Login("Electronic Asset Trading");

        //Set Width and Height of the window
        newFrame.setSize(500, 650);

        //Show window
        newFrame.setVisible(true);
    }
}
