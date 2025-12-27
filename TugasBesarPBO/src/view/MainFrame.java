package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private DashboardPanel dashboardPanel;
    private MobilPanel mobilPanel;
    private PenjualanPanel penjualanPanel;
    private LaporanPanel laporanPanel;

    private String username;

    // Constructor menerima username dari Login
    public MainFrame(String username) {
        this.username = username;
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        setTitle("Dealer Mobil - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("DEALER MOBIL ADMIN PANEL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Admin: " + username);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ================= MENU BAR (LOGOUT) =================
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAkun = new JMenu("Akun");
        JMenuItem menuLogout = new JMenuItem("Logout");

        menuLogout.addActionListener(e -> logout());

        menuAkun.add(menuLogout);
        menuBar.add(menuAkun);
        setJMenuBar(menuBar);

        // ================= TABS =================
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        dashboardPanel = new DashboardPanel();
        mobilPanel = new MobilPanel();
        penjualanPanel = new PenjualanPanel();
        laporanPanel = new LaporanPanel();

        tabbedPane.addTab("Dashboard", new ImageIcon("src/assets/dashboard.png"), dashboardPanel);
        tabbedPane.addTab("Manajemen Mobil", new ImageIcon("src/assets/car.png"), mobilPanel);
        tabbedPane.addTab("Penjualan", new ImageIcon("src/assets/sales.png"), penjualanPanel);
        tabbedPane.addTab("Laporan", new ImageIcon("src/assets/report.png"), laporanPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // ================= FOOTER =================
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(236, 240, 241));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel footerLabel = new JLabel("© 2024 Dealer Mobil App - Real-time Monitoring System");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(Color.DARK_GRAY);

        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void setupFrame() {
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }

    @Override
    public void dispose() {
        if (dashboardPanel != null) {
            dashboardPanel.stopRefreshTimer();
        }
        super.dispose();
    }
}