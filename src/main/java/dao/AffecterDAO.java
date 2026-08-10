package dao;

import exception.AccesDonneesException;
import exception.AvertissementDoublonException;
import exception.DoublonException;
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

            Query<Long> verifMemeLieu = session.createQuery(
                    "SELECT COUNT(a) FROM Affecter a WHERE a.employe.codeemp = :codeemp AND a.lieu.codelieu = :codelieu",
                    Long.class);
            verifMemeLieu.setParameter("codeemp", codeemp);
            verifMemeLieu.setParameter("codelieu", codelieu);
            if (verifMemeLieu.getSingleResult() > 0) {
                throw new DoublonException("Cet employé est déjà affecté à ce lieu.");
            }

            Query<Long> verifMemeDate = session.createQuery(
                    "SELECT COUNT(a) FROM Affecter a WHERE a.employe.codeemp = :codeemp AND a.id.date = :date",
                    Long.class);
            verifMemeDate.setParameter("codeemp", codeemp);
            verifMemeDate.setParameter("date", date);
            if (verifMemeDate.getSingleResult() > 0) {
                throw new DoublonException("Cet employé est déjà affecté à un autre lieu à cette date.");
            }

            if (date.isBefore(LocalDate.now().minusYears(1)) || date.isAfter(LocalDate.now().plusYears(2))) {
                throw new DoublonException("La date d'affectation (" + date + ") semble incorrecte : trop éloignée dans le temps.");
            }

            tx = session.beginTransaction();

            Employe employe = session.get(Employe.class, codeemp);
            Lieu lieu = session.get(Lieu.class, codelieu);
            if (employe == null || lieu == null) {
                throw new IllegalArgumentException("Employé ou lieu introuvable.");
            }

            Affecter affecter = new Affecter(employe, lieu, date);
            session.persist(affecter);
            tx.commit();

        } catch (DoublonException ex) {
            throw ex;
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

    public void modifier(Integer ancienCodeemp, Integer ancienCodelieu, LocalDate ancienneDate,
                         Integer nouveauCodeemp, Integer nouveauCodelieu, LocalDate nouvelleDate) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Long> verifMemeLieu = session.createQuery(
                    "SELECT COUNT(a) FROM Affecter a WHERE a.employe.codeemp = :codeemp AND a.lieu.codelieu = :codelieu " +
                            "AND NOT (a.employe.codeemp = :ancienCodeemp AND a.lieu.codelieu = :ancienCodelieu AND a.id.date = :ancienneDate)",
                    Long.class);
            verifMemeLieu.setParameter("codeemp", nouveauCodeemp);
            verifMemeLieu.setParameter("codelieu", nouveauCodelieu);
            verifMemeLieu.setParameter("ancienCodeemp", ancienCodeemp);
            verifMemeLieu.setParameter("ancienCodelieu", ancienCodelieu);
            verifMemeLieu.setParameter("ancienneDate", ancienneDate);
            if (verifMemeLieu.getSingleResult() > 0) {
                throw new DoublonException("Cet employé est déjà affecté à ce lieu.");
            }

            Query<Long> verifMemeDate = session.createQuery(
                    "SELECT COUNT(a) FROM Affecter a WHERE a.employe.codeemp = :codeemp AND a.id.date = :date " +
                            "AND NOT (a.employe.codeemp = :ancienCodeemp AND a.lieu.codelieu = :ancienCodelieu AND a.id.date = :ancienneDate)",
                    Long.class);
            verifMemeDate.setParameter("codeemp", nouveauCodeemp);
            verifMemeDate.setParameter("date", nouvelleDate);
            verifMemeDate.setParameter("ancienCodeemp", ancienCodeemp);
            verifMemeDate.setParameter("ancienCodelieu", ancienCodelieu);
            verifMemeDate.setParameter("ancienneDate", ancienneDate);
            if (verifMemeDate.getSingleResult() > 0) {
                throw new DoublonException("Cet employé est déjà affecté à un autre lieu à cette date.");
            }

            if (nouvelleDate.isBefore(LocalDate.now().minusYears(1)) || nouvelleDate.isAfter(LocalDate.now().plusYears(2))) {
                throw new DoublonException("La date d'affectation (" + nouvelleDate + ") semble incorrecte : trop éloignée dans le temps.");
            }

            tx = session.beginTransaction();

            AffecterId ancienId = new AffecterId(ancienCodeemp, ancienCodelieu, ancienneDate);
            Affecter ancien = session.get(Affecter.class, ancienId);
            if (ancien != null) {
                session.remove(ancien);
                session.flush();
            }

            Employe employe = session.get(Employe.class, nouveauCodeemp);
            Lieu lieu = session.get(Lieu.class, nouveauCodelieu);
            if (employe == null || lieu == null) {
                throw new IllegalArgumentException("Employé ou lieu introuvable.");
            }

            Affecter nouvelle = new Affecter(employe, lieu, nouvelleDate);
            session.persist(nouvelle);
            tx.commit();

        } catch (DoublonException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible de modifier l'affectation.", ex);
        }
    }

    public List<Affecter> rechercher(String critere) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Affecter> query = session.createQuery(
                    "SELECT a FROM Affecter a JOIN FETCH a.employe JOIN FETCH a.lieu " +
                            "WHERE a.employe.nom LIKE :c OR a.employe.prenom LIKE :c OR a.lieu.designation LIKE :c",
                    Affecter.class);
            query.setParameter("c", "%" + critere + "%");
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Affecter> listerTous(String tri) {
        String hql = "SELECT a FROM Affecter a JOIN FETCH a.employe JOIN FETCH a.lieu";
        if ("desc".equals(tri)) {
            hql += " ORDER BY a.id.date DESC";
        } else if ("asc".equals(tri)) {
            hql += " ORDER BY a.id.date ASC";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Affecter> query = session.createQuery(hql, Affecter.class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }

    public List<Affecter> listerTous() {
        return listerTous(null);
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

    public List<Object[]> compterParLieu() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT l.designation, COUNT(a) FROM Affecter a JOIN a.lieu l GROUP BY l.designation",
                    Object[].class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }
}