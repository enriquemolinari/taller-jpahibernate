package orm.locking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class MergeOptimisticThread2 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("jpa-derby-client");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // usando merge
            BlogPost p1 = new BlogPost(1, "primer post cambiado - thread 2",
                    "cambio", 1);
            em.merge(p1);

            // comparando manualmente
//			int versionReadInPreviousTx = 28;
//			BlogPost p1 = em.find(BlogPost.class, 1L);
//
//			if (!p1.sameVersion(versionReadInPreviousTx)) {
//				throw new OptimisticLockException();
//			}
//			p1.nuevoTitulo("cambio 201");

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
