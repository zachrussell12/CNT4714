public class ResultObject {

    public String[] columnTitles;
    public String[][] rowData;

    public ResultObject(String[] columnNames, String[][] rowsSet, int rows, int columns) {
        this.columnTitles = columnNames;
        this.rowData = rowsSet;
    }
}
