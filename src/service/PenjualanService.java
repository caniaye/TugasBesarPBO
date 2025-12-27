package service;

import dao.PenjualanDAO;
import model.Penjualan;
import exception.CustomException;
import java.math.BigDecimal;
import java.util.List;

public class PenjualanService {
    private final PenjualanDAO penjualanDAO;
    
    public PenjualanService() {
        this.penjualanDAO = new PenjualanDAO();
    }
    
    public List<Penjualan> getAllPenjualan() throws CustomException {
        return penjualanDAO.getAllPenjualan();
    }
    
    public void addPenjualan(Penjualan penjualan) throws CustomException {
        validatePenjualan(penjualan);
        penjualanDAO.addPenjualan(penjualan);
    }
    
    public BigDecimal getTotalPenjualan() throws CustomException {
        return penjualanDAO.getTotalPenjualan();
    }
    
    public int getTotalUnitTerjual() throws CustomException {
        return penjualanDAO.getTotalUnitTerjual();
    }
    
    private void validatePenjualan(Penjualan penjualan) throws CustomException {
        if (penjualan.getMobilId() <= 0) {
            throw new CustomException("ID Mobil tidak valid");
        }
        if (penjualan.getJumlah() <= 0) {
            throw new CustomException("Jumlah harus lebih dari 0");
        }
        if (penjualan.getHargaTotal() == null || 
            penjualan.getHargaTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException("Harga total tidak valid");
        }
        if (penjualan.getPembeliNama() == null || 
            penjualan.getPembeliNama().trim().isEmpty()) {
            throw new CustomException("Nama pembeli harus diisi");
        }
        if (penjualan.getPembeliTelepon() == null || 
            penjualan.getPembeliTelepon().trim().isEmpty()) {
            throw new CustomException("Telepon pembeli harus diisi");
        }
        if (penjualan.getTanggalJual() == null) {
            throw new CustomException("Tanggal penjualan harus diisi");
        }
    }
}