package view;

import config.DatabaseConnection;
import javax.swing.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        initComponents();
    }

    private void initComponents() {
        JLabel lblUser = new JLabel("Username");
        JLabel lblPass = new JLabel("Password");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        lblUser.setBounds(30, 30, 80, 25);
        txtUsername.setBounds(120, 30, 120, 25);

        lblPass.setBounds(30, 70, 80, 25);
        txtPassword.setBounds(120, 70, 120, 25);

        btnLogin.setBounds(90, 110, 100, 30);

        add(lblUser);
        add(txtUsername);
        add(lblPass);
        add(txtPassword);
        add(btnLogin);

        // Klik tombol
        btnLogin.addActionListener(e -> login());

        // Tekan ENTER
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi");
            return;
        }

        String sql = "SELECT * FROM user WHERE username=? AND password=?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login berhasil");

                    // PANGGIL MAINFRAME DENGAN USERNAME
                    new MainFrame(username).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username atau password salah");
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal login: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}