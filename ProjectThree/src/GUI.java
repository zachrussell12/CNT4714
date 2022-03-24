/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Three - Two-Tier Client-Server Application Development With MySQL and JDBC
Date: March 20, 2022
Class: Section: 0001
*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

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

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        final JTable[] resultWindow = {new JTable()};
        final JScrollPane[] scrollableResult = {new JScrollPane(resultWindow[0], ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)};
        JButton clearResult = new JButton("Clear Query Result");
        clearResult.setBackground(Color.MAGENTA);
        clearResult.setForeground(Color.black);
        clearResult.setBorderPainted(false);

        bottomSection.add(new JLabel("SQL Query Result: "));
        bottomSection.add(scrollableResult[0]);
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
                    String[] split = queryField.getText().split(" ");
                    if(Application.getConnection()) {

                        if (split[0].equalsIgnoreCase("select")) {
                            ResultObject result = Application.submitQuery(queryField.getText());
                            if (result == null) {
                                //
                            } else {
                                System.out.println("Successfully got a result");

                                DefaultTableModel model = new DefaultTableModel();
                                resultWindow[0].setForeground(Color.black);
                                resultWindow[0].setBackground(Color.white);
                                resultWindow[0].setGridColor(Color.black);
                                resultWindow[0].setModel(model);
                                resultWindow[0].getTableHeader().setOpaque(false);
                                resultWindow[0].getTableHeader().setBackground(Color.LIGHT_GRAY);

                                for (int i = 1; i < result.columnTitles.length; i++) {
                                    model.addColumn(result.columnTitles[i]);
                                }

                                for (int i = 0; i < result.rowData.length; i++) {
                                    Vector<Object> rows = new Vector<Object>();
                                    for (int j = 1; j < result.columnTitles.length; j++) {
                                        rows.add(result.rowData[i][j]);
                                    }
                                    model.addRow(rows);
                                }

                                updateOperations("query");

                            }
                        } else {
                            Application.submitUpdate(queryField.getText());

                            updateOperations("update");
                        }
                    }
                    else{
                        connectionStatus.setText("You must establish a connection before submitting a query");
                    }
                } catch (SQLException throwables) {
                    System.out.println("SQL syntax problem");
                    JOptionPane.showMessageDialog( null,
                            throwables.getMessage(), "Database error",
                            JOptionPane.ERROR_MESSAGE );
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
                resultWindow[0].setModel(new DefaultTableModel());
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

    private void updateOperations(String val){
        if(val.equals("update")){
            try {
                Properties props = new Properties();
                props.load(Application.class.getClassLoader().getResourceAsStream("operations.properties"));

                Connection operate = DriverManager.getConnection(props.getProperty("databaseURL"), props.getProperty("username"), props.getProperty("password"));

                Statement firstStatement = operate.createStatement();
                ResultSet stateResult = firstStatement.executeQuery("select * from operationscount");

                stateResult.next();
                int countTwo = Integer.parseInt(stateResult.getString("num_queries"));
                int count = Integer.parseInt(stateResult.getString("num_updates"));

                int afterUpdate = count + 1;

                Statement secondStatement = operate.createStatement();

                secondStatement.executeUpdate("update operationscount set num_queries = '" + countTwo + "' where num_queries = " + countTwo);
                secondStatement.executeUpdate("update operationscount set num_updates = '" + afterUpdate + "' where num_queries = " + count);
            }
             catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(val.equals("query")){
            try {
                Properties props = new Properties();
                props.load(Application.class.getClassLoader().getResourceAsStream("operations.properties"));

                Connection operate = DriverManager.getConnection(props.getProperty("databaseURL"), props.getProperty("username"), props.getProperty("password"));

                Statement firstStatement = operate.createStatement();
                ResultSet stateResult = firstStatement.executeQuery("select * from operationscount");

                stateResult.next();
                int countTwo = Integer.parseInt(stateResult.getString("num_queries"));
                int count = Integer.parseInt(stateResult.getString("num_updates"));

                int afterUpdate = countTwo + 1;

                Statement secondStatement = operate.createStatement();

                secondStatement.executeUpdate("update operationscount set num_queries = '" + afterUpdate + "' where num_queries = " + countTwo);
                secondStatement.executeUpdate("update operationscount set num_updates = '" + count + "' where num_queries = " + count);
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
