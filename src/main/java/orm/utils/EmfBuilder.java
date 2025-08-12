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
//                .property("hibernate.cache.use_second_level_cache", true)
//                .property("hibernate.cache.use_query_cache", true)
//                .property("hibernate.cache.region.factory_class", "jcache")
//                .property("hibernate.javax.cache.uri", "ehcache.xml")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                        Action.CREATE_DROP);
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

