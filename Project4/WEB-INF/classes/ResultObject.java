/*
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Four: Developing A Three-Tier Distributed Web-Based Application
Date: April 21, 2022
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