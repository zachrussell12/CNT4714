/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Three - Two-Tier Client-Server Application Development With MySQL and JDBC
Date: March 20, 2022
Class: Section: 0001
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class GUI extends JFrame{

    private String propFile = "client.properties";

    private JTextArea queryField;

    public GUI() {
        initialize();
    }

    public void initialize(){

        JFrame window = new JFrame("Database Interface");

        window.setSize(700,700);
        window.setLocation(500,50);

        window.setLayout(new FlowLayout());

        JLabel enterQuery = new JLabel("Enter Query: ");

        queryField = new JTextArea("select * from riders", 3, 30);
        queryField.setWrapStyleWord(true);
        queryField.setLineWrap(true);

        JScrollPane scrollable = new JScrollPane(queryField, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.black);
        submitButton.setBorderPainted(false);

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.black);
        clearButton.setBorderPainted(false);

        Box submitClear = Box.createHorizontalBox();

        submitClear.add(clearButton);
        submitClear.add(submitButton);

        Box box = Box.createVerticalBox();
        box.add(enterQuery);
        box.add(scrollable);
        box.add(submitClear);

        Box dropBox = Box.createHorizontalBox();
        JLabel propLabel = new JLabel("Properties File: ");
        JComboBox propDrop = new JComboBox();
        propDrop.addItem("client.properties");
        propDrop.addItem("root.properties");
        dropBox.add(propLabel);
        dropBox.add(propDrop);

        Box userBox = Box.createHorizontalBox();
        JLabel user = new JLabel("Username: ");
        JTextField userName = new JTextField(10);
        userName.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        userName.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        userBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        userBox.add(user);
        userBox.add(userName);

        Box passBox = Box.createHorizontalBox();
        JLabel pass = new JLabel("Password: ");
        JPasswordField password = new JPasswordField(10);
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        password.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        passBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        passBox.add(pass);
        passBox.add(password);

        JScrollPane scrollableTwo = new JScrollPane(userBox, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane scrollableThree = new JScrollPane(passBox, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Box topLeftHeader = Box.createHorizontalBox();
        JLabel header = new JLabel("Connection Details: ");
        JLabel blank = new JLabel("\t\t");
        topLeftHeader.add(header);
        topLeftHeader.add(blank);

        Box boxTwo = Box.createVerticalBox();
        header.setHorizontalAlignment(0);
        boxTwo.add(topLeftHeader);
        boxTwo.add(dropBox);
        boxTwo.add(scrollableTwo);
        boxTwo.add(scrollableThree);
        //resultWindow.setGridColor(Color.black);

        Box topSection = Box.createHorizontalBox();

        topSection.add(boxTwo);
        topSection.add(box);

        Box midSection = Box.createHorizontalBox();

        JButton connect = new JButton("Connect to Database");
        connect.setBackground(Color.blue);
        connect.setForeground(Color.white);
        connect.setBorderPainted(false);

        JTextArea connectionStatus = new JTextArea("No connection",1, 40);
        connectionStatus.setBackground(Color.black);
        connectionStatus.setForeground(Color.red);
        connectionStatus.setEditable(false);

        midSection.add(connect);
        midSection.add(connectionStatus);

        Box bottomSection = Box.createVerticalBox();

        JTextArea resultWindow = new JTextArea(30, 60);
        JScrollPane scrollableResult = new JScrollPane(resultWindow, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JButton clearResult = new JButton("Clear Query Result");
        clearResult.setBackground(Color.MAGENTA);
        clearResult.setForeground(Color.black);
        clearResult.setBorderPainted(false);

        bottomSection.add(new JLabel("SQL Query Result: "));
        bottomSection.add(scrollableResult);
        bottomSection.add(clearResult);

        window.add(topSection);
        window.add(midSection);
        window.add(bottomSection);

        window.setVisible(true);

        propDrop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propFile = propDrop.getSelectedItem().toString();
                System.out.println(propFile);
            }
        });

        submitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String result = Application.submitQuery(queryField.getText());
                    if(result == "-1"){
                        connectionStatus.setText("You must establish a connection before submitting a query");
                    }
                    else if(result == "1"){
                        System.out.println("Update created successfully");
                    }
                    else{
                        resultWindow.setText(result);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        clearButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryField.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        connect.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    String connectionURL = Application.login(userName.getText(), password.getText(), propFile);

                    if(connectionURL == "error"){
                        connectionStatus.setForeground(Color.red);
                        connectionStatus.setText("Invalid Login Credentials. Please try again.");
                    }
                    else {
                        connectionStatus.setForeground(Color.green);
                        connectionStatus.setText("Connected to " + connectionURL);
                    }

                } catch (SQLException | IOException throwables) {
                    connectionStatus.setForeground(Color.red);
                    connectionStatus.setText("Invalid Login Credentials. Please try again.");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        clearResult.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultWindow.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}
