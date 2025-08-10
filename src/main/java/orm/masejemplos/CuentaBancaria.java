package orm.masejemplos;

import jakarta.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name = "tipo_cuenta")
public class CuentaBancaria {

    @Id
    @GeneratedValue
    private long id;

    private float saldo;

    protected CuentaBancaria() {
    }

    protected CuentaBancaria(float monto) {
        this.saldo = monto;
    }

    private long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    private float getSaldo() {
        return saldo;
    }

    private void setSaldo(float saldo) {
        this.saldo = saldo;
    }
}
