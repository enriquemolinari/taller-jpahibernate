package orm.mappings.onetomany;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String isbn;
    private String titulo;
    @OneToMany
    //para que no genere una tabla intermedia, se usa esta anotacion
    //id_libro es el nombre de la columna que colocara en la tabla para Autor para referencia a la PK id en libro
    @JoinColumn(name = "id_libro")
    private List<Autor> autores;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = new java.util.ArrayList<>();
    }

    public void agregarAutor(Autor autor) {
        this.autores.add(autor);
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
