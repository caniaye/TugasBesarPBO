package report;

import config.DatabaseConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperExportManager;

import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportMobil {

    public static void generatePDF() throws Exception {

        Connection conn = DatabaseConnection.getConnection();

        // PATH LANGSUNG (PALING AMAN DI VS CODE)
        String reportPath = "src/resources/reports/mobil_report.jrxml";

        JasperReport report =
                JasperCompileManager.compileReport(reportPath);

        Map<String, Object> params = new HashMap<>();
        params.put("judul", "LAPORAN DATA MOBIL");

        JasperPrint print =
                JasperFillManager.fillReport(report, params, conn);

        JasperExportManager.exportReportToPdfFile(
                print, "Laporan_Mobil.pdf"
        );

        JOptionPane.showMessageDialog(
                null, "Laporan Mobil (PDF) berhasil dibuat"
        );
    }
}
