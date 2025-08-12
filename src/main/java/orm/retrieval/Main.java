package orm.retrieval;

import jakarta.persistence.EntityManagerFactory;
import orm.utils.EmfBuilder;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        cargoDatos(emf);

        //Lazy vs Early
        //find()
        emf.runInTransaction((em) -> {
            var libro = em.find(Libro.class, "abcd-1234");
            System.out.println(libro.titulo());
            System.out.println(libro.autores());
        });

        //Como agrego un Autor a un Libro ?
        //getReferece opmtimiza estas acciones
        emf.runInTransaction((em) -> {
            //var libro = em.find(Libro.class, "abcd-1234");
            // no inicializa libro, pero verifica que exista el ID
            // A menos que el ID este en cache
            var libro = em.getReference(Libro.class, "abcd-1234");
            var autor = new Autor("Nuevo", "Autor");
            libro.agregarAutor(autor);
        });

        //Detached
        var libro = emf.callInTransaction((em) -> {
            return em.find(Libro.class, "abcd-1234");
            // libro es persistent ahora
        });
        // libro esta en estado detached ahora. Que pasa con esto?
        System.out.println(libro.autores());

        //Detached: Solunciones
        var libro2 = emf.callInTransaction((em) -> {
            var l = em.find(Libro.class, "abcd-1234");
            // libro es persistent ahora

            //opciones para inicializar atributos lazy
            //1. Hibernate.initialize(l.autores()); // no me gusta demasiado
            //2. Cambiar Fetch en el mapeo. Pero queda para siempre asi, no importa el caso de uso.
            //3. Query, segun si lo necesito fetcheo o no.
            //4. toRecord method
            //return l.toRecord();
            return l;
        });
        // libro esta en estado detached ahora. Que pasa con esto?
        System.out.println(libro2.autores());

    }

    static void cargoDatos(EntityManagerFactory emf) {
        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            var jose = new Autor("Jose", "Malvino");
            var libro = new Libro("abcd-1234", "La casa y el bosque");
            libro.agregarAutor(antonio);
            libro.agregarAutor(jose);
            em.persist(libro);
        });
    }

}
