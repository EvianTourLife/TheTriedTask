package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.query.NativeQuery;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String sql = "create table if not exists user " +
                    "(ID bigint auto_increment " +
                    "primary key, " +
                    "NAME varchar(50) not null," +
                    "LASTNAME varchar(50) not null," +
                    "AGE tinyint (3) not null);";

            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("drop table if exists user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.remove(user);
            //session.createQuery("delete User where id = :id")
            //  .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        List<User> user = Collections.emptyList();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            user = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //session.createNativeQuery("truncate table user").executeUpdate();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
