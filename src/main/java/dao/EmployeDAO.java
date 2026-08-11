package dao;

import exception.AccesDonneesException;
import exception.AvertissementDoublonException;
import exception.DoublonException;
import exception.SuppressionImpossibleException;
import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class EmployeDAO {

    public void ajouter(Employe e, boolean forcer) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            if (!forcer) {
                Query<Long> verif = session.createQuery(
                        "SELECT COUNT(emp) FROM Employe emp WHERE emp.nom = :nom AND emp.prenom = :prenom", Long.class);
                verif.setParameter("nom", e.getNom());
                verif.setParameter("prenom", e.getPrenom());
                if (verif.getSingleResult() > 0) {
                    throw new AvertissementDoublonException(
                            "Un employé nommé " + e.getNom() + " " + e.getPrenom() + " existe déjà. Voulez-vous vraiment continuer ?");
                }
            }

            tx = session.beginTransaction();
            session.persist(e);
            tx.commit();
        } catch (AvertissementDoublonException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw new AccesDonneesException("Impossible d'ajouter l'employé.", ex);
        }
    }

    public void ajouter(Employe e) {
        ajouter(e, false);
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

    public List<Employe> rechercher(String critere) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Employe WHERE nom LIKE :critere OR prenom LIKE :critere";
            Integer codeRecherche = null;

            try {
                codeRecherche = Integer.parseInt(critere.trim());
                hql += " OR codeemp = :code";
            } catch (NumberFormatException ignore) {
                // le critère n'est pas un nombre, on ignore la recherche par code
            }

            Query<Employe> query = session.createQuery(hql, Employe.class);
            query.setParameter("critere", "%" + critere + "%");
            if (codeRecherche != null) {
                query.setParameter("code", codeRecherche);
            }

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

    public List<Object[]> compterParPoste() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT COALESCE(e.poste, 'Non renseigné'), COUNT(e) FROM Employe e GROUP BY e.poste",
                    Object[].class);
            return query.getResultList();
        } catch (RuntimeException ex) {
            throw new AccesDonneesException("Impossible de contacter la base de données.", ex);
        }
    }
}