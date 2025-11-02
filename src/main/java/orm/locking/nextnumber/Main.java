package orm.locking.nextnumber;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import orm.utils.EmfBuilder;

import java.time.Year;

public class Main {

    public static void main(String[] args) {

        // 1. Con la BD en read committed (No lo previene ni informa)
        // 2. con la BD en repeatable read (Optimistic Lock)
        // 2.1 Informo el error y lo hago intentar al usuario nuevamente
        // 2.1 Con retries si quiero hacerlo sin que el usuario lo note
        // 3. Con la BD en read committed y lockeo pesismista (select for
        // update)
        // 4. Con la BD en read committed y lockeo optimista (version)

//        EntityManagerFactory emf = Persistence
//                .createEntityManagerFactory("jpa-pgsql");
        var emfBuilder = new EmfBuilder().postgreSqlCredentials().addClass(NextNumber.class);
        var emf = emfBuilder.build();

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            int anioActual = Year.now().getValue();

            TypedQuery<NextNumber> query = em.createQuery(
                    "from NextNumber where anio = :anioActual",
                    NextNumber.class);
            query.setParameter("anioActual", anioActual);

            // select for update
            // https://www.postgresql.org/docs/current/explicit-locking.html
            // select for key update is a bit weaker than select for update
            //query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

            NextNumber l = query.getSingleResult();

            System.out.println(l.recuperarSiguiente());

            System.out.println(l.actual());
            System.out.println(l.anio());

            tx.commit();
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
