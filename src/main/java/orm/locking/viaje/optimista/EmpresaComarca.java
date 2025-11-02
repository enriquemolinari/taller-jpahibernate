package orm.locking.viaje.optimista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

//Clase que representa la empresa de transporte.
//asumamos que solo tiene 1 colectivo
public class EmpresaComarca {
    private EntityManagerFactory emf;

    public EmpresaComarca(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void nuevoViaje(Long idViaje, LocalDate fechaSalida, LocalTime horaSalida) {
        emf.runInTransaction((em -> {
            var viaje = new Viaje(idViaje, fechaSalida, horaSalida, List.of(
                    new Butaca("1"),
                    new Butaca("2"),
                    new Butaca("3"),
                    new Butaca("4"),
                    new Butaca("5"),
                    new Butaca("6")
            ));
            em.persist(viaje);
        }));
    }

    public void comprarButacaEnViaje(Long viajeId, String numeroButaca, String nombrePasajero) {
        executeAndCheckOptimisticLockIfFail(em -> {
            var viaje = em.find(Viaje.class, viajeId);
            var pasaje = viaje.comprarButaca(numeroButaca, nombrePasajero);
            em.persist(pasaje);
            return pasaje;
        }, numeroButaca);
    }

    private void executeAndCheckOptimisticLockIfFail(Function<EntityManager, Pasaje> comprarButaca,
                                                     String numeroButaca) {
        try {
            emf.callInTransaction(comprarButaca);
        } catch (Exception e) {
            if (e.getCause() instanceof OptimisticLockException) {
                throw new RuntimeException("No se pudo comprar la butaca " + numeroButaca +
                        " porque otro usuario la consiguió justo antes. Por favor, seleccione otra.");
            }
            throw new RuntimeException(e);
        }
    }
}
