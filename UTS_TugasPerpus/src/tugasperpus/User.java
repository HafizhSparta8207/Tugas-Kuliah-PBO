package tugasperpus;

public class User {
    // 1. Atribut harus di dalam kurung kurawal class
    int id;
    String email, password;

    // 2. Method juga harus di dalam sini
    public void index() { 
        System.out.println("SQL: SELECT * FROM user"); 
    }
    
    public void store() { 
        System.out.println("SQL: INSERT INTO user (email, password) VALUES ('" + email + "', '" + password + "')"); 
    }
    
    public void update() { 
        System.out.println("SQL: UPDATE user SET email='" + email + "' WHERE id=" + id); 
    }
    
    public void destroy() { 
        System.out.println("SQL: DELETE FROM user WHERE id=" + id); 
    }

    public void create() {}
    public void edit() {}
}