package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

public class TampilKarakter extends JFrame {
    private JTable tblKarakter;
    private JTextField txtSearch;
    private JButton btnTambah, btnRefresh, btnEdit, btnHapus, btnSearch;
    private JScrollPane scrollPane;

    public TampilKarakter() {
        initComponents();
        loadData("");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Panel Database Karakter Teyvat");

        txtSearch = new JTextField();
        btnSearch = new JButton("Cari");
        btnTambah = new JButton("Tambah");
        btnRefresh = new JButton("Refresh");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        
        tblKarakter = new JTable();
        scrollPane = new JScrollPane(tblKarakter);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));
        btnRefresh.addActionListener(e -> { txtSearch.setText(""); loadData(""); });
        btnTambah.addActionListener(e -> new TambahKarakter(this, true, 0).setVisible(true));
        
        btnEdit.addActionListener(e -> {
            int row = tblKarakter.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data karakter!");
                return;
            }
            int id = (int) tblKarakter.getValueAt(row, 0);
            new TambahKarakter(this, true, id).setVisible(true);
        });

        btnHapus.addActionListener(e -> {
            int row = tblKarakter.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih karakter yang ingin dieliminasi!");
                return;
            }
            int id = (int) tblKarakter.getValueAt(row, 0);
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data karakter ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = Koneksi.getConnection();
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM karakter WHERE id_karakter=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    loadData("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error Hapus: " + ex.getMessage());
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
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
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
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama Karakter", "Elemen", "Rarity", "Senjata Signature", "Deskripsi"}, 0);
        try {
            Connection conn = Koneksi.getConnection();
            // Menggunakan query SQL JOIN untuk mengambil nama senjata, bukan hanya ID
            String sql = "SELECT k.*, s.nama_senjata FROM karakter k LEFT JOIN senjata s ON k.id_senjata = s.id_senjata";
            if (!keyword.isEmpty()) {
                sql += " WHERE k.nama_karakter LIKE ? OR k.elemen LIKE ?";
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            if (!keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_karakter"),
                    rs.getString("nama_karakter"),
                    rs.getString("elemen"),
                    rs.getInt("rarity"),
                    rs.getString("nama_senjata") != null ? rs.getString("nama_senjata") : "Belum Set",
                    rs.getString("deskripsi")
                });
            }
            tblKarakter.setModel(model);
        } catch (Exception e) {
            System.out.println("Gagal memuat: " + e.getMessage());
        }
    }
}