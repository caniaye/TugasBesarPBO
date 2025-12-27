package view;

import service.ReportService;
import exception.CustomException;
import javax.swing.*;
import java.awt.*;

public class LaporanPanel extends JPanel {
    private JButton mobilReportButton, penjualanReportButton;
    private final ReportService reportService;
    
    public LaporanPanel() {
        reportService = new ReportService();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Laporan Dealer Mobil");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Description
        JLabel descLabel = new JLabel("<html><center>Pilih jenis laporan yang ingin di-generate:<br>" +
                                    "Laporan akan ditampilkan dalam format PDF menggunakan Jasper Reports</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(descLabel, gbc);
        
        // Report Buttons
        mobilReportButton = createReportButton("Laporan Data Mobil", new Color(52, 152, 219));
        penjualanReportButton = createReportButton("Laporan Penjualan", new Color(46, 204, 113));
        
        contentPanel.add(mobilReportButton, gbc);
        contentPanel.add(penjualanReportButton, gbc);
        
        // Status label
        JLabel statusLabel = new JLabel("Status: Siap generate laporan");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(statusLabel, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Add listeners
        setupListeners();
    }
    
    private JButton createReportButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void setupListeners() {
        mobilReportButton.addActionListener(e -> generateMobilReport());
        penjualanReportButton.addActionListener(e -> generatePenjualanReport());
    }
    
    private void generateMobilReport() {
        try {
            reportService.generateMobilReport();
            JOptionPane.showMessageDialog(this, 
                "Laporan data mobil berhasil di-generate!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error Generate Report", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generatePenjualanReport() {
        try {
            reportService.generatePenjualanReport();
            JOptionPane.showMessageDialog(this, 
                "Laporan penjualan berhasil di-generate!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error Generate Report", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}