/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Three - Two-Tier Client-Server Application Development With MySQL and JDBC
Date: March 20, 2022
Class: Section: 0001
*/

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Application{

    private static Connection connection;

    public Application() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        initializeConnection();

        new GUI();

    }

    public static void initializeConnection() throws SQLException, ClassNotFoundException{

        Class.forName("com.mysql.cj.jdbc.Driver");

    }

    public static String login(String user, String pass, String file) throws SQLException, IOException {

        Properties props = new Properties();
        props.load(Application.class.getClassLoader().getResourceAsStream(file));
        System.out.println(user);
        System.out.println(props.getProperty("username"));
        System.out.println(pass);
        System.out.println(props.getProperty("password"));
        if((user.equals(props.getProperty("username"))) && (pass.equals(props.getProperty("password")))){

            System.out.println("Login credentials valid");
            connection = DriverManager.getConnection(props.getProperty("databaseURL"), user, pass);

            return props.getProperty("databaseURL");
        }
        else{
            return "error";
        }
    }

    public static String submitQuery(String query) throws SQLException, ClassNotFoundException{

        String[] split = query.split(" ");

        if(split[0].equalsIgnoreCase("select")){
            System.out.println("Select statement");

            String returnResult = null;

            System.out.println(query);

            try {
                Statement firstStatement = connection.createStatement();
                ResultSet stateResult = firstStatement.executeQuery(query);

                int columns = stateResult.getMetaData().getColumnCount();
                String[] columnNames = new String[columns];

                for(int i = 1; i < columns; i++){
                    columnNames[i] = stateResult.getMetaData().getColumnLabel(i);
                    System.out.println(columnNames[i]);
                    if(i == columns-1){
                        returnResult += columnNames[i] + "\n";
                    }
                    else {
                        returnResult += columnNames[i] + "\t";
                    }
                }

                if(columns == 1){
                    returnResult += columnNames[0] + "\n";
                }

                while(stateResult.next()){
                    for(int i = 1; i < columns; i++){
                        if(i == columns-1){
                            returnResult += stateResult.getString(columnNames[i]) + "\n";
                        }
                        else {
                            returnResult += stateResult.getString(columnNames[i]) + "\t";
                        }
                    }
                    if(columns == 1){
                        returnResult += stateResult.getString(columnNames[0]) + "\n";
                    }
                }

                if(returnResult == null){
                    return "No such items exist";
                }

                return returnResult;
            }
            catch(NullPointerException err){
                return "-1";
            }
        }
        else{
            System.out.println("Update Statement");

            Statement adminStatement = connection.createStatement();
            adminStatement.executeUpdate(query);
            return "1";
        }
    }
}
