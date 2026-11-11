package orm.locking.viaje.optimista;

import orm.utils.EmfBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearViaje {
    public static void main(String[] args) {
        var emf = new EmfBuilder()
                .postgreSqlCredentials()
                .withSchema(EmpresaComarca.SCHEMA)
                .addClass(Viaje.class)
                .addClass(Butaca.class)
                .addClass(Pasaje.class)
                .build();
        var empresa = new EmpresaComarca(emf);
        empresa.nuevoViaje(1L,
                LocalDate.now().plusMonths(1),
                LocalTime.of(10, 0));
    }
}
