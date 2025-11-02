package orm.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;

public class EmfBuilder {
    public static final String DB_USER = "app";
    public static final String DB_PWD = "app";
    public static final String IN_MEMORY_DB_URL = "jdbc:derby:memory:ejemplo;create=true";
    public static final String CLIENT_DB_URL = "jdbc:derby://localhost:1527/ejemplo;create=true";
    public static final String CLIENT_PG_DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String PG_DB_USER = "postgres";
    public static final String PG_DB_PWD = "mysecretpassword";
    private EntityManagerFactory emf;
    private PersistenceConfiguration config;

    public EmfBuilder() {
        config = new PersistenceConfiguration("ejemplo")
                .property(PersistenceConfiguration.JDBC_USER, DB_USER)
                .property(PersistenceConfiguration.JDBC_PASSWORD, DB_PWD)
                .property(JdbcSettings.SHOW_SQL, true)
                .property(JdbcSettings.FORMAT_SQL, true)
                .property(JdbcSettings.HIGHLIGHT_SQL, true)
                .property(PersistenceConfiguration.JDBC_URL,
                        CLIENT_DB_URL)
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                        Action.CREATE_DROP);
    }

    public EmfBuilder postgreSqlCredentials() {
        config.property(PersistenceConfiguration.JDBC_URL,
                CLIENT_PG_DB_URL);
        config.property(PersistenceConfiguration.JDBC_USER,
                PG_DB_USER);
        config.property(PersistenceConfiguration.JDBC_PASSWORD,
                PG_DB_PWD);
        return this;
    }

    public EmfBuilder addClass(Class<?> clazz) {
        config.managedClass(clazz);
        return this;
    }

    public EmfBuilder memory() {
        config.property(PersistenceConfiguration.JDBC_URL,
                IN_MEMORY_DB_URL);
        return this;
    }

    public EmfBuilder withOutChangeSchema() {
        config.property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                Action.NONE);
        return this;
    }

    public EntityManagerFactory build() {
        return config.createEntityManagerFactory();
    }
}

