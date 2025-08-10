package orm.locking;

import jakarta.persistence.*;

public class MergeOptimisticThread1 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("jpa-pgsql");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Este método es el servicio del tercer paso del read-modify-write
            // siempre llega el id, la version y lo nuevo que fue modificado.
//            BlogPost p1 = new BlogPost("titulo post", "texto post");
//            em.persist(p1);

            //Opcion 1: creo instancia nueva con la version que viene de la UI
            // luego merge
//            BlogPost p1 = new BlogPost(1, "primer post cambiado - thread 1",
//                    "cambio", 0);
//            em.merge(p1);

            //Opcion 2: comparo versiones o con otro atributo para ver si cambio la instancia
            //que quiero persistir
            // comparando manualmente
            int versionReadInPreviousTx = 0;

            BlogPost p1 = em.find(BlogPost.class, 1L);

            if (!p1.sameVersion(versionReadInPreviousTx)) {
                throw new OptimisticLockException();
            }
            p1.nuevoTitulo("cambio 200");

            //Recordar: Si hubiera transacciones concurrentes, siempre al leer primero
            //y luego modificar el mecanismo de JPA Optimista lanzara exception
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
