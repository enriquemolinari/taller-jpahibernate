package orm.locking.viaje;

import orm.utils.EmfBuilder;

public class ComprarViaje {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "Se debe pasar el nombre del pasajero como argumento");
        }
        var emf = new EmfBuilder()
                .postgreSqlCredentials()
                .withOutChangeSchema()
                .addClass(Viaje.class)
                .addClass(Butaca.class)
                .addClass(Pasaje.class)
                .build();

        var empresa = new EmpresaComarca(emf);
        empresa.comprarButacaEnViaje(1L, "1", args[0]);
    }
}
