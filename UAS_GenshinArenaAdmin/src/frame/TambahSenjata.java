package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import koneksi.Koneksi;

public class TambahSenjata extends JDialog {
    private JTextField txtNama, txtBaseAtk;
    private JComboBox<String> cmbTipe, cmbRarity;
    private JButton btnSimpan, btnBatal;
    private JLabel lblNama, lblTipe, lblRarity, lblAtk;
    private int idEdit = 0;
    private TampilSenjata parent;

    public TambahSenjata(JFrame parentFrame, boolean modal, int idEdit) {
        super(parentFrame, modal);
        this.parent = (TampilSenjata) parentFrame;
        this.idEdit = idEdit;
        initComponents();
        if (idEdit > 0) loadDataEdit();
        setLocationRelativeTo(parentFrame);
    }

    private void initComponents() {
        lblNama = new JLabel("Nama Senjata:");
        lblTipe = new JLabel("Tipe Senjata:");
        lblRarity = new JLabel("Rarity (Star):");
        lblAtk = new JLabel("Base ATK:");

        txtNama = new JTextField();
        txtBaseAtk = new JTextField();
        cmbTipe = new JComboBox<>(new String[]{"Pedang", "Polearm", "Busur", "Catalyst", "Claymore"});
        cmbRarity = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        
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
                .addComponent(lblNama).addComponent(lblTipe).addComponent(lblRarity).addComponent(lblAtk).addComponent(btnSimpan))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtNama, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbTipe, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbRarity, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtBaseAtk, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBatal))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblNama).addComponent(txtNama))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblTipe).addComponent(cmbTipe))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblRarity).addComponent(cmbRarity))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblAtk).addComponent(txtBaseAtk))
            .addGap(15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSimpan).addComponent(btnBatal))
        );
        pack();
    }

    private void loadDataEdit() {
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM senjata WHERE id_senjata=?");
            ps.setInt(1, idEdit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama_senjata"));
                cmbTipe.setSelectedItem(rs.getString("tipe_senjata"));
                cmbRarity.setSelectedItem(String.valueOf(rs.getInt("rarity")));
                txtBaseAtk.setText(String.valueOf(rs.getInt("base_atk")));
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat: " + e.getMessage());
        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        String nama = txtNama.getText().trim();
        String tipe = cmbTipe.getSelectedItem().toString();
        String rarityStr = cmbRarity.getSelectedItem().toString();
        String atkStr = txtBaseAtk.getText().trim();

        if (nama.isEmpty() || atkStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua form data senjata harus terisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int rarity = Integer.parseInt(rarityStr);
            int atk = Integer.parseInt(atkStr); // Validasi konversi angka angka

            Connection conn = Koneksi.getConnection();
            if (idEdit == 0) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO senjata (nama_senjata, tipe_senjata, rarity, base_atk) VALUES (?, ?, ?, ?)");
                ps.setString(1, nama);
                ps.setString(2, tipe);
                ps.setInt(3, rarity);
                ps.setInt(4, atk);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = conn.prepareStatement("UPDATE senjata SET nama_senjata=?, tipe_senjata=?, rarity=?, base_atk=? WHERE id_senjata=?");
                ps.setString(1, nama);
                ps.setString(2, tipe);
                ps.setInt(3, rarity);
                ps.setInt(4, atk);
                ps.setInt(5, idEdit);
                ps.executeUpdate();
            }
            parent.loadData("");
            this.dispose();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Base ATK wajib disi dengan data angka murni!", "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
    }
}