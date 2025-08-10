package orm.mappings.manytoone;

import orm.utils.EmfBuilder;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            em.persist(antonio);
            var libro = new Libro("abcd-1234", "La casa y el bosque", antonio);
            em.persist(libro);
        });
    }

}
