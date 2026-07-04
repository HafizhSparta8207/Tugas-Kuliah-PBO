package tugasperpus;

public class Penerbit {
    int id;
    String namaPenerbit, alamatPenerbit;

    public void index() { System.out.println("SQL: SELECT * FROM penerbit"); }
    public void store() { System.out.println("SQL: INSERT INTO penerbit (namaPenerbit, alamatPenerbit) VALUES ('" + namaPenerbit + "', '" + alamatPenerbit + "')"); }
    public void update() { System.out.println("SQL: UPDATE penerbit SET namaPenerbit='" + namaPenerbit + "' WHERE id=" + id); }
    public void destroy() { System.out.println("SQL: DELETE FROM penerbit WHERE id=" + id); }

    public void create() {}
    public void edit() {}
}
