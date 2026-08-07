package dao;

import model.Lieu;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class LieuDAO {

    public void ajouter(Lieu l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(l);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void modifier(Lieu l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(l);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void supprimer(Integer codelieu) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Lieu l = session.get(Lieu.class, codelieu);
            if (l != null) {
                session.remove(l);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public Lieu trouverParCode(Integer codelieu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Lieu.class, codelieu);
        }
    }

    public List<Lieu> listerTous() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Lieu> query = session.createQuery("FROM Lieu", Lieu.class);
            return query.getResultList();
        }
    }
}