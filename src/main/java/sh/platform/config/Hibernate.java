package sh.platform.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * A credential specialization that provides a {@link SessionFactory}
 */
public class Hibernate extends Credential {

    public Hibernate(Map<String, Object> config) {
        super(config);
    }

    /**
     * Create and return an SessionFactory using MYSQL driver
     *
     * @param configuration the hibernate properties configuration
     * @return a {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getMySQL(Configuration configuration) {
        Objects.requireNonNull(configuration, "configuration is required");
        return getSessionFactory(configuration, "com.mysql.jdbc.Driver", "mysql");
    }

    /**
     * Create and return an SessionFactory using MYSQL driver
     *
     * @return a {@link SessionFactory}
     */
    public SessionFactory getMySQL() {
        return getSessionFactory(new Configuration(), "com.mysql.jdbc.Driver", "mysql");
    }

    /**
     * Create and return an SessionFactory using PostgreSQL driver
     *
     * @param configuration the hibernate properties configuration
     * @return {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getPostgreSQL(Configuration configuration) {
        Objects.requireNonNull(configuration, "configuration is required");
        return getSessionFactory(configuration, "org.postgresql.Driver", "postgresql");
    }

    /**
     * Create and return an SessionFactory using PostgreSQL driver
     *
     * @return {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getPostgreSQL() {
        return getSessionFactory(new Configuration(), "org.postgresql.Driver", "postgresql");
    }

    private SessionFactory getSessionFactory(Configuration configuration, String driver, String provider) {
        SQLDatabase sqlDatabase = new SQLDatabase(config);

        Properties properties = configuration.getProperties();
        properties.put(Environment.DRIVER, driver);
        properties.put(Environment.URL, sqlDatabase.getJDBCURL(provider));
        properties.put(Environment.USER, sqlDatabase.getUserName());
        properties.put(Environment.PASS, sqlDatabase.getPassword());
        properties.putIfAbsent(Environment.HBM2DDL_AUTO, "create-drop");
        Properties settings = new Properties();
        settings.putAll(properties);
        configuration.setProperties(settings);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
