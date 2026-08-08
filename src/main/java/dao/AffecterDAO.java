package dao;

import exception.AccesDonneesException;
import model.Affecter;
import model.AffecterId;
import model.Employe;
import model.Lieu;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class AffecterDAO {

    public void ajouter(Integer codeemp, Integer codelieu, LocalDate date) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Employe employe = session.get(Employe.class, codeemp);
            Lieu lieu = session.get(Lieu.class, codelieu);

            if (employe == null || lieu == null) {
                throw new IllegalArgumentException("Employé ou lieu introuvable.");
            }

            Affecter affecter = new Affecter(employe, lieu, date);
            session.persist(affecter);

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible d'ajouter l'affectation.", ex);
        }
    }

    public void supprimer(Integer codeemp, Integer codelieu, LocalDate date) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            AffecterId id = new AffecterId(codeemp, codelieu, date);
            Affecter a = session.get(Affecter.class, id);
            if (a != null) {
                session.remove(a);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible de supprimer l'affectation.", ex);
        }
    }

    public Affecter trouverParId(Integer codeemp, Integer codelieu, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            AffecterId id = new AffecterId(codeemp, codelieu, date);
            return session.get(Affecter.class, id);
        }
    }

    public List<Affecter> listerTous() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Affecter> query = session.createQuery(
                    "SELECT a FROM Affecter a JOIN FETCH a.employe JOIN FETCH a.lieu", Affecter.class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Affecter> listerParEmploye(Integer codeemp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Affecter> query = session.createQuery(
                    "SELECT a FROM Affecter a JOIN FETCH a.employe JOIN FETCH a.lieu WHERE a.employe.codeemp = :codeemp",
                    Affecter.class);
            query.setParameter("codeemp", codeemp);
            return query.getResultList();
        }
    }
}