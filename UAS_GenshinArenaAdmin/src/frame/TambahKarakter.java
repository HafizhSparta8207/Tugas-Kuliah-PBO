package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import koneksi.Koneksi;

public class TambahKarakter extends JDialog {
    private JTextField txtNama;
    private JComboBox<String> cmbElemen, cmbRarity, cmbSenjata;
    private JTextArea txtDeskripsi;
    private JScrollPane scrollArea;
    private JButton btnSimpan, btnBatal;
    private JLabel lblNama, lblElemen, lblRarity, lblSenjata, lblDesc;
    
    private int idEdit = 0;
    private TampilKarakter parent;
    private ArrayList<Integer> listIdSenjata = new ArrayList<>();

    public TambahKarakter(JFrame parentFrame, boolean modal, int idEdit) {
        super(parentFrame, modal);
        this.parent = (TampilKarakter) parentFrame;
        this.idEdit = idEdit;
        initComponents();
        loadComboBoxSenjata();
        if (idEdit > 0) loadDataEdit();
        setLocationRelativeTo(parentFrame);
    }

    private void initComponents() {
        lblNama = new JLabel("Nama Karakter:");
        lblElemen = new JLabel("Elemen:");
        lblRarity = new JLabel("Rarity (Star):");
        lblSenjata = new JLabel("Senjata Pilihan:");
        lblDesc = new JLabel("Deskripsi:");

        txtNama = new JTextField();
        cmbElemen = new JComboBox<>(new String[]{"Pyro", "Hydro", "Anemo", "Electro", "Dendro", "Cryo", "Geo"});
        cmbRarity = new JComboBox<>(new String[]{"4", "5"});
        cmbSenjata = new JComboBox<>();
        txtDeskripsi = new JTextArea(3, 20);
        scrollArea = new JScrollPane(txtDeskripsi);
        
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
                .addComponent(lblNama).addComponent(lblElemen).addComponent(lblRarity).addComponent(lblSenjata).addComponent(lblDesc).addComponent(btnSimpan))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtNama, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbElemen, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbRarity, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbSenjata, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollArea, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBatal))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblNama).addComponent(txtNama))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblElemen).addComponent(cmbElemen))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblRarity).addComponent(cmbRarity))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblSenjata).addComponent(cmbSenjata))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblDesc).addComponent(scrollArea))
            .addGap(15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSimpan).addComponent(btnBatal))
        );
        pack();
    }

    private void loadComboBoxSenjata() {
        try {
            Connection conn = Koneksi.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_senjata, nama_senjata FROM senjata");
            
            cmbSenjata.removeAllItems();
            listIdSenjata.clear();
            
            while (rs.next()) {
                listIdSenjata.add(rs.getInt("id_senjata"));
                cmbSenjata.addItem(rs.getString("nama_senjata"));
            }
        } catch (Exception e) {
            System.out.println("Gagal mengisi data senjata: " + e.getMessage());
        }
    }

    private void loadDataEdit() {
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM karakter WHERE id_karakter=?");
            ps.setInt(1, idEdit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama_karakter"));
                cmbElemen.setSelectedItem(rs.getString("elemen"));
                cmbRarity.setSelectedItem(String.valueOf(rs.getInt("rarity")));
                txtDeskripsi.setText(rs.getString("deskripsi"));
                
                int idSenjataKarakter = rs.getInt("id_senjata");
                int index = listIdSenjata.indexOf(idSenjataKarakter);
                if (index != -1) {
                    cmbSenjata.setSelectedIndex(index);
                }
            }
        } catch (Exception e) {
            System.out.println("Error load edit: " + e.getMessage());
        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        String nama = txtNama.getText().trim();
        String elemen = cmbElemen.getSelectedItem().toString();
        String rarityStr = cmbRarity.getSelectedItem().toString();
        String deskripsi = txtDeskripsi.getText().trim();

        if (nama.isEmpty() || cmbSenjata.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Nama Karakter tidak boleh kosong dan Senjata wajib dipilih!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int indexTerpilih = cmbSenjata.getSelectedIndex();
        int idSenjata = listIdSenjata.get(indexTerpilih);
        int rarity = Integer.parseInt(rarityStr);

        try {
            Connection conn = Koneksi.getConnection();
            if (idEdit == 0) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO karakter (nama_karakter, elemen, rarity, id_senjata, deskripsi) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, nama);
                ps.setString(2, elemen);
                ps.setInt(3, rarity);
                ps.setInt(4, idSenjata);
                ps.setString(5, deskripsi);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = conn.prepareStatement("UPDATE karakter SET nama_karakter=?, elemen=?, rarity=?, id_senjata=?, deskripsi=? WHERE id_karakter=?");
                ps.setString(1, nama);
                ps.setString(2, elemen);
                ps.setInt(3, rarity);
                ps.setInt(4, idSenjata);
                ps.setString(5, deskripsi);
                ps.setInt(6, idEdit);
                ps.executeUpdate();
            }
            parent.loadData("");
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
    }
}