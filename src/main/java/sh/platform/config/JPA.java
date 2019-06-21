package sh.platform.config;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.Persistence.createEntityManagerFactory;

public class JPA extends Credential {

    public JPA(Map<String, Object> config) {
        super(config);
    }

    /**
     * Create and return an EntityManagerFactory for the named persistence unit using the given properties.
     *
     * @return {@link EntityManagerFactory}
     */
    public EntityManagerFactory getPostgresql(String persistenceUnitName) {
        Objects.requireNonNull(persistenceUnitName, "persistenceUnitName");

        return getEntityManagerFactory("org.postgresql.Driver", "postgresql", persistenceUnitName);
    }


    /**
     * Create and return an EntityManagerFactory for the named persistence unit using the given properties.
     *
     * @return {@link EntityManagerFactory}
     */
    public EntityManagerFactory getMySQL(String persistenceUnitName) {
        Objects.requireNonNull(persistenceUnitName, "persistenceUnitName");

        return getEntityManagerFactory("com.mysql.jdbc.Driver", "mysql", persistenceUnitName);
    }


    private EntityManagerFactory getEntityManagerFactory(String driver, String provider, String persistenceUnitName) {
        SQLDatabase sqlDatabase = new SQLDatabase(config);
        Map<String, String> configuration = new HashMap<>();

        configuration.put("javax.persistence.jdbc.driver", driver);
        configuration.put("javax.persistence.jdbc.url", sqlDatabase.getJDBCURL(provider));

        configuration.put("javax.persistence.jdbc.password", sqlDatabase.getPassword());
        configuration.put("javax.persistence.jdbc.user", sqlDatabase.getUserName());
        return createEntityManagerFactory(persistenceUnitName, configuration);
    }
}
