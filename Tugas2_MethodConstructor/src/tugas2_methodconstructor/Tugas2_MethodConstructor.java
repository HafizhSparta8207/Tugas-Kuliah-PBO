package tugas2_methodconstructor;

public class Tugas2_MethodConstructor {

    public static void main(String[] args) {
        System.out.println("=== EKSEKUSI TUGAS 2: METHOD & CONSTRUCTOR ===");
        System.out.println();

        // 1. Instansiasi Objek 1 menggunakan Constructor 1 (Tanpa Parameter)
        System.out.println("[Pembuatan Objek 1: Menggunakan Constructor Default]");
        IemAudio iemBudget = new IemAudio();
        
        // Menjalankan method tanpa nilai balik (void)
        iemBudget.cetakSpesifikasi(); 
        
        // Menjalankan method dengan nilai balik (return String)
        String analisis1 = iemBudget.dapatkanRekomendasi();
        System.out.println("Hasil Analisis  : " + analisis1);
        System.out.println();

        // 2. Instansiasi Objek 2 menggunakan Constructor 2 (Dengan Parameter Lengkap)
        System.out.println("[Pembuatan Objek 2: Menggunakan Constructor Berparameter]");
        IemAudio iemPremium = new IemAudio("Simgot", "EA500LM", 1250000.0, "Harman Target");
        
        // Menjalankan method tanpa nilai balik (void)
        iemPremium.cetakSpesifikasi();
        
        // Menjalankan method dengan nilai balik (return String)
        String analisis2 = iemPremium.dapatkanRekomendasi();
        System.out.println("Hasil Analisis  : " + analisis2);
    }
}