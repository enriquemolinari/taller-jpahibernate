package orm.locking.viaje;

import orm.utils.EmfBuilder;

import java.util.Scanner;

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

        //ATENCIÓN !!!!

        var nombre = preguntarQuienEstaComprando();
        // Asumamos que el usuario ve las butacas libres y ocupadas
        empresa.listarButacasDeViaje(1L)
                .stream()
                .forEach(b -> System.out.println(b));
        //Luego de pensar, selecciona la número 1 y realiza la compra.
        empresa.comprarButacaEnViaje(1L, "1", nombre);
    }

    private static String preguntarQuienEstaComprando() {
        var scanner = new Scanner(System.in);
        System.out.print("¿Quién sos?: ");
        var nombre = scanner.nextLine();
        return nombre;
    }
}
