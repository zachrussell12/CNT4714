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
    private static boolean connectionStatus = false;

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

            connectionStatus = true;

            return props.getProperty("databaseURL");
        }
        else{
            return "error";
        }
    }

    public static ResultObject submitQuery(String query) throws SQLException, ClassNotFoundException{

            try {
                Statement firstStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet stateResult = firstStatement.executeQuery(query);

                int columns = stateResult.getMetaData().getColumnCount();
                stateResult.last();
                int rows = stateResult.getRow();
                stateResult.beforeFirst();

                System.out.println(columns);
                System.out.println(rows);
                String[][] resultRows = new String[rows][columns+1];
                String[] columnNames = new String[columns+1];

                for (int i = 1; i <=columns; i++) {
                    columnNames[i] = stateResult.getMetaData().getColumnLabel(i);
                    System.out.println(columnNames[i]);
                }

                int count = 0;

                while (stateResult.next()){
                        for (int i = 1; i <=columns; i++) {
                                resultRows[count][i] = stateResult.getString(columnNames[i]);
                                //System.out.println(resultRows[count][i] + "-");
                        }
                    count++;
                }

                ResultObject result = new ResultObject(columnNames, resultRows, rows, columns);

                for(int j = 0; j < rows; j++) {
                    for (int i = 1; i <=columns; i++) {
                        System.out.println(result.rowData[j][i]);
                    }
                }

                return result;
            }
            catch(NullPointerException err){
                return null;
            }
        }

    public static void submitUpdate(String query) throws SQLException {

        Statement firstStatement = connection.createStatement();
        firstStatement.executeUpdate(query);
    }

    public static boolean getConnection() throws SQLException {
        return connectionStatus;
    }
}
