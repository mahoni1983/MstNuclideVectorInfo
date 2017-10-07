import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

   NonEditableTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}