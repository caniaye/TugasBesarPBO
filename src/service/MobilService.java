package service;

import dao.MobilDAO;
import model.Mobil;
import exception.CustomException;
import java.util.List;

public class MobilService {
    private final MobilDAO mobilDAO;

    public MobilService() {
        this.mobilDAO = new MobilDAO();
    }

    public List<Mobil> getAllMobil() throws CustomException {
        return mobilDAO.getAllMobil();
    }

    public void addMobil(Mobil mobil) throws CustomException {
        validateMobil(mobil);
        mobilDAO.addMobil(mobil);
    }

    public void updateMobil(Mobil mobil) throws CustomException {
        validateMobil(mobil);
        mobilDAO.updateMobil(mobil);
    }

    public void deleteMobil(int id) throws CustomException {
        mobilDAO.deleteMobil(id);
    }

    public int getTotalStok() throws CustomException {
        return mobilDAO.getTotalStok();
    }

    private void validateMobil(Mobil mobil) throws CustomException {
        if (mobil.getMerk() == null || mobil.getMerk().trim().isEmpty()) {
            throw new CustomException("Merk mobil harus diisi");
        }
        if (mobil.getModel() == null || mobil.getModel().trim().isEmpty()) {
            throw new CustomException("Model mobil harus diisi");
        }
        if (mobil.getTahun() < 1900 || mobil.getTahun() > 2100) {
            throw new CustomException("Tahun mobil tidak valid");
        }
        if (mobil.getHarga() == null || mobil.getHarga().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new CustomException("Harga mobil harus lebih dari 0");
        }
        if (mobil.getStok() < 0) {
            throw new CustomException("Stok tidak boleh negatif");
        }
    }
}