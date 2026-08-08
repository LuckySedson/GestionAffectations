package dao;

import exception.AccesDonneesException;
import exception.SuppressionImpossibleException;
import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
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
            throw new AccesDonneesException("Impossible d'ajouter l'employé.", ex);
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
            throw new AccesDonneesException("Impossible de modifier l'employé.", ex);
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
        } catch (ConstraintViolationException ex) {
            if (tx != null) tx.rollback();
            throw new SuppressionImpossibleException(
                    "Cet employé est affecté à un ou plusieurs lieux et ne peut pas être supprimé.");
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible de supprimer l'employé.", ex);
        }
    }

    public Employe trouverParCode(Integer codeemp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, codeemp);
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Employe> trouverParNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employe> query = session.createQuery("FROM Employe WHERE nom LIKE :nom", Employe.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Employe> listerTous() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }
}