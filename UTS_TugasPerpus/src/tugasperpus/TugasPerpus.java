package tugasperpus;

public class TugasPerpus {
    public static void main(String[] args) {
        // --- Eksekusi User ---
        User userBaru = new User();
        userBaru.id = 1;
        userBaru.email = "admin@mail.com";
        System.out.println("--- OUTPUT KELOLA USER ---");
        userBaru.index(); userBaru.store(); userBaru.update(); userBaru.destroy();

        // --- Eksekusi Penerbit ---
        Penerbit pgn = new Penerbit();
        pgn.id = 10;
        pgn.namaPenerbit = "Phoenix Gramedia Indonesia";
        System.out.println("\n--- OUTPUT KELOLA PENERBIT ---");
        pgn.index(); pgn.store(); pgn.update(); pgn.destroy();

        // --- Eksekusi Buku ---
        Buku buku1 = new Buku();
        buku1.id = 101;
        buku1.judul = "The Dangers in My Heart";
        buku1.penulis = "Norio Sakurai";
        buku1.tahun = 2018;
        buku1.penerbit = pgn; // Memasukkan objek penerbit ke dalam buku
        
        System.out.println("\n--- OUTPUT KELOLA BUKU ---");
        buku1.index(); buku1.store(); buku1.update(); buku1.destroy();
    }
}