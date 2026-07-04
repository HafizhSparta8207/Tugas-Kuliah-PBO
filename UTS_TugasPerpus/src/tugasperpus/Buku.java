package tugasperpus;

public class Buku {
    int id, tahun;
    String judul, penulis;
    Penerbit penerbit; // Menghubungkan ke Class Penerbit

    public void index() { System.out.println("SQL: SELECT * FROM buku"); }
    public void store() { 
        // Mengambil ID dari objek penerbit untuk disimpan
        System.out.println("SQL: INSERT INTO buku (judul, penulis, tahun, id_penerbit) VALUES ('" + judul + "', '" + penulis + "', " + tahun + ", " + penerbit.id + ")"); 
    }
    public void update() { System.out.println("SQL: UPDATE buku SET judul='" + judul + "' WHERE id=" + id); }
    public void destroy() { System.out.println("SQL: DELETE FROM buku WHERE id=" + id); }

    public void create() {}
    public void edit() {}
}
