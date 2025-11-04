package orm.locking.viaje;

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

    public List<ButacaView> listarButacasDeViaje(Long viajeId) {
        return emf.callInTransaction((em -> {
            var viaje = em.find(Viaje.class, viajeId);
            return viaje.butacas().stream().map(b -> new ButacaView(b.numero(), b.estaOcupada())).toList();
        }));
    }

    public void comprarButacaEnViaje(Long viajeId, String numeroButaca, String nombrePasajero) {
        emf.callInTransaction((em -> {
            var viaje = em.find(Viaje.class, viajeId);
            var pasaje = viaje.comprarButaca(numeroButaca, nombrePasajero);
            em.persist(pasaje);
            return pasaje;
        }));
    }

    record ButacaView(String numero, boolean ocupada) {
    }
}
