package orm.mappings.bidireccional;

import jakarta.persistence.*;

@Entity
public class Autoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private float porcentajeAutoria;
    @ManyToOne
    private Libro libro;
    @ManyToOne
    private Autor autor;

    public Autoria(float porcentajeAutoria, Libro libro, Autor autor) {
        this.porcentajeAutoria = porcentajeAutoria;
        this.libro = libro;
        this.autor = autor;
    }

    public float porcentajeAutoria() {
        return porcentajeAutoria;
    }

    public String nombreCompleto() {
        return this.autor.nombreCompleto() + " (" + this.porcentajeAutoria + "%)";
    }
}
