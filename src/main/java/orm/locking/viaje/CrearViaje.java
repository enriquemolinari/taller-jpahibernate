package orm.locking.viaje;

import orm.utils.EmfBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearViaje {
    public static void main(String[] args) {
        var emf = new EmfBuilder()
                .postgreSqlCredentials()
                .addClass(Viaje.class)
                .addClass(Butaca.class)
                .addClass(Pasaje.class)
                .build();
        var empresa = new EmpresaComarca(emf);
        empresa.nuevoViaje(1L,
                LocalDate.of(2025, 12, 20),
                LocalTime.of(10, 0));
    }
}
