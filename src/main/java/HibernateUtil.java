package org.example.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory ;   //= buildSessionFactory();

    static {
        try {
            String configFile = System.getProperty("test.config") != null
                    ? "hibernate-test.cfg.xml"
                    : "hibernate.cfg.xml";

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure(configFile)
                    .applySettings(System.getProperties())
                    .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .getMetadataBuilder()
                    .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}