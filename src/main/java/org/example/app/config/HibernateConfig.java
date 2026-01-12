package org.example.app.config;

import org.example.app.entity.User;
import org.example.app.view.AppView;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration
                        .buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                new AppView().getOutput(e.getMessage());
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties props = new Properties();
        // Формуємо потік (Stream) даних з конфігураційного файлу
        try {
            props.load(HibernateConfig.class.getResourceAsStream("/app.example.properties"));
        } catch (IOException e) {
            // Виведення повідомлення про помилки роботи
            // з БД або конфігураційним файлом
            new AppView().getOutput(e.getMessage());
        }
        props.put(Environment.JAKARTA_JDBC_DRIVER, props.getProperty("dbDriver"));
        props.put(Environment.JAKARTA_JDBC_URL, props.getProperty("dbUrl"));
        props.put(Environment.JAKARTA_JDBC_USER, props.getProperty("username"));
        props.put(Environment.JAKARTA_JDBC_PASSWORD, props.getProperty("password"));
        props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperties(props);
        return configuration;
    }
}
