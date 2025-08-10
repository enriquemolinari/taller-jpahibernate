package orm.mappings.onetomany;

import orm.utils.EmfBuilder;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            var jose = new Autor("Jose", "Malvino");
            em.persist(antonio);
            em.persist(jose);
            var libro = new Libro("abcd-1234", "La casa y el bosque");
            libro.agregarAutor(antonio);
            libro.agregarAutor(jose);
            em.persist(libro);
        });
    }

}
