package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

public class TampilSenjata extends JFrame {
    private JTable tblSenjata;
    private JTextField txtSearch;
    private JButton btnTambah, btnRefresh, btnEdit, btnHapus, btnSearch;
    private JScrollPane scrollPane;

    public TampilSenjata() {
        initComponents();
        loadData("");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Panel Database Senjata");

        txtSearch = new JTextField();
        btnSearch = new JButton("Cari");
        btnTambah = new JButton("Tambah");
        btnRefresh = new JButton("Refresh");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        
        tblSenjata = new JTable();
        scrollPane = new JScrollPane(tblSenjata);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));
        btnRefresh.addActionListener(e -> { txtSearch.setText(""); loadData(""); });
        btnTambah.addActionListener(e -> new TambahSenjata(this, true, 0).setVisible(true));
        
        btnEdit.addActionListener(e -> {
            int row = tblSenjata.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data senjata terlebih dahulu!");
                return;
            }
            int id = (int) tblSenjata.getValueAt(row, 0);
            new TambahSenjata(this, true, id).setVisible(true);
        });

        btnHapus.addActionListener(e -> {
            int row = tblSenjata.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris senjata yang ingin dihapus!");
                return;
            }
            int id = (int) tblSenjata.getValueAt(row, 0);
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus senjata ini? (Bisa merusak relasi data karakter)", "Peringatan", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = Koneksi.getConnection();
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM senjata WHERE id_senjata=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    loadData("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
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
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
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
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama Senjata", "Tipe", "Rarity", "Base ATK"}, 0);
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM senjata";
            if (!keyword.isEmpty()) {
                sql += " WHERE nama_senjata LIKE ?";
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            if (!keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_senjata"),
                    rs.getString("nama_senjata"),
                    rs.getString("tipe_senjata"),
                    rs.getInt("rarity"),
                    rs.getInt("base_atk")
                });
            }
            tblSenjata.setModel(model);
        } catch (Exception e) {
            System.out.println("Gagal memuat data: " + e.getMessage());
        }
    }
}