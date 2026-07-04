package tugas3_inheritance;

// Menggunakan kata kunci 'extends' untuk mewarisi sifat dari AudioGear
public class IemGear extends AudioGear {
    // Tambahan 2 Atribut Baru khusus untuk IEM
    public String cableType;
    public boolean isDetachable;

    // Constructor Subclass yang meneruskan data ke Constructor Induk via 'super'
    public IemGear(String brand, String model, double price, int driverCount, String signature, String cableType, boolean isDetachable) {
        // Memanggil constructor 5 parameter milik superclass
        super(brand, model, price, driverCount, signature);
        this.cableType = cableType;
        this.isDetachable = isDetachable;
    }

    // Tambahan Method 1 - Tanpa Nilai Balik (Void)
    public void tuneAudio() {
        System.out.println("Info Tuning: Karakter suara " + model + " dioptimalkan menjadi " + signature + " dengan kabel " + cableType + ".");
    }

    // Tambahan Method 2 - Dengan Nilai Balik (String)
    public String getSpecsSummary() {
        String fiturKabel = isDetachable ? "Bisa Dilepas (Detachable)" : "Kabel Paten";
        return "Ringkasan IEM: " + brand + " " + model + " [" + driverCount + " Driver | " + cableType + " | " + fiturKabel + "].";
    }
}