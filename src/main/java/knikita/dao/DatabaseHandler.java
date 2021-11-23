package knikita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public class DatabaseHandler {

    private final SessionFactory factory;
    private static DatabaseHandler databaseHandler;

    public static DatabaseHandler getInstance() {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler();
        }
        return databaseHandler;
    }

    private DatabaseHandler() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void save(Object object) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        }
    }

    public void update(Object object) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }

    public void load(Object object, Serializable id) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.load(object, id);
            session.getTransaction().commit();
        }
    }

    public <T> T get(Class<T> aClass, Serializable id) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            T result = session.get(aClass, id);
            session.getTransaction().commit();
            return result;
        }
    }

}
