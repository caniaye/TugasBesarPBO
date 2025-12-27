package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomTable extends JTable {
    
    public CustomTable(DefaultTableModel model) {
        super(model);
        customizeTable();
    }
    
    private void customizeTable() {
        // Set row height
        setRowHeight(30);
        
        // Set font
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Set selection colors
        setSelectionBackground(new Color(220, 240, 255));
        setSelectionForeground(Color.BLACK);
        
        // Set grid
        setShowGrid(true);
        setGridColor(new Color(230, 230, 230));
        
        // Custom header
        getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        getTableHeader().setBackground(new Color(70, 130, 180));
        getTableHeader().setForeground(Color.WHITE);
        getTableHeader().setReorderingAllowed(false);
        
        // Center align for numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Right align for price columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Set column renderers
        for (int i = 0; i < getColumnCount(); i++) {
            String columnName = getColumnName(i).toLowerCase();
            if (columnName.contains("harga") || columnName.contains("total")) {
                getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            } else if (columnName.contains("tahun") || columnName.contains("jumlah") || columnName.contains("stok")) {
                getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Non-editable table
    }
}