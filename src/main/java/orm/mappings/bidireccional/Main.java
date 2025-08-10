package orm.mappings.bidireccional;

import orm.utils.EmfBuilder;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder()
                .addClass(Autor.class)
                .addClass(Autoria.class)
                .addClass(Libro.class)
                .build();

        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            var jose = new Autor("Jose", "Malvino");
            em.persist(antonio);
            em.persist(jose);

            var libro = new Libro("abcd-1234", "La casa y el bosque");
            em.persist(libro);

            var autoriaAntonio = new Autoria(50, libro, antonio);
            em.persist(autoriaAntonio);
            libro.agregarAutoria(autoriaAntonio);
            var autoriaJose = new Autoria(50, libro, jose);
            em.persist(autoriaJose);
            libro.agregarAutoria(autoriaJose);
        });
    }

}
