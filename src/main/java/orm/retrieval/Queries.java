package orm.retrieval;

import orm.utils.EmfBuilder;

import java.time.LocalDateTime;

public class Queries {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        Main.cargoDatos(emf);

        //select no necesario
        //usamos los nombres de las clases
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from Libro l", Libro.class);
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });

        //usamos los nombres de los atributos de las clases
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from Libro l where l.fechaPublicacion > :fecha", Libro.class);
            query.setParameter("fecha", LocalDateTime.now().minusYears(1));
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });

        //Reconoce collections
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from Libro l where l.autores is not empty", Libro.class);
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });

        //Reconoce collections
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from Libro l where size(l.autores) > 1", Libro.class);
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });

        //usamos join
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from Libro l join l.autores", Libro.class);
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });

        // es polimorfico
        emf.runInTransaction((em) -> {
            var query = em.createQuery("from java.lang.Object o", Object.class);
            var objects = query.getResultList();
            objects.forEach(System.out::println);
        });
    }
}
