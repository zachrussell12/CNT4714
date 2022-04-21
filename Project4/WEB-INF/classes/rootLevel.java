/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Four: Developing A Three-Tier Distributed Web-Based Application
Date: April 21, 2022
Class: Section: 0001
*/

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rootLevel extends HttpServlet {

    private static Connection connection;

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession sessionCreds = request.getSession();

        if(request.getParameter("username") != null){

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project4", request.getParameter("username"), request.getParameter("password"));
                sessionCreds.setAttribute("user", request.getParameter("username"));
                sessionCreds.setAttribute("pass", request.getParameter("password"));
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                dispatcher.forward(request, response);

            } catch (ClassNotFoundException | SQLException throwables) {
                String message = "<p style=\"background-color: #eb4d4d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">Database Error: " + throwables.toString() + "</p>";
                HttpSession session = request.getSession();
                session.setAttribute("message", message);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                dispatcher.forward(request, response);
                dispatcher.forward(request, response);
            }

        }
        else if(request.getParameter("exec") != null){

            String query = request.getParameter("sqlStatement");

            String[] parts = query.split(" ");

            if(query.length() != 0){
            if(parts[0].contains("select") || parts[0].contains("SELECT")) {
                try {

                    Statement firstStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet stateResult = firstStatement.executeQuery(query);

                    int columns = stateResult.getMetaData().getColumnCount();
                    stateResult.last();
                    int rows = stateResult.getRow();
                    stateResult.beforeFirst();

                    //System.out.println(columns);
                    //System.out.println(rows);
                    String[][] resultRows = new String[rows][columns + 1];
                    String[] columnNames = new String[columns + 1];

                    for (int i = 1; i <= columns; i++) {
                        columnNames[i] = stateResult.getMetaData().getColumnLabel(i);
                        //System.out.println(columnNames[i]);
                    }

                    int count = 0;

                    while (stateResult.next()) {
                        for (int i = 1; i <= columns; i++) {
                            resultRows[count][i] = stateResult.getString(columnNames[i]);
                            //System.out.println(resultRows[count][i] + "-");
                        }
                        count++;
                    }

                    ResultObject result = new ResultObject(columnNames, resultRows, rows, columns);

                    String concat = "<table><thead><tr>";

                    for (int i = 1; i < result.columnTitles.length; i++) {
                        concat += "<th style=\"margin-right: 10px; margin-left: 10px; background-color: #4ac77c; border: 0; padding-top: 10px; padding-bottom: 10px;\">" + result.columnTitles[i] + "</th>";
                        //model.addColumn(result.columnTitles[i]);
                    }

                    concat += "</tr></thead>";

                    for (int i = 0; i < result.rowData.length; i++) {
                        concat += "<tr>";
                        for (int j = 1; j < result.columnTitles.length; j++) {
                            if (i % 2 == 0) {
                                concat += "<td style=\"background-color: #6e7073; padding-top: 5px; padding-bottom: 5px;\">" + result.rowData[i][j] + "</td>";
                            } else {
                                concat += "<td style=\"background-color: #515254; padding-top: 5px; padding-bottom: 5px;\">" + result.rowData[i][j] + "</td>";
                            }
                            //rows.add(result.rowData[i][j]);
                        }
                        //model.addRow(rows);
                        concat += "</tr>";
                    }

                    concat += "</table>";

                    String message = "<div style=\"display: flex; flex-direction: column; margin-left: auto; margin-right: auto; width: 60%; padding-bottom: 50px;\">" + concat + "</div>";
                    //message += "<span id='servlet'>servlet </span> and <span id='jsp'>jsp </span>technology.";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    ;
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    String message = "<p style=\"background-color: #eb4d4d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">Database Error: " + throwables.toString() + "</p>";
                    //message += "<span id='servlet'>servlet </span> and <span id='jsp'>jsp </span>technology.";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    ;
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else if(parts[0].contains("insert") || parts[0].contains("INSERT")){
                try {
                    String[] statement = performUpdate("i", query, parts, sessionCreds);
                    String message = "";
                    if(statement.length == 3) {
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p><p>" + statement[2] + "</p></div>";
                    }
                    else{
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p></div>";
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | SQLException throwables) {
                    throwables.printStackTrace();
                    String message = "<p style=\"background-color: #eb4d4d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">Database Error: " + throwables.toString() + "</p>";
                    //message += "<span id='servlet'>servlet </span> and <span id='jsp'>jsp </span>technology.";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    ;
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else if(parts[0].contains("update") || parts[0].contains("UPDATE")){
                try {
                    String[] statement = performUpdate("u", query, parts, sessionCreds);
                    String message = "";
                    if(statement.length == 3) {
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p><p>" + statement[2] + "</p></div>";
                    }
                    else{
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p></div>";
                    }                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | SQLException throwables) {
                    throwables.printStackTrace();
                    String message = "<p style=\"background-color: #eb4d4d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">Database Error: " + throwables.toString() + "</p>";
                    //message += "<span id='servlet'>servlet </span> and <span id='jsp'>jsp </span>technology.";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else if(parts[0].contains("replace") || parts[0].contains("REPLACE")){
                try {
                    String[] statement = performUpdate("r", query, parts, sessionCreds);
                    String message = "";
                    if(statement.length == 3) {
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p><p>" + statement[2] + "</p></div>";
                    }
                    else{
                        message = "<div style=\"background-color: #4ac77c; color: white; width: 60%; font-size: 16px; padding: 20px; display: flex; flex-direction: column; margin-left: auto; margin-right: auto;\"><p>" + statement[0] + "</p><p>" + statement[1] + "</p></div>";
                    }                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | SQLException throwables) {
                    throwables.printStackTrace();
                    String message = "<p style=\"background-color: #eb4d4d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">Database Error: " + throwables.toString() + "</p>";
                    //message += "<span id='servlet'>servlet </span> and <span id='jsp'>jsp </span>technology.";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("firstName", query);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else{
                String message = "<p style=\"background-color: #ebd04d; color: white; width: 40%; font-size: 16px; padding: 20px; display: flex; margin-left: auto; margin-right: auto;\">SQL Command not recognized. Please make sure you use a DDL, DML, or DCL command.</p>";
                HttpSession session = request.getSession();
                session.setAttribute("message", message);
                session.setAttribute("firstName", query);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
                dispatcher.forward(request, response);
            }
            }
        }
        else if(request.getParameter("clear") != null){
            String message = "<p></p>";
            HttpSession session = request.getSession();
            session.setAttribute("message", message);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
         doGet(request, response);
    }

    public String[] performUpdate(String type, String query, String[] parts, HttpSession credentials) throws ClassNotFoundException, SQLException {

        switch(type){
            case "i":

                Statement firstStatement = connection.createStatement();
                firstStatement.executeUpdate(query);

                if(query.contains("shipments")) {
                    String[] values = null;

                    Pattern deconstructValues = Pattern.compile("\\(([^\\)]+)\\)");
                    Matcher matchValues = deconstructValues.matcher(query);

                    if (matchValues.find()) {
                        values = matchValues.group().split(",");
                    }

                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].replace(",", "");
                        values[i] = values[i].replace(")", "");
                        values[i] = values[i].replace("(", "");
                        values[i] = values[i].replace("'", "");
                        values[i] = values[i].replace(" ", "");
                    }


                    if (Integer.parseInt(values[3]) > 100) {
                        Pattern findSupplier = Pattern.compile("S..");
                        Matcher matchSupplier = findSupplier.matcher(query);


                        String concat = "UPDATE suppliers set status = status + 5 where snum = '";
                        String foundMatch = "";

                        if (matchSupplier.find()) {
                            foundMatch += matchSupplier.group().replace(",", "") + ";";
                        }

                        if (foundMatch.length() != 0) {
                            concat += foundMatch;
                        }

                        Statement secondStatement = connection.createStatement();
                        secondStatement.executeUpdate(concat);

                        String[] msg = new String[3];

                        msg[0] = "The statement executed successfully. 1 row(s) affected.";
                        msg[1] = "Business Logic Detected! - Updating Supplier Status";
                        msg[2] = "Business logic updated 1 supplier status mark.";

                        return msg;
                    }
                }

                String [] msg = new String[2];

                msg[0] = "The statement executed successfully. 1 row(s) affected.";
                msg[1] = "Business Logic Triggered! No status marks updated though.";

                return msg;

            case "u":

                if(query.contains("quantity")){
                    Statement firstUStatement = connection.createStatement();
                    firstUStatement.executeUpdate(query);

                    String partNum = "";

                    Pattern findPart = Pattern.compile("P..");
                    Matcher matchPart = findPart.matcher(query);

                    if(matchPart.find()){
                        partNum = matchPart.group();
                    }

                    partNum.replace("\"", "");
                    partNum.replace("\"", "");

                    Statement secondUStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet stateResult = secondUStatement.executeQuery("SELECT snum from shipments where quantity >= '100' AND pnum = '" + partNum + ";");

                    stateResult.last();
                    int rows = stateResult.getRow();
                    stateResult.beforeFirst();

                    if(rows != 0) {

                        String[] suppliersToIncrease = new String[rows];
                        stateResult.next();

                        for (int i = 0; i < suppliersToIncrease.length; i++) {
                            suppliersToIncrease[i] = stateResult.getString("snum");
                            stateResult.next();
                        }

                        for (int j = 0; j < suppliersToIncrease.length; j++) {
                            Statement increaseStatuses = connection.createStatement();
                            increaseStatuses.executeUpdate("UPDATE suppliers SET status = status + 5 where snum = '" + suppliersToIncrease[j] + "';");
                        }

                        String[] uMsg = new String[3];

                        uMsg[0] = "The statement executed successfully. " + firstUStatement.getUpdateCount() + " row(s) affected.";
                        uMsg[1] = "Business Logic Detected! - Updating Supplier Status";
                        uMsg[2] = "Business Logic update " + suppliersToIncrease.length + " supplier status marks.";

                        return uMsg;
                    }
                    else{

                        String[] uMsg = new String[2];

                        uMsg[0] = "The statement executed successfully. " + firstUStatement.getUpdateCount() + " row(s) affected.";
                        uMsg[1] = "Business Logic Not Triggered";

                        return uMsg;

                    }

                }
                else{
                    Statement firstUStatement = connection.createStatement();
                    firstUStatement.executeUpdate(query);

                    String[] uMsg = new String[2];

                    uMsg[0] = "The statement executed successfully. " + firstUStatement.getUpdateCount() + " row(s) affected.";
                    uMsg[1] = "Business Logic Not Triggered";

                    return uMsg;

                }
            case "r":
                Statement firstUStatement = connection.createStatement();
                firstUStatement.executeUpdate(query);

                String[] rMsg = new String[2];

                rMsg[0] = "The statement executed successfully.";
                rMsg[0] = "Business Logic Not Triggered.";

                return rMsg;
        }

        return null;
    }
}
