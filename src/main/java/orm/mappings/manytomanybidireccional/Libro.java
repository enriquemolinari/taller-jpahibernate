package orm.mappings.manytomanybidireccional;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Libro {
    @Id
    private String isbn;
    private String titulo;
    @ManyToMany(mappedBy = "libro")
    private List<Autor> autores;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = new java.util.ArrayList<>();
    }

    public void agregarAutor(Autor autor) {
        this.autores.add(autor);
        autor.agregarLibro(this);
    }

    public String nombre() {
        return titulo;
    }

    public String autores() {
        return this.autores.stream()
                .map(Autor::nombreCompleto)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No hay autores");
    }
}
