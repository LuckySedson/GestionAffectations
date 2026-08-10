package dao;

import exception.AccesDonneesException;
import exception.DoublonException;
import exception.SuppressionImpossibleException;
import model.Lieu;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class LieuDAO {

    public void ajouter(Lieu l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> verif = session.createQuery(
                    "SELECT COUNT(lieu) FROM Lieu lieu WHERE lieu.designation = :d AND lieu.province = :p", Long.class);
            verif.setParameter("d", l.getDesignation());
            verif.setParameter("p", l.getProvince());
            if (verif.getSingleResult() > 0) {
                throw new DoublonException(
                        "Le lieu \"" + l.getDesignation() + "\" existe déjà dans la province \"" + l.getProvince() + "\".");
            }

            tx = session.beginTransaction();
            session.persist(l);
            tx.commit();
        } catch (DoublonException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible d'ajouter le lieu.", ex);
        }
    }

    public void modifier(Lieu l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Long> verif = session.createQuery(
                    "SELECT COUNT(lieu) FROM Lieu lieu WHERE lieu.designation = :d AND lieu.province = :p AND lieu.codelieu != :codelieu",
                    Long.class);
            verif.setParameter("d", l.getDesignation());
            verif.setParameter("p", l.getProvince());
            verif.setParameter("codelieu", l.getCodelieu());
            if (verif.getSingleResult() > 0) {
                throw new DoublonException(
                        "Le lieu \"" + l.getDesignation() + "\" existe déjà dans la province \"" + l.getProvince() + "\".");
            }

            tx = session.beginTransaction();
            session.merge(l);
            tx.commit();
        } catch (DoublonException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible de modifier le lieu.", ex);
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
        } catch (ConstraintViolationException ex) {
            if (tx != null) tx.rollback();
            throw new SuppressionImpossibleException(
                    "Ce lieu est utilisé dans une ou plusieurs affectations et ne peut pas être supprimé.");
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible de supprimer le lieu.", ex);
        }
    }

    public List<Lieu> trouverParCritere(String critere) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Lieu> query = session.createQuery(
                    "FROM Lieu WHERE designation LIKE :c OR province LIKE :c", Lieu.class);
            query.setParameter("c", "%" + critere + "%");
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public Lieu trouverParCode(Integer codelieu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Lieu.class, codelieu);
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Lieu> listerTous() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Lieu> query = session.createQuery("FROM Lieu", Lieu.class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }
}