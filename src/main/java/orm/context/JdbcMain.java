package orm.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcMain {
    public static void main(String[] args) {
         var libro = new Libro("978-0132350884", "Smalltalk Best Practices Patters");
         libro.agregarAutor(new Autor("Kent", "Beck"));
         create(libro);
    }

    public static void create(Libro unLibro) {
        String url = "jdbc:derby://localhost:1527/ejemplo;create=true";
        
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);
            
            try {
                // Insert libro, isbn es la PK
                String insertLibro = "INSERT INTO libro (isbn, titulo) VALUES (?, ?)";

                try (PreparedStatement psLibro = conn.prepareStatement(insertLibro)) {
                    psLibro.setString(1, unLibro.isbn());
                    psLibro.setString(2, unLibro.nombre());
                    psLibro.executeUpdate();
                }
                
                // Insert autores del libro
                String insertAutor = "INSERT INTO autor (nombre, apellido, libro_isbn) VALUES (?, ?, ?)";
                try (PreparedStatement psAutor = conn.prepareStatement(insertAutor)) {
                    var autores = unLibro.autores();
                    for (String autor : autores) {
                        String[] partes = autor.split(" ");
                        psAutor.setString(1, partes[0]);
                        psAutor.setString(2, partes[1]);
                        psAutor.setString(3, unLibro.isbn()); //FK
                        psAutor.executeUpdate();
                    }
                }
                // Commit si todo va bien
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error en la transacción JDBC, se realizó rollback", e);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en la transacción JDBC", e);
        }
    }
}
