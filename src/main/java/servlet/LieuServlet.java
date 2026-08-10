package servlet;

import dao.LieuDAO;
import exception.AccesDonneesException;
import exception.DoublonException;
import exception.SuppressionImpossibleException;
import model.Lieu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/lieu")
public class LieuServlet extends HttpServlet {

    private final LieuDAO lieuDAO = new LieuDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter("action");
            if (action == null) action = "liste";

            switch (action) {
                case "liste":
                    List<Lieu> lieux = lieuDAO.listerTous();
                    req.setAttribute("lieux", lieux);
                    req.getRequestDispatcher("/lieu/liste.jsp").forward(req, resp);
                    break;

                case "formAjout":
                    req.getRequestDispatcher("/lieu/form.jsp").forward(req, resp);
                    break;

                case "formModif":
                    Integer codelieuModif = Integer.parseInt(req.getParameter("codelieu"));
                    req.setAttribute("lieu", lieuDAO.trouverParCode(codelieuModif));
                    req.getRequestDispatcher("/lieu/form.jsp").forward(req, resp);
                    break;

                case "supprimer":
                    Integer codelieuSupp = Integer.parseInt(req.getParameter("codelieu"));
                    Lieu lieuSupprime = lieuDAO.trouverParCode(codelieuSupp);
                    lieuDAO.supprimer(codelieuSupp);
                    req.getSession().setAttribute("dernierLieuSupprime", lieuSupprime);
                    resp.sendRedirect("lieu?action=liste&msg=suppr");
                    break;

                case "annulerSuppr":
                    Lieu lieuARestaurer = (Lieu) req.getSession().getAttribute("dernierLieuSupprime");
                    if (lieuARestaurer != null) {
                        Lieu nouveau = new Lieu(lieuARestaurer.getDesignation(), lieuARestaurer.getProvince());
                        lieuDAO.ajouter(nouveau);
                        req.getSession().removeAttribute("dernierLieuSupprime");
                    }
                    resp.sendRedirect("lieu?action=liste&msg=restaure");
                    break;
                case "recherche":
                    String critere = req.getParameter("critere");
                    req.setAttribute("lieux", lieuDAO.trouverParCritere(critere));
                    req.getRequestDispatcher("/lieu/liste.jsp").forward(req, resp);
                    break;

                default:
                    resp.sendRedirect("lieu?action=liste");
            }
        } catch (SuppressionImpossibleException ex) {
            resp.sendRedirect("lieu?action=liste&msg=occupe");
        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String designation = req.getParameter("designation");
        String province = req.getParameter("province");
        String codelieuStr = req.getParameter("codelieu");

        try {
            String msg;
            if (codelieuStr == null || codelieuStr.isEmpty()) {
                Lieu l = new Lieu(designation, province);
                lieuDAO.ajouter(l);
                msg = "ajout";
            } else {
                Lieu l = lieuDAO.trouverParCode(Integer.parseInt(codelieuStr));
                l.setDesignation(designation);
                l.setProvince(province);
                lieuDAO.modifier(l);
                msg = "modif";
            }
            resp.sendRedirect("lieu?action=liste&msg=" + msg);

        } catch (DoublonException ex) {
            Lieu saisie = new Lieu(designation, province);
            saisie.setCodelieu(codelieuStr == null || codelieuStr.isEmpty() ? null : Integer.parseInt(codelieuStr));
            req.setAttribute("lieu", saisie);
            req.setAttribute("erreurForm", ex.getMessage());
            req.getRequestDispatcher("/lieu/form.jsp").forward(req, resp);

        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}