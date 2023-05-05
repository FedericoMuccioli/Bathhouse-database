package lab.view.utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyDefaultTableCellRenderer extends DefaultTableCellRenderer {
	
	static public void resizeAndCenterTable(JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Create a cell renderer for centering the cell content
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

		// Apply the cell renderer to the columns
		for (int column = 0; column < table.getColumnCount(); column++) {
		    table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
		}

		// Create a cell renderer for centering the header content
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

		// Set the modified header renderer to the table header
		table.getTableHeader().setDefaultRenderer(headerRenderer);

		// Calculate and set column widths
		for (int column = 0; column < table.getColumnCount(); column++) {
		    int width = 50; // Minimum column width
		    for (int row = 0; row < table.getRowCount(); row++) {
		        var renderer = table.getCellRenderer(row, column);
		        var component = table.prepareRenderer(renderer, row, column);
		        width = Math.max(component.getPreferredSize().width + 5, width);
		    }
		    table.getColumnModel().getColumn(column).setPreferredWidth(width);
		}
	}
	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setBackground(Color.GRAY);
        return component;
    }

}
