/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Three - Two-Tier Client-Server Application Development With MySQL and JDBC
Date: March 20, 2022
Class: Section: 0001
*/

public class ResultObject {

    public String[] columnTitles;
    public String[][] rowData;

    public ResultObject(String[] columnNames, String[][] rowsSet, int rows, int columns) {
        this.columnTitles = columnNames;
        this.rowData = rowsSet;
    }
}
