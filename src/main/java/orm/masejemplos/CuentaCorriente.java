package orm.masejemplos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class CuentaCorriente extends CuentaBancaria {

    private float descubierto;

    protected CuentaCorriente() {
    }

    public CuentaCorriente(float monto) {
        super(monto);
    }

    private float getDescubierto() {
        return descubierto;
    }

    private void setDescubierto(float descubierto) {
        this.descubierto = descubierto;
    }
}
