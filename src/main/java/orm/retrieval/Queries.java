package orm.retrieval;

import orm.utils.EmfBuilder;

public class Queries {
    public static void main(String[] args) {
        var emf = new EmfBuilder().addClass(Autor.class)
                .addClass(Libro.class).build();

        Main.cargoDatos(emf);

        emf.runInTransaction((em) -> {

            var query = em.createQuery("select o from java.lang.Object o", Object.class);
            var objects = query.getResultList();
        });

    }
}
