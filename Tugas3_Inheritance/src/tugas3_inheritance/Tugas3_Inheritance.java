package tugas3_inheritance;

public class Tugas3_Inheritance {

    public static void main(String[] args) {
        System.out.println("=== DEMO TUGAS 3: INHERITANCE & ENCAPSULATION ===");
        System.out.println();

        // 1. Membuat objek dari tiap-tiap Constructor Superclass (Ada 3 Objek)
        System.out.println("[Langkah 1: Inisialisasi 3 Objek Superclass]");
        AudioGear gear1 = new AudioGear(); // Constructor 1
        AudioGear gear2 = new AudioGear("Moondrop", "Chu II"); // Constructor 2
        AudioGear gear3 = new AudioGear("Simgot", "EA500LM", 1250000.0, 1, "Bright-Vocal"); // Constructor 3

        System.out.println("Objek 1 (Default) : " + gear1.brand + " " + gear1.model);
        System.out.println("Objek 2 (2 Param) : " + gear2.brand + " " + gear2.model);
        System.out.println("Objek 3 (5 Param) : " + gear3.brand + " " + gear3.model);
        System.out.println();

        // 2. Menjalankan Semua Method CRUD SQL pada Superclass
        System.out.println("[Langkah 2: Menjalankan Method CRUD SQL Superclass]");
        gear3.createSQL(); // Method void
        
        String hasilRead = gear3.readSQL(); // Method dengan nilai balik
        System.out.println(hasilRead);
        
        gear3.updateSQL(1190000.0); // Method void
        
        boolean statusHapus = gear3.deleteSQL("EA500LM"); // Method dengan nilai balik
        System.out.println("Hasil eksekusi delete: " + statusHapus);
        System.out.println();

        // 3. Membuat Objek dari Subclass (IemGear)
        System.out.println("[Langkah 3: Inisialisasi Objek Subclass]");
        IemGear iemPremium = new IemGear("Kinera", "Hodur", 4500000.0, 3, "Warm-Musical", "Silver Plated Copper", true);

        // 4. Menjalankan Semua Method Milik Subclass & Pembuktian Inheritance
        System.out.println("[Langkah 4: Menjalankan Method Baru Subclass]");
        iemPremium.tuneAudio(); // Method subclass void
        
        String summary = iemPremium.getSpecsSummary(); // Method subclass dengan nilai balik
        System.out.println(summary);
        
        System.out.println("--- Bukti Pewarisan: Subclass Memanggil Method SQL Induk ---");
        iemPremium.createSQL(); // Objek subclass bisa mengakses method createSQL milik induknya
    }
}