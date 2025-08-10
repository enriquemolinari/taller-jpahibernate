package orm.mappings.bidireccional;

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
    @OneToMany(mappedBy = "autor")
    private List<Autoria> autorias;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.autorias = new java.util.ArrayList<>();
    }
    
    public String nombreCompleto() {
        return nombre + " " + apellido;
    }
}
