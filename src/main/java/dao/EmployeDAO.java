package dao;

import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class EmployeDAO {

    public void ajouter(Employe e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(e);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void modifier(Employe e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(e);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void supprimer(Integer codeemp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employe e = session.get(Employe.class, codeemp);
            if (e != null) {
                session.remove(e);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public Employe trouverParCode(Integer codeemp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, codeemp);
        }
    }

    public List<Employe> trouverParNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employe> query = session.createQuery(
                    "FROM Employe WHERE nom LIKE :nom", Employe.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        }
    }

    public List<Employe> listerTous() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
            return query.getResultList();
        }
    }
}