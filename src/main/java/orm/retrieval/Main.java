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
