package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;
    
    public static Connection getConnection() {
        if (koneksi == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/genshin_arena?useSSL=false&serverTimezone=Asia/Jakarta";
                String user = "root";
                String password = "";
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi database Genshin Arena berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Koneksi database gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }
}