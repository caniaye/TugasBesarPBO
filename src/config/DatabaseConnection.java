package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dealer_mobil";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {}

    // SETIAP PANGGILAN = KONEKSI BARU (AMAN UNTUK JASPER)
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Gagal koneksi ke database: " + e.getMessage(), e);
        }
    }

    // TIDAK DIGUNAKAN LAGI
    public static void closeConnection() {
        // sengaja dikosongkan
    }
}
