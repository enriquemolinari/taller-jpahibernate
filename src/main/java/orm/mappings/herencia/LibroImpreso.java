package orm.mappings.herencia;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("libro_impreso")
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibroImpreso extends Libro {
    @Embedded
    private FichaFisica fichaFisica;

    public LibroImpreso(String isbn, String titulo, FichaFisica fichaFisica) {
        super(isbn, titulo);
        this.fichaFisica = fichaFisica;
    }

    public String ficha() {
        return this.fichaFisica.toString();
    }

    @Override
    public String toString() {
        return "LibroImpreso{" +
                "nombre= " + this.nombre() +
                ", autores= " + this.autores() +
                "fichaFisica=" + fichaFisica +
                '}';
    }
}
