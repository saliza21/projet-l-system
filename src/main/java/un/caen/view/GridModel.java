package un.caen.view;


import un.caen.model.feild.Grid;
import un.caen.util.Position;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * The `GridModel` class is an implementation of the `AbstractTableModel` class from the Swing library.
 * It is used to provide a table-like representation of a `Grid` object, which is a data structure
 * used to store and manipulate grid-based data.
 *
 */
public class GridModel extends DefaultTableModel {
    /**
     * The number of rows in the grid.
     */
    private final int rows;

    /**
     * The number of columns in the grid.
     */
    private final int cols;

    /**
     * The 2D array that stores the data for the grid.
     */
    private Object[][] gridData;

    /**
     * Constructs a new `GridModel` instance with the specified number of rows and columns.
     *
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     */
    public GridModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.gridData = new Object[rows][cols];
    }

    /**
     * Constructs a new `GridModel` instance from a `Grid` object.
     *
     * @param data the `Grid` object to be represented by the `GridModel`
     */
    public GridModel(Grid data) {
        this.rows = data.getHeight();
        this.cols = data.getWidth();
        this.gridData = new Object[rows][cols];
        setData(data);
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return the number of rows in the grid
     */
    @Override
    public int getRowCount() {
        return rows;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return the number of columns in the grid
     */
    @Override
    public int getColumnCount() {
        return cols;
    }

    /**
     * Returns the value at the specified row and column in the grid.
     *
     * @param rowIndex    the row index of the cell
     * @param columnIndex the column index of the cell
     * @return the value at the specified row and column
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gridData[rowIndex][columnIndex];
    }

    /**
     * Returns the column name for the specified column index.
     *
     * @param column the column index
     * @return the column name
     */
    @Override
    public String getColumnName(int column) {
        return "Column " + (column + 1);
    }

    /**
     * Sets the value of the cell at the specified row and column.
     *
     * @param row    the row index of the cell
     * @param col    the column index of the cell
     * @param value the new value for the cell
     */
    public void setCell(int row, int col, Object value) {
        gridData[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    /**
     * Sets the data for the grid model from a `Grid` object.
     *
     * @param newData the new `Grid` object to be represented by the `GridModel`
     */
    public void setData(Grid newData) {
      for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
              gridData[i][j] = newData.getCell(new Position(j,i));
          }
      }
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
            return ImageIcon.class;
    }

}
