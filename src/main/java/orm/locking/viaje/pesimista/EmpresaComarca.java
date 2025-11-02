package orm.locking.viaje.pesimista;

import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        emf.callInTransaction((em -> {
            var viaje = em.find(Viaje.class, viajeId);
            var butaca = em.createQuery("select b FROM Viaje v join v.butacas b " +
                            "WHERE v.id = :viajeId AND b.numeroButaca = :numero", Butaca.class)
                    .setParameter("viajeId", viajeId)
                    .setParameter("numero", numeroButaca)
                    //genera un "select for update"
                    .setLockMode(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResultOrNull();
            if (butaca == null) {
                throw new IllegalArgumentException("Butaca no encontrada: " + numeroButaca);
            }
            var pasaje = viaje.comprarButaca(butaca, nombrePasajero);
            em.persist(pasaje);
            return pasaje;
        }));
    }
}
