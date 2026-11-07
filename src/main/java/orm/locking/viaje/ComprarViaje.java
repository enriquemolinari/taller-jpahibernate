package orm.locking.viaje;

import orm.utils.EmfBuilder;

public class ComprarViaje {
    public static void main(String[] args) {
        var emf = new EmfBuilder()
                .postgreSqlCredentials()
                .withSchema(EmpresaComarca.SCHEMA)
                .withOutChangeSchema()
                .addClass(Viaje.class)
                .addClass(Butaca.class)
                .addClass(Pasaje.class)
                .build();

        var empresa = new EmpresaComarca(emf);

        empresa.listarButacasDeViaje(1L)
                .stream()
                .forEach(b -> System.out.println(b));

        empresa.comprarButacaEnViaje(1L, "1", "Enrique");
    }
}
