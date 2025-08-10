package orm.masejemplos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {

    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence
                // .createEntityManagerFactory("jpa-objectdb");
                .createEntityManagerFactory("jpa-derby-client");
        // .createEntityManagerFactory("jpa-derby-embedded");
        // .createEntityManagerFactory("jpa-pgsql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Persona p = new Persona(1L, "E", "SM 123",
                    LocalDate.now().minusYears(40), new Dni("26548987"));

            // Persona e = em.find(Persona.class, 1L);
            // e.addTelefono(new Telefono("2323"));

            Telefono t = new Telefono("1111");
            p.addTelefono(t);

            em.merge(p);
            // em.persist(t);
            // em.persist(p);

            // objectdb soporta entidades sin PK
            // EntidadSinPK e = new EntidadSinPK();
            // e.setNombre("una entidad sin pk");
            // em.persist(e);

            // Persona p = em.getReference(Persona.class, 2L);
            // em.remove(p);

            // Telefono t2 = new Telefono("4444");
            // //em.persist(t2);
            // p.addTelefono(t2);

            // Telefono t = em.find(Telefono.class, 0);
            // em.remove(t);

            // Persona p = new Persona();
            // p.setNombre("Pablo");
            // p.fechaNacimiento(LocalDate.of(1977, 06, 25));
            // em.persist(p);

            // Persona p = new Persona();
            // p.setNombre("José");
            // p.fechaNacimiento(LocalDate.of(1980, 04, 06));
            // em.persist(p);

            // TypedQuery<Persona> q = em.createQuery("select p from Persona p",
            // Persona.class);

            // TypedQuery<Persona> q = em.createQuery(
            // "select p from Persona p where p.fechaNac.fecha > :fecha1 and
            // p.fechaNac.fecha < :fecha2",
            // Persona.class);
            // q.setParameter("fecha1", Date.valueOf(LocalDate.of(1950, 01,
            // 01)));
            // q.setParameter("fecha2", Date.valueOf(LocalDate.of(1979, 01,
            // 01)));
            // List<Persona> personas = q.getResultList();
            // for (Persona persona : personas) {
            // System.out.println(persona.getNombre());
            // }

            // Persona p = em.find(Persona.class, 7l);
            // p.addTelefono(new Telefono("8888"));

            // TypedQuery<Persona> q = em.createQuery(
            // "select p from Persona p where p.telefonos is not empty",
            // Persona.class);

            // TypedQuery<Persona> q = em.createQuery(
            // "select p from Persona p where size(p.telefonos) > 1",
            // Persona.class);

            // join with collection
            // TypedQuery<Persona> q = em.createQuery(
            // "select p from Persona p join p.telefonos t where t.nro =
            // :telefono",
            // Persona.class);
            //
            // q.setParameter("telefono", "8888");
            //
            // List<Persona> l = q.getResultList();
            // System.out.println(l.size());
            // for (Persona persona : l) {
            // System.out.println(persona.getNombre());
            // }

            // Empleado e = new Empleado("Pablito", 1000);
            // em.persist(e);

            tx.commit();
            // em.clear();
            //
            // Persona e = em.find(Persona.class, 1L);
            // System.out.println(e.getNombre());
            // e.printTelefonos();

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
            if (emf != null)
                emf.close();
        }
    }
}
