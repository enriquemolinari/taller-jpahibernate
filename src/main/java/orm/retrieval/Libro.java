package orm.retrieval;

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
@Cacheable
public class Libro {
    @Id
    private String isbn;
    private String titulo;

    //, fetch = FetchType.EAGER
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "isbn")
    private List<Autor> autores;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = new java.util.ArrayList<>();
    }

    InfoLibroYAutores toRecord() {
        return new InfoLibroYAutores(this.titulo, this.autores());
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

    public int totalAutores() {
        return this.autores.size();
    }

    public String titulo() {
        return this.titulo;
    }

    public void removerAutorDeNombre(String unNombre) {
        this.autores.removeIf(autor -> autor.conNombre(unNombre));
    }
}
