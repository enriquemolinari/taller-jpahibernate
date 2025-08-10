package orm.mappings.manytomanybidireccional;

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
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String apellido;
    @ManyToMany
    private List<Libro> libros;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.libros = new java.util.ArrayList<>();
    }

    void agregarLibro(Libro libro) {
        this.libros.add(libro);
    }

    public String nombreCompleto() {
        return nombre + " " + apellido;
    }
}
