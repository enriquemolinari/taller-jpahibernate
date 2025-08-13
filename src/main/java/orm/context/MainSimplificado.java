package orm.context;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;

public class MainSimplificado {
    public static final String IN_MEMORY_DB_URL = "jdbc:derby:memory:ejemplo;create=true";
    public static final String CLIENT_DB_URL = "jdbc:derby://localhost:1527/ejemplo;create=true";
    private static final String DB_USER = "app";
    private static final String DB_PWD = "app";

    public static void main(String[] args) {
        var emf = createEmf();

        emf.runInTransaction((em) -> {
            var antonio = new Autor("Antonio", "Zarate");
            //antonio es transient ahora
            em.persist(antonio);
            //antonio es persistent ahora
            var libro = new Libro("abcd-1234", "La casa y el bosque");
            em.persist(libro);
            //libro es persistent ahora
        });
    }

    private static EntityManagerFactory createEmf() {
        PersistenceConfiguration config = new PersistenceConfiguration("ejemplo")
                //agrego las clases persistentes
                .managedClass(Libro.class)
                .managedClass(Autor.class)
                // connexion a la bd
                .property(PersistenceConfiguration.JDBC_URL, CLIENT_DB_URL)
                .property(PersistenceConfiguration.JDBC_USER, DB_USER)
                .property(PersistenceConfiguration.JDBC_PASSWORD, DB_PWD)
                // muestra la query en la consola
                .property(JdbcSettings.SHOW_SQL, true)
                // formatea la query
                .property(JdbcSettings.FORMAT_SQL, true)
                // hilightea la sintaxis de SQL
                .property(JdbcSettings.HIGHLIGHT_SQL, true)
                // Action.NONE
                // Action.CREATE_DROP
                // Action.UPDATE
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                        Action.CREATE_DROP);
        return config.createEntityManagerFactory();
    }
}
