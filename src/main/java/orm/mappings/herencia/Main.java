package orm.mappings.herencia;

import orm.utils.EmfBuilder;

public class Main {
    public static void main(String[] args) {
        var emf = new EmfBuilder()
                .addClass(Autor.class)
                .addClass(FichaFisica.class)
                .addClass(LibroImpreso.class)
                .addClass(LibroElectronico.class)
                .addClass(Libro.class)
                .build();

        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            var jose = new Autor("Jose", "Malvino");
            em.persist(antonio);
            em.persist(jose);
            var libro = new LibroImpreso("abcd-1234",
                    "La casa y el bosque",
                    new FichaFisica("7 x 0.85 x 10 pulgadas", "310g"));
            var libroe = new LibroElectronico("abcd-1235",
                    "otro libro ahora electronico", "PDF", "12kb");
            libro.agregarAutor(antonio);
            libro.agregarAutor(jose);
            libroe.agregarAutor(jose);
            em.persist(libro);
            em.persist(libroe);
        });

        emf.runInTransaction((em) -> {
            //¿Cómo sabe que instancia de Libro darme?
            //Observar la query realizada con SINGLE_TABLE vs JOINED vs TABLE per Concrete Class
            var libros = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
            libros.stream().forEach(libro -> {
                System.out.println(libro);
            });
        });
    }
}
