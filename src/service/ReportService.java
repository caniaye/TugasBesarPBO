package service;

import exception.CustomException;
import report.ReportMobil;
import report.ReportPenjualan;

public class ReportService {

    public void generateMobilReport() throws CustomException {
        try {
            ReportMobil.generatePDF();
        } catch (Exception e) {
            throw new CustomException(
                "Gagal generate laporan mobil: " + e.getMessage(), e
            );
        }
    }

    public void generatePenjualanReport() throws CustomException {
        try {
            ReportPenjualan.generatePDF();
        } catch (Exception e) {
            throw new CustomException(
                "Gagal generate laporan penjualan: " + e.getMessage(), e
            );
        }
    }
}
