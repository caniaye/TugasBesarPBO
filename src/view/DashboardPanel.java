package view;

import service.MobilService;
import service.PenjualanService;
import exception.CustomException;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel totalMobilLabel;
    private JLabel totalStokLabel;
    private JLabel totalPenjualanLabel;
    private JLabel unitTerjualLabel;
    private Timer refreshTimer;
    
    private final MobilService mobilService;
    private final PenjualanService penjualanService;
    
    public DashboardPanel() {
        mobilService = new MobilService();
        penjualanService = new PenjualanService();
        initComponents();
        setupRefreshTimer();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Dashboard Dealer Mobil");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        statsPanel.setBackground(Color.WHITE);
        
        totalMobilLabel = createStatCard("Total Mobil", "0", new Color(41, 128, 185));
        totalStokLabel = createStatCard("Stok Tersedia", "0", new Color(39, 174, 96));
        totalPenjualanLabel = createStatCard("Total Penjualan", "Rp 0", new Color(142, 68, 173));
        unitTerjualLabel = createStatCard("Unit Terjual", "0", new Color(230, 126, 34));
        
        statsPanel.add(totalMobilLabel);
        statsPanel.add(totalStokLabel);
        statsPanel.add(totalPenjualanLabel);
        statsPanel.add(unitTerjualLabel);
        
        add(statsPanel, BorderLayout.CENTER);
        
        // Update data
        updateDashboardData();
    }
    
    private JLabel createStatCard(String title, String value, Color color) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        
        JLabel container = new JLabel();
        container.setLayout(new BorderLayout());
        container.add(cardPanel, BorderLayout.CENTER);
        
        return container;
    }
    
    private void updateDashboardData() {
        try {
            int totalMobil = mobilService.getAllMobil().size();
            int totalStok = mobilService.getTotalStok();
            String totalPenjualan = penjualanService.getTotalPenjualan().toString();
            int unitTerjual = penjualanService.getTotalUnitTerjual();
            
            // Update labels
            ((JLabel) ((BorderLayout) ((JPanel) totalMobilLabel.getComponent(0)).getLayout()).
                getLayoutComponent(BorderLayout.CENTER)).setText(String.valueOf(totalMobil));
            
            ((JLabel) ((BorderLayout) ((JPanel) totalStokLabel.getComponent(0)).getLayout()).
                getLayoutComponent(BorderLayout.CENTER)).setText(String.valueOf(totalStok));
            
            ((JLabel) ((BorderLayout) ((JPanel) totalPenjualanLabel.getComponent(0)).getLayout()).
                getLayoutComponent(BorderLayout.CENTER)).setText("Rp " + totalPenjualan);
            
            ((JLabel) ((BorderLayout) ((JPanel) unitTerjualLabel.getComponent(0)).getLayout()).
                getLayoutComponent(BorderLayout.CENTER)).setText(String.valueOf(unitTerjual));
            
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error Dashboard", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupRefreshTimer() {
        refreshTimer = new Timer(5000, e -> updateDashboardData());
        refreshTimer.start();
    }
    
    public void stopRefreshTimer() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }
}