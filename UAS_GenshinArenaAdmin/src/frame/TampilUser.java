package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

public class TampilUser extends JFrame {
    private JTable tblUser;
    private JTextField txtSearch;
    private JButton btnTambah, btnRefresh, btnEdit, btnHapus, btnSearch;
    private JScrollPane scrollPane;

    public TampilUser() {
        initComponents();
        loadData("");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Panel Pengelolaan Users");

        txtSearch = new JTextField();
        btnSearch = new JButton("Cari");
        btnTambah = new JButton("Tambah");
        btnRefresh = new JButton("Refresh");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        
        tblUser = new JTable();
        scrollPane = new JScrollPane(tblUser);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));
        btnRefresh.addActionListener(e -> { txtSearch.setText(""); loadData(""); });
        btnTambah.addActionListener(e -> new TambahUser(this, true, 0).setVisible(true));
        
        btnEdit.addActionListener(e -> {
            int row = tblUser.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin diedit!");
                return;
            }
            int id = (int) tblUser.getValueAt(row, 0);
            new TambahUser(this, true, id).setVisible(true);
        });

        btnHapus.addActionListener(e -> {
            int row = tblUser.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus!");
                return;
            }
            int id = (int) tblUser.getValueAt(row, 0);
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus user ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = Koneksi.getConnection();
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id_user=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    loadData("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus: " + ex.getMessage());
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSearch))
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnTambah).addComponent(btnEdit).addComponent(btnHapus).addComponent(btnRefresh))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(txtSearch).addComponent(btnSearch))
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnTambah).addComponent(btnEdit).addComponent(btnHapus).addComponent(btnRefresh))
        );
        pack();
    }

    public void loadData(String keyword) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID User", "Username", "Level"}, 0);
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT id_user, username, level FROM users";
            if (!keyword.isEmpty()) {
                sql += " WHERE username LIKE ?";
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            if (!keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_user"), rs.getString("username"), rs.getString("level")});
            }
            tblUser.setModel(model);
        } catch (Exception e) {
            System.out.println("Gagal memuat data user: " + e.getMessage());
        }
    }
}