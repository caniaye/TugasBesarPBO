package dao;

import config.DatabaseConnection;
import model.Penjualan;
import exception.CustomException;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PenjualanDAO {
    
    public List<Penjualan> getAllPenjualan() throws CustomException {
        List<Penjualan> penjualanList = new ArrayList<>();
        String sql = "SELECT p.*, m.merk, m.model FROM penjualan p " +
                    "JOIN mobil m ON p.mobil_id = m.id ORDER BY p.tanggal_jual DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setId(rs.getInt("id"));
                penjualan.setMobilId(rs.getInt("mobil_id"));
                penjualan.setMerkMobil(rs.getString("merk"));
                penjualan.setModelMobil(rs.getString("model"));
                penjualan.setJumlah(rs.getInt("jumlah"));
                penjualan.setHargaTotal(rs.getBigDecimal("harga_total"));
                penjualan.setPembeliNama(rs.getString("pembeli_nama"));
                penjualan.setPembeliTelepon(rs.getString("pembeli_telepon"));
                penjualan.setTanggalJual(rs.getDate("tanggal_jual").toLocalDate());
                penjualan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                penjualanList.add(penjualan);
            }
        } catch (SQLException e) {
            throw new CustomException("Error mengambil data penjualan: " + e.getMessage(), e);
        }
        return penjualanList;
    }
    
    public void addPenjualan(Penjualan penjualan) throws CustomException {
        String sql = "INSERT INTO penjualan (mobil_id, jumlah, harga_total, pembeli_nama, pembeli_telepon, tanggal_jual) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, penjualan.getMobilId());
            pstmt.setInt(2, penjualan.getJumlah());
            pstmt.setBigDecimal(3, penjualan.getHargaTotal());
            pstmt.setString(4, penjualan.getPembeliNama());
            pstmt.setString(5, penjualan.getPembeliTelepon());
            pstmt.setDate(6, Date.valueOf(penjualan.getTanggalJual()));
            pstmt.executeUpdate();
            
            // Update stok mobil
            updateStokMobil(penjualan.getMobilId(), penjualan.getJumlah());
            
        } catch (SQLException e) {
            throw new CustomException("Error menambah penjualan: " + e.getMessage(), e);
        }
    }
    
    private void updateStokMobil(int mobilId, int jumlah) throws CustomException {
        String sql = "UPDATE mobil SET stok = stok - ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jumlah);
            pstmt.setInt(2, mobilId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new CustomException("Error update stok mobil: " + e.getMessage(), e);
        }
    }
    
    public BigDecimal getTotalPenjualan() throws CustomException {
        String sql = "SELECT SUM(harga_total) as total FROM penjualan";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            throw new CustomException("Error menghitung total penjualan: " + e.getMessage(), e);
        }
        return BigDecimal.ZERO;
    }
    
    public int getTotalUnitTerjual() throws CustomException {
        String sql = "SELECT SUM(jumlah) as total FROM penjualan";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new CustomException("Error menghitung total unit terjual: " + e.getMessage(), e);
        }
        return 0;
    }
}