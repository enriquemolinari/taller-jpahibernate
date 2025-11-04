package orm.locking.viaje.optimista;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Viaje {
    @Id
    private Long id;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "viaje_id")
    private List<Butaca> butacas;

    public Viaje(Long id, LocalDate fechaSalida, LocalTime horaSalida, List<Butaca> butacas) {
        this.id = id;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.butacas = butacas;
    }

    public Pasaje comprarButaca(String numeroButaca, String nombrePasajero) {
        Butaca butaca = butacas.stream()
                .filter(b -> b.esSuNumero(numeroButaca))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Butaca no encontrada: " + numeroButaca));
        
        butaca.ocupar(nombrePasajero);

        return new Pasaje(nombrePasajero, numeroButaca, this);
    }

    public String fechaHoraSalida() {
        return fechaSalida.toString() + " " + horaSalida.toString();
    }
}
