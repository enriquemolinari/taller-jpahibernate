package orm.mappings.components;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FichaFisica {
    private String dimensiones;
    private String peso;

    public FichaFisica(String dimensiones, String peso) {
        this.dimensiones = dimensiones;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return dimensiones + " - " + peso;
    }
}
