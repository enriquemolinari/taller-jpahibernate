package orm.context.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Libro {
    @Id
    private String isbn;
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn")
    private List<Autor> autores;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = new ArrayList<>();
    }

    public String nombre() {
        return titulo;
    }

    public int cantidadAutores() {
        return this.autores.size();
    }

    public void agregarAutor(Autor unAutor) {
        this.autores.add(unAutor);
    }

    public String[] autores() {
        return this.autores.stream()
                .map(Autor::nombreCompleto)
                .toArray(String[]::new);
    }
}
