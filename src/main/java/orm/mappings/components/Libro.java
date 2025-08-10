package orm.mappings.components;

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
    private String isbn;
    private String titulo;
    @OneToMany
    //para que no genere una tabla intermedia, se usa esta anotacion
    //isbn es el nombre de la columna que colocara en la tabla para Autor
    @JoinColumn(name = "isbn")
    private List<Autor> autores;
    @Embedded
    private FichaFisica fichaFisica;

    public Libro(String isbn, String titulo, FichaFisica fichaFisica) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fichaFisica = fichaFisica;
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

    public String ficha() {
        return this.fichaFisica.toString();
    }
}
