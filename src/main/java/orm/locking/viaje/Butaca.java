package orm.locking.viaje;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Butaca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numeroButaca;
    private boolean ocupada;
    private String nombrePasajero;

    public Butaca(String numeroButaca) {
        this.numeroButaca = numeroButaca;
        this.ocupada = false;
    }

    public void ocupar(String nombrePasajero) {
        if (this.ocupada) {
            throw new IllegalStateException("La butaca " + numeroButaca + " ya está ocupada.");
        }
        this.ocupada = true;
        this.nombrePasajero = nombrePasajero;
    }

    public boolean esSuNumero(String numeroButaca) {
        return this.numeroButaca.equals(numeroButaca);
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public String numero() {
        return numeroButaca;
    }
}
