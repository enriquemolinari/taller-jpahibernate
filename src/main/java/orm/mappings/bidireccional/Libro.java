package orm.mappings.bidireccional;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "libro")
    private List<Autoria> autorias;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autorias = new java.util.ArrayList<>();
    }

    public void agregarAutoria(Autoria autor) {
        this.autorias.add(autor);
    }

    public String nombre() {
        return titulo;
    }

    public String autores() {
        return this.autorias.stream()
                .map(Autoria::nombreCompleto)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No hay autores");
    }
}
