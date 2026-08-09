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
    //para que es el mappedby?
    //1. Si no pongo el mappedBy, Hibernate crea dos tablas relacionadas.
    //2. Si agrego el mappedBy crea solo una tabla para la relacion entre Libros y Autores.
    //Pero, para que se persista esa relacion tengo que desde autores agregarle el libro.
    //Igual para ser consistente en memoria con lo que esta persistido siempre tengo que relacionar ambos lados
    //en las relaciones bidireccionales.
    @ManyToMany(mappedBy = "libros")
    private List<Autor> autores;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = new java.util.ArrayList<>();
    }

    public void agregarAutor(Autor autor) {
        //Necesaria para tener la info en memoria
        this.autores.add(autor);
        //Esta es la que persiste la relacion en la tabla intermedia
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
