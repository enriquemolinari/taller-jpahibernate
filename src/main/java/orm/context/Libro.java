package orm.context;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Libro {
    @Id
    private String isbn;
    private String titulo;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
    }

    public String nombre() {
        return titulo;
    }

//    public String autor() {
//        return this.autor.nombreCompleto();
//    }
}
