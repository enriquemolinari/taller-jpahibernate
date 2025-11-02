package orm.locking.viaje;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Pasaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombrePasajero;
    private String numeroButaca;
    @ManyToOne
    private Viaje viaje;

    public Pasaje(String nombrePasajero, String numeroButaca, Viaje viaje) {
        this.nombrePasajero = nombrePasajero;
        this.numeroButaca = numeroButaca;
        this.viaje = viaje;
    }

    public String ticket() {
        return "Pasaje para " + nombrePasajero + " en la butaca " + numeroButaca +
                " para el viaje el " + viaje.fechaHoraSalida();
    }
}
