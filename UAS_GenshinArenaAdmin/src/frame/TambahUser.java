package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import koneksi.Koneksi;

public class TambahUser extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbLevel;
    private JButton btnSimpan, btnBatal;
    private JLabel lblUser, lblPass, lblLevel;
    private int idUserEdit = 0;
    private TampilUser parentFrame;

    public TambahUser(JFrame parent, boolean modal, int idEdit) {
        super(parent, modal);
        this.parentFrame = (TampilUser) parent;
        this.idUserEdit = idEdit;
        initComponents();
        if (idUserEdit > 0) {
            loadDataEdit();
            setTitle("Form Edit User");
        } else {
            setTitle("Form Tambah User");
        }
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        lblUser = new JLabel("Username:");
        lblPass = new JLabel("Password:");
        lblLevel = new JLabel("Level Akses:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmbLevel = new JComboBox<>(new String[]{"admin", "user"});
        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");

        btnBatal.addActionListener(e -> this.dispose());
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblUser).addComponent(lblPass).addComponent(lblLevel).addComponent(btnSimpan))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbLevel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBatal))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblUser).addComponent(txtUsername))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPass).addComponent(txtPassword))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblLevel).addComponent(cmbLevel))
            .addGap(15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSimpan).addComponent(btnBatal))
        );
        pack();
    }

    private void loadDataEdit() {
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT username, level FROM users WHERE id_user=?");
            ps.setInt(1, idUserEdit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtUsername.setText(rs.getString("username"));
                cmbLevel.setSelectedItem(rs.getString("level"));
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat data edit: " + e.getMessage());
        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String level = cmbLevel.getSelectedItem().toString();

        if (username.isEmpty() || (idUserEdit == 0 && password.isEmpty())) {
            JOptionPane.showMessageDialog(this, "Data form tidak valid/belum terisi penuh!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();
            
            // Validasi pengecekan duplikasi username
            String sqlCek = "SELECT id_user FROM users WHERE username=? AND id_user != ?";
            PreparedStatement psCek = conn.prepareStatement(sqlCek);
            psCek.setString(1, username);
            psCek.setInt(2, idUserEdit);
            ResultSet rsCek = psCek.executeQuery();
            if (rsCek.next()) {
                JOptionPane.showMessageDialog(this, "Username telah digunakan oleh akun lain!", "Duplikasi Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idUserEdit == 0) {
                // Operasi Insert
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, level) VALUES (?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, level);
                ps.executeUpdate();
            } else {
                // Operasi Update
                String sqlUpdate = "UPDATE users SET username=?, level=?";
                if (!password.isEmpty()) sqlUpdate += ", password=?";
                sqlUpdate += " WHERE id_user=?";
                
                PreparedStatement ps = conn.prepareStatement(sqlUpdate);
                ps.setString(1, username);
                ps.setString(2, level);
                if (!password.isEmpty()) {
                    ps.setString(3, password);
                    ps.setInt(4, idUserEdit);
                } else {
                    ps.setInt(3, idUserEdit);
                }
                ps.executeUpdate();
            }
            
            parentFrame.loadData("");
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }
}