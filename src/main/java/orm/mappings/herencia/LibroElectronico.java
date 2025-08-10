package orm.mappings.herencia;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("libro_electronico")
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibroElectronico extends Libro {
    private String formato;
    private String peso;

    public LibroElectronico(String isbn, String titulo, String formato, String peso) {
        super(isbn, titulo);
        this.formato = formato;
        this.peso = peso;
    }

    public String formato() {
        return this.formato;
    }

    public String peso() {
        return this.peso;
    }
}
