package orm.retrieval;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;
import orm.utils.EmfBuilder;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        cargoDatos(emf);

        //Lazy (Proxy) vs Early
        //find()
        emf.runInTransaction((em) -> {
            var libro = em.find(Libro.class, "abcd-1234");
            //No trae los autores. El debugger puede hacer que intellij haga que se dispare la query para mostrar el size de la collection.
            System.out.println(libro.titulo());
            System.out.println(libro.autores());
        });

        //Como agrego un Autor a un Libro ?
        //getReferece opmtimiza estas acciones
        //NO realiza la SQL query para traer Libro (ojo el Debuguer del IDE puede ocasionar el fetch de la entidad)
        emf.runInTransaction((em) -> {
            //var libro = em.find(Libro.class, "abcd-1234");
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
        //System.out.println(libro.autores());

        //Detached: Soluciones
        var libro2 = emf.callInTransaction((em) -> {
            var l = em.find(Libro.class, "abcd-1234");
            // libro es persistent ahora

            //opciones para inicializar atributos lazy
           // Hibernate.initialize(l.autores()); // no me gusta demasiado
            //2. Cambiar Fetch en el mapeo. Pero queda para siempre asi, no importa el caso de uso.
            //3. Cambiar el find por una Query join fetch.
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
            var libro = new Libro("abcd-1234", "La casa y el bosque", LocalDateTime.of(2024, 02, 8, 10, 30));
            var otroLibro = new Libro("zwqa-5678", "El mono y la jirafa", LocalDateTime.of(2025, 02, 8, 10, 30));
            libro.agregarAutor(antonio);
            libro.agregarAutor(jose);
            em.persist(libro);
            em.persist(otroLibro);
        });
    }

}
