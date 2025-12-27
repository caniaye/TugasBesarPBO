package dao;

import config.DatabaseConnection;
import model.Mobil;
import exception.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobilDAO {

    public List<Mobil> getAllMobil() throws CustomException {
        List<Mobil> mobilList = new ArrayList<>();
        String sql = "SELECT * FROM mobil ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mobil mobil = new Mobil();
                mobil.setId(rs.getInt("id"));
                mobil.setMerk(rs.getString("merk"));
                mobil.setModel(rs.getString("model"));
                mobil.setTahun(rs.getInt("tahun"));
                mobil.setWarna(rs.getString("warna"));
                mobil.setHarga(rs.getBigDecimal("harga"));
                mobil.setStok(rs.getInt("stok"));
                mobil.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                mobil.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                mobilList.add(mobil);
            }
        } catch (SQLException e) {
            throw new CustomException("Error mengambil data mobil: " + e.getMessage(), e);
        }
        return mobilList;
    }

    public void addMobil(Mobil mobil) throws CustomException {
        String sql = "INSERT INTO mobil (merk, model, tahun, warna, harga, stok) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mobil.getMerk());
            pstmt.setString(2, mobil.getModel());
            pstmt.setInt(3, mobil.getTahun());
            pstmt.setString(4, mobil.getWarna());
            pstmt.setBigDecimal(5, mobil.getHarga());
            pstmt.setInt(6, mobil.getStok());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("Error menambah mobil: " + e.getMessage(), e);
        }
    }

    public void updateMobil(Mobil mobil) throws CustomException {
        String sql = "UPDATE mobil SET merk = ?, model = ?, tahun = ?, warna = ?, harga = ?, stok = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mobil.getMerk());
            pstmt.setString(2, mobil.getModel());
            pstmt.setInt(3, mobil.getTahun());
            pstmt.setString(4, mobil.getWarna());
            pstmt.setBigDecimal(5, mobil.getHarga());
            pstmt.setInt(6, mobil.getStok());
            pstmt.setInt(7, mobil.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("Error mengupdate mobil: " + e.getMessage(), e);
        }
    }

    public void deleteMobil(int id) throws CustomException {
        String sql = "DELETE FROM mobil WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("Error menghapus mobil: " + e.getMessage(), e);
        }
    }

    public Mobil getMobilById(int id) throws CustomException {
        String sql = "SELECT * FROM mobil WHERE id = ?";
        Mobil mobil = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                mobil = new Mobil();
                mobil.setId(rs.getInt("id"));
                mobil.setMerk(rs.getString("merk"));
                mobil.setModel(rs.getString("model"));
                mobil.setTahun(rs.getInt("tahun"));
                mobil.setWarna(rs.getString("warna"));
                mobil.setHarga(rs.getBigDecimal("harga"));
                mobil.setStok(rs.getInt("stok"));
            }
        } catch (SQLException e) {
            throw new CustomException("Error mengambil mobil by ID: " + e.getMessage(), e);
        }
        return mobil;
    }

    public int getTotalStok() throws CustomException {
        String sql = "SELECT SUM(stok) as total_stok FROM mobil";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total_stok");
            }
        } catch (SQLException e) {
            throw new CustomException("Error menghitung total stok: " + e.getMessage(), e);
        }
        return 0;
    }
}