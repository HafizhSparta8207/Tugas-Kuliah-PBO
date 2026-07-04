package tugas2_methodconstructor;

public class IemAudio {
    // Atribut objek (Karakteristik IEM)
    public String brand;
    public String model;
    public double price;
    public String signature;

    // Constructor 1: Tanpa Parameter (Default / No-Arg Constructor)
    public IemAudio() {
        this.brand = "Generic Brand";
        this.model = "Standard IEM";
        this.price = 150000.0;
        this.signature = "Balanced";
    }

    // Constructor 2: Dengan Parameter Lengkap (Parameterized Constructor)
    public IemAudio(String brand, String model, double price, String signature) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.signature = signature;
    }

    // Method 1: TANPA Nilai Balik (Void Method)
    // Berfungsi langsung mencetak data spesifikasi ke layar console
    public void cetakSpesifikasi() {
        System.out.println("=== SPESIFIKASI AUDIO GEAR ===");
        System.out.println("Brand            : " + this.brand);
        System.out.println("Model            : " + this.model);
        System.out.println("Harga Resmi      : Rp" + this.price);
        System.out.println("Karakter Suara   : " + this.signature);
    }

    // Method 2: DENGAN Nilai Balik (Return Method berformat String)
    // Berfungsi menganalisis kelas harga perangkat dan mengembalikan teks hasil analisis
    public String dapatkanRekomendasi() {
        if (this.price > 1000000.0) {
            return "Perangkat " + this.model + " termasuk dalam kategori Mid-Fi/Premium Setup.";
        } else {
            return "Perangkat " + this.model + " termasuk dalam kategori Budget King yang ramah kantong.";
        }
    }
}