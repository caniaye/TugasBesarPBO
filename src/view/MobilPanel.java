package view;

import service.MobilService;
import model.Mobil;
import exception.CustomException;
import view.components.CustomTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class MobilPanel extends JPanel {
    private JTable mobilTable;
    private DefaultTableModel tableModel;
    private JTextField merkField, modelField, tahunField, warnaField, hargaField, stokField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private int selectedMobilId = -1;
    
    private final MobilService mobilService;
    
    public MobilPanel() {
        mobilService = new MobilService();
        initComponents();
        loadMobilData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Manajemen Mobil");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Merk", "Model", "Tahun", "Warna", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 || columnIndex == 3 || columnIndex == 6 ? Integer.class : String.class;
            }
        };
        
        mobilTable = new CustomTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(mobilTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Mobil"));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("Merk:"));
        merkField = new JTextField();
        formPanel.add(merkField);
        
        formPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        formPanel.add(modelField);
        
        formPanel.add(new JLabel("Tahun:"));
        tahunField = new JTextField();
        formPanel.add(tahunField);
        
        formPanel.add(new JLabel("Warna:"));
        warnaField = new JTextField();
        formPanel.add(warnaField);
        
        formPanel.add(new JLabel("Harga:"));
        hargaField = new JTextField();
        formPanel.add(hargaField);
        
        formPanel.add(new JLabel("Stok:"));
        stokField = new JTextField();
        formPanel.add(stokField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        addButton = new JButton("Tambah");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Hapus");
        clearButton = new JButton("Clear");
        
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Add form and buttons to main panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add listeners
        setupListeners();
    }
    
    private void setupListeners() {
        mobilTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && mobilTable.getSelectedRow() != -1) {
                int selectedRow = mobilTable.getSelectedRow();
                selectedMobilId = (int) mobilTable.getValueAt(selectedRow, 0);
                merkField.setText(mobilTable.getValueAt(selectedRow, 1).toString());
                modelField.setText(mobilTable.getValueAt(selectedRow, 2).toString());
                tahunField.setText(mobilTable.getValueAt(selectedRow, 3).toString());
                warnaField.setText(mobilTable.getValueAt(selectedRow, 4).toString());
                hargaField.setText(mobilTable.getValueAt(selectedRow, 5).toString());
                stokField.setText(mobilTable.getValueAt(selectedRow, 6).toString());
            }
        });
        
        addButton.addActionListener(e -> addMobil());
        updateButton.addActionListener(e -> updateMobil());
        deleteButton.addActionListener(e -> deleteMobil());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void loadMobilData() {
        try {
            List<Mobil> mobilList = mobilService.getAllMobil();
            tableModel.setRowCount(0);
            
            for (Mobil mobil : mobilList) {
                Object[] row = {
                    mobil.getId(),
                    mobil.getMerk(),
                    mobil.getModel(),
                    mobil.getTahun(),
                    mobil.getWarna(),
                    "Rp " + mobil.getHarga().toString(),
                    mobil.getStok()
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
    
    private void addMobil() {
        try {
            Mobil mobil = validateAndCreateMobil();
            mobilService.addMobil(mobil);
            loadMobilData();
            clearForm();
            JOptionPane.showMessageDialog(this, 
                "Mobil berhasil ditambahkan!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (CustomException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateMobil() {
        if (selectedMobilId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih mobil yang akan diupdate!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Mobil mobil = validateAndCreateMobil();
            mobil.setId(selectedMobilId);
            mobilService.updateMobil(mobil);
            loadMobilData();
            clearForm();
            JOptionPane.showMessageDialog(this, 
                "Mobil berhasil diupdate!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (CustomException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteMobil() {
        if (selectedMobilId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih mobil yang akan dihapus!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus mobil ini?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                mobilService.deleteMobil(selectedMobilId);
                loadMobilData();
                clearForm();
                JOptionPane.showMessageDialog(this, 
                    "Mobil berhasil dihapus!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (CustomException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private Mobil validateAndCreateMobil() throws CustomException, NumberFormatException {
        String merk = merkField.getText().trim();
        String model = modelField.getText().trim();
        int tahun = Integer.parseInt(tahunField.getText().trim());
        String warna = warnaField.getText().trim();
        BigDecimal harga = new BigDecimal(hargaField.getText().trim().replace("Rp", "").trim());
        int stok = Integer.parseInt(stokField.getText().trim());
        
        if (merk.isEmpty() || model.isEmpty() || warna.isEmpty()) {
            throw new CustomException("Semua field harus diisi!");
        }
        
        return new Mobil(merk, model, tahun, warna, harga, stok);
    }
    
    private void clearForm() {
        selectedMobilId = -1;
        merkField.setText("");
        modelField.setText("");
        tahunField.setText("");
        warnaField.setText("");
        hargaField.setText("");
        stokField.setText("");
        mobilTable.clearSelection();
    }
}