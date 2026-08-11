package servlet;

import dao.EmployeDAO;
import exception.AccesDonneesException;
import exception.AvertissementDoublonException;
import exception.DoublonException;
import exception.SuppressionImpossibleException;
import model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/employe")
public class EmployeServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter("action");
            if (action == null) action = "liste";

            switch (action) {
                case "liste":
                    List<Employe> employes = employeDAO.listerTous();
                    req.setAttribute("employes", employes);
                    req.getRequestDispatcher("/employe/liste.jsp").forward(req, resp);
                    break;

                case "formAjout":
                    req.getRequestDispatcher("/employe/form.jsp").forward(req, resp);
                    break;

                case "formModif":
                    Integer codeempModif = Integer.parseInt(req.getParameter("codeemp"));
                    req.setAttribute("employe", employeDAO.trouverParCode(codeempModif));
                    req.getRequestDispatcher("/employe/form.jsp").forward(req, resp);
                    break;

                case "supprimer":
                    Integer codeempSupp = Integer.parseInt(req.getParameter("codeemp"));
                    Employe employeSupprime = employeDAO.trouverParCode(codeempSupp);
                    employeDAO.supprimer(codeempSupp);
                    req.getSession().setAttribute("dernierEmployeSupprime", employeSupprime);
                    resp.sendRedirect("employe?action=liste&msg=suppr");
                    break;

                case "annulerSuppr":
                    Employe aRestaurer = (Employe) req.getSession().getAttribute("dernierEmployeSupprime");
                    if (aRestaurer != null) {
                        Employe nouveau = new Employe(aRestaurer.getNom(), aRestaurer.getPrenom(), aRestaurer.getPoste());
                        employeDAO.ajouter(nouveau, true); // forcer=true pour éviter un faux avertissement de doublon
                        req.getSession().removeAttribute("dernierEmployeSupprime");
                    }
                    resp.sendRedirect("employe?action=liste&msg=restaure");
                    break;

                case "recherche":
                    String critere = req.getParameter("critere");
                    List<Employe> resultats = employeDAO.rechercher(critere);
                    req.setAttribute("employes", resultats);
                    req.getRequestDispatcher("/employe/liste.jsp").forward(req, resp);
                    break;

                default:
                    resp.sendRedirect("employe?action=liste");
            }
        } catch (SuppressionImpossibleException ex) {
            resp.sendRedirect("employe?action=liste&msg=occupe");
        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String poste = req.getParameter("poste");
        String codeempStr = req.getParameter("codeemp");
        boolean forcer = "true".equals(req.getParameter("confirmerDoublon"));

        try {
            String msg;
            if (codeempStr == null || codeempStr.isEmpty()) {
                Employe e = new Employe(nom, prenom, poste);
                employeDAO.ajouter(e, forcer);
                msg = "ajout";
            } else {
                Employe e = employeDAO.trouverParCode(Integer.parseInt(codeempStr));
                e.setNom(nom);
                e.setPrenom(prenom);
                e.setPoste(poste);
                employeDAO.modifier(e);
                msg = "modif";
            }
            resp.sendRedirect("employe?action=liste&msg=" + msg);

        } catch (AvertissementDoublonException ex) {
            Employe saisie = new Employe(nom, prenom, poste);
            req.setAttribute("employe", saisie);
            req.setAttribute("avertissement", ex.getMessage());
            req.getRequestDispatcher("/employe/form.jsp").forward(req, resp);

        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}