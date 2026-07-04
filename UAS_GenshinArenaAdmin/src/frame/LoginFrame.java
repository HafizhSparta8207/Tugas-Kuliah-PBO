package frame;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import koneksi.Koneksi;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblUser, lblPass, lblTitle;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Genshin Arena - Login Panel");

        lblTitle = new JLabel("GENSHIN ARENA LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblUser = new JLabel("Username:");
        lblPass = new JLabel("Password:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        btnLogin.addActionListener(this::btnLoginActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(lblTitle)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser).addComponent(lblPass))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
            .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblTitle).addGap(20)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblUser).addComponent(txtUsername))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPass).addComponent(txtPassword))
            .addGap(15).addComponent(btnLogin)
        );
        pack();
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat datang " + username);
                new MenuUtamaFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}