package sh.platform.config.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.Hibernate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PostgresqlSQLTest {

    private GenericContainer postgres =
            new GenericContainer("postgres:latest")
                    .withExposedPorts(5432)
                    .withEnv("POSTGRES_PASSWORD", "password")
                    .withEnv("POSTGRES_DB", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    public void shouldRunIntegrationTest() {
        postgres.start();
        System.setProperty("database.host", postgres.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(postgres.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "postgres");
        System.setProperty("database.password", "password");

        Config config = new Config();
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Address.class);
        final Hibernate credential = config.getCredential("database", Hibernate::new);
        final SessionFactory sessionFactory = credential.getPostgreSQL(configuration);
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            Address address = new Address();
            address.setId(ThreadLocalRandom.current().nextInt());
            address.setCity("Dhaka MySQL");
            address.setCountry("Bangladesh");
            address.setPostcode("1000");
            address.setStreet("Poribagh");
            session.save(address);
            transaction.commit();

            final List<Address> addresses = session.createQuery("from Address", Address.class).list();
            assertFalse(addresses.isEmpty());
        } catch (Exception exp) {
            Assertions.assertTrue(false, "An error when execute MySQL");
        } finally {
            System.clearProperty("database.host");
            System.clearProperty("database.port");
            System.clearProperty("database.path");
            System.clearProperty("database.username");
            System.clearProperty("database.password");
            postgres.close();
        }

    }
}
