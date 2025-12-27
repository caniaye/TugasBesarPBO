package view;

import service.PenjualanService;
import service.MobilService;
import model.Penjualan;
import model.Mobil;
import exception.CustomException;
import view.components.CustomTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PenjualanPanel extends JPanel {
    private JTable penjualanTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> mobilComboBox;
    private JTextField jumlahField, hargaTotalField, pembeliNamaField, pembeliTeleponField;
    private JButton addButton, clearButton;
    
    private final PenjualanService penjualanService;
    private final MobilService mobilService;
    private List<Mobil> mobilList;
    
    public PenjualanPanel() {
        penjualanService = new PenjualanService();
        mobilService = new MobilService();
        initComponents();
        loadPenjualanData();
        loadMobilData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Manajemen Penjualan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Mobil", "Jumlah", "Harga Total", "Pembeli", "Telepon", "Tanggal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 || columnIndex == 2 ? Integer.class : String.class;
            }
        };
        
        penjualanTable = new CustomTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(penjualanTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Penjualan"));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("Mobil:"));
        mobilComboBox = new JComboBox<>();
        formPanel.add(mobilComboBox);
        
        formPanel.add(new JLabel("Jumlah:"));
        jumlahField = new JTextField();
        formPanel.add(jumlahField);
        
        formPanel.add(new JLabel("Harga Total:"));
        hargaTotalField = new JTextField();
        formPanel.add(hargaTotalField);
        
        formPanel.add(new JLabel("Nama Pembeli:"));
        pembeliNamaField = new JTextField();
        formPanel.add(pembeliNamaField);
        
        formPanel.add(new JLabel("Telepon Pembeli:"));
        pembeliTeleponField = new JTextField();
        formPanel.add(pembeliTeleponField);
        
        formPanel.add(new JLabel("Tanggal:"));
        JTextField tanggalField = new JTextField(LocalDate.now().toString());
        tanggalField.setEditable(false);
        formPanel.add(tanggalField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        addButton = new JButton("Tambah Penjualan");
        clearButton = new JButton("Clear");
        
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        
        // Add listeners for auto-calculate
        jumlahField.addActionListener(e -> calculateTotal());
        mobilComboBox.addActionListener(e -> calculateTotal());
        
        // Add form and buttons to main panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add button listeners
        setupListeners();
    }
    
    private void setupListeners() {
        addButton.addActionListener(e -> addPenjualan());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void loadPenjualanData() {
        try {
            List<Penjualan> penjualanList = penjualanService.getAllPenjualan();
            tableModel.setRowCount(0);
            
            for (Penjualan penjualan : penjualanList) {
                Object[] row = {
                    penjualan.getId(),
                    penjualan.getMerkMobil() + " " + penjualan.getModelMobil(),
                    penjualan.getJumlah(),
                    "Rp " + penjualan.getHargaTotal().toString(),
                    penjualan.getPembeliNama(),
                    penjualan.getPembeliTelepon(),
                    penjualan.getTanggalJual().toString()
                };
                tableModel.addRow(row);
            }
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error Load Data", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadMobilData() {
        try {
            mobilList = mobilService.getAllMobil();
            mobilComboBox.removeAllItems();
            
            for (Mobil mobil : mobilList) {
                mobilComboBox.addItem(mobil.getMerk() + " " + mobil.getModel() + " (Stok: " + mobil.getStok() + ")");
            }
        } catch (CustomException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error Load Mobil", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void calculateTotal() {
        try {
            int selectedIndex = mobilComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < mobilList.size() && !jumlahField.getText().isEmpty()) {
                Mobil mobil = mobilList.get(selectedIndex);
                int jumlah = Integer.parseInt(jumlahField.getText().trim());
                BigDecimal total = mobil.getHarga().multiply(BigDecimal.valueOf(jumlah));
                hargaTotalField.setText(total.toString());
            }
        } catch (NumberFormatException e) {
            // Ignore
        }
    }
    
    private void addPenjualan() {
        try {
            Penjualan penjualan = validateAndCreatePenjualan();
            penjualanService.addPenjualan(penjualan);
            loadPenjualanData();
            loadMobilData();
            clearForm();
            JOptionPane.showMessageDialog(this, 
                "Penjualan berhasil ditambahkan!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (CustomException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Penjualan validateAndCreatePenjualan() throws CustomException, NumberFormatException {
        int selectedIndex = mobilComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            throw new CustomException("Pilih mobil terlebih dahulu!");
        }
        
        Mobil mobil = mobilList.get(selectedIndex);
        int jumlah = Integer.parseInt(jumlahField.getText().trim());
        BigDecimal hargaTotal = new BigDecimal(hargaTotalField.getText().trim());
        String pembeliNama = pembeliNamaField.getText().trim();
        String pembeliTelepon = pembeliTeleponField.getText().trim();
        
        if (pembeliNama.isEmpty() || pembeliTelepon.isEmpty()) {
            throw new CustomException("Data pembeli harus diisi!");
        }
        
        if (jumlah > mobil.getStok()) {
            throw new CustomException("Stok tidak mencukupi! Stok tersedia: " + mobil.getStok());
        }
        
        return new Penjualan(
            mobil.getId(),
            jumlah,
            hargaTotal,
            pembeliNama,
            pembeliTelepon,
            LocalDate.now()
        );
    }
    
    private void clearForm() {
        mobilComboBox.setSelectedIndex(0);
        jumlahField.setText("");
        hargaTotalField.setText("");
        pembeliNamaField.setText("");
        pembeliTeleponField.setText("");
    }
}