package orm.masejemplos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CA")
public class CajaDeAhorro extends CuentaBancaria {

    protected CajaDeAhorro() {

    }

    public CajaDeAhorro(float monto) {
        super(monto);
    }

    public void extraer() {
        // todo...
    }

}
