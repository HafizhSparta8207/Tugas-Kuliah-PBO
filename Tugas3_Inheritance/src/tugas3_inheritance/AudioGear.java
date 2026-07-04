package tugas3_inheritance;

public class AudioGear {
    // 5 Atribut Utama (Sesuai Syarat Tugas)
    public String brand;
    public String model;
    public double price;
    public int driverCount;
    public String signature;

    // Constructor 1: Tanpa Parameter (Default)
    public AudioGear() {
        this.brand = "Unknown Brand";
        this.model = "Generic Audio";
        this.price = 0.0;
        this.driverCount = 1;
        this.signature = "Balanced";
    }

    // Constructor 2: Dengan 2 Parameter
    public AudioGear(String brand, String model) {
        this.brand = brand;
        this.model = model;
        this.price = 250000.0; // Harga standar awal
        this.driverCount = 1;
        this.signature = "Harman Target";
    }

    // Constructor 3: Dengan 5 Parameter Lengkap
    public AudioGear(String brand, String model, double price, int driverCount, String signature) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.driverCount = driverCount;
        this.signature = signature;
    }

    // Method Create - Tanpa Nilai Balik (Void) berisi simulasi SQL
    public void createSQL() {
        System.out.println("SQL Executed: INSERT INTO audio_gear (brand, model, price, drivers, signature) VALUES "
                + "('" + brand + "', '" + model + "', " + price + ", " + driverCount + ", '" + signature + "');");
    }

    // Method Read - Dengan Nilai Balik (String) berisi simulasi SQL
    public String readSQL() {
        return "SQL Query: SELECT * FROM audio_gear WHERE brand = '" + brand + "' AND model = '" + model + "';";
    }

    // Method Update - Tanpa Nilai Balik (Void) berisi simulasi SQL
    public void updateSQL(double newPrice) {
        this.price = newPrice;
        System.out.println("SQL Executed: UPDATE audio_gear SET price = " + newPrice + " WHERE model = '" + model + "';");
    }

    // Method Delete - Dengan Nilai Balik (Boolean) berisi simulasi SQL
    public boolean deleteSQL(String modelTarget) {
        System.out.println("SQL Executed: DELETE FROM audio_gear WHERE model = '" + modelTarget + "';");
        return true; 
    }
}