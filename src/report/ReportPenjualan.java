package report;

import config.DatabaseConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperExportManager;

import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportPenjualan {

    public static void generatePDF() throws Exception {

        Connection conn = DatabaseConnection.getConnection();

        // PATH LANGSUNG (PALING AMAN DI VS CODE)
        String reportPath = "src/resources/reports/penjualan_report.jrxml";

        JasperReport report =
                JasperCompileManager.compileReport(reportPath);

        Map<String, Object> params = new HashMap<>();
        params.put("judul", "LAPORAN PENJUALAN");

        JasperPrint print =
                JasperFillManager.fillReport(report, params, conn);

        JasperExportManager.exportReportToPdfFile(
                print, "Laporan_Penjualan.pdf"
        );

        JOptionPane.showMessageDialog(
                null, "Laporan Penjualan (PDF) berhasil dibuat"
        );
    }
}
