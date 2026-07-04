package frame;

import java.awt.GridLayout;
import javax.swing.*;

public class MenuUtamaFrame extends JFrame {
    private JButton btnUser, btnSenjata, btnKarakter, btnLogout;

    public MenuUtamaFrame() {
        setTitle("Genshin Arena Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        btnUser = new JButton("Kelola Data Users");
        btnSenjata = new JButton("Kelola Data Senjata");
        btnKarakter = new JButton("Kelola Data Karakter");
        btnLogout = new JButton("Logout Sistem");

        btnUser.addActionListener(e -> { new TampilUser().setVisible(true); });
        btnSenjata.addActionListener(e -> { new TampilSenjata().setVisible(true); });
        btnKarakter.addActionListener(e -> { new TampilKarakter().setVisible(true); });
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        add(btnUser);
        add(btnSenjata);
        add(btnKarakter);
        add(btnLogout);
    }
}