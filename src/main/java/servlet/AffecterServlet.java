package servlet;

import dao.AffecterDAO;
import dao.EmployeDAO;
import dao.LieuDAO;
import exception.AccesDonneesException;
import exception.DoublonException;
import exception.SuppressionImpossibleException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/affecter")
public class AffecterServlet extends HttpServlet {

    private final AffecterDAO affecterDAO = new AffecterDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();
    private final LieuDAO lieuDAO = new LieuDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter("action");
            if (action == null) action = "liste";

            switch (action) {
                case "liste":
                    String tri = req.getParameter("tri");
                    req.setAttribute("affectations", affecterDAO.listerTous(tri));
                    req.setAttribute("triActuel", tri);
                    req.getRequestDispatcher("/affecter/liste.jsp").forward(req, resp);
                    break;

                case "formAjout":
                    req.setAttribute("employes", employeDAO.listerTous());
                    req.setAttribute("lieux", lieuDAO.listerTous());
                    req.getRequestDispatcher("/affecter/form.jsp").forward(req, resp);
                    break;

                case "formModif":
                    Integer ceModif = Integer.parseInt(req.getParameter("codeemp"));
                    Integer clModif = Integer.parseInt(req.getParameter("codelieu"));
                    LocalDate dModif = LocalDate.parse(req.getParameter("date"));

                    req.setAttribute("affectation", affecterDAO.trouverParId(ceModif, clModif, dModif));
                    req.setAttribute("employes", employeDAO.listerTous());
                    req.setAttribute("lieux", lieuDAO.listerTous());
                    req.getRequestDispatcher("/affecter/form.jsp").forward(req, resp);
                    break;

                case "supprimer":
                    Integer codeemp = Integer.parseInt(req.getParameter("codeemp"));
                    Integer codelieu = Integer.parseInt(req.getParameter("codelieu"));
                    LocalDate date = LocalDate.parse(req.getParameter("date"));
                    affecterDAO.supprimer(codeemp, codelieu, date);
                    resp.sendRedirect("affecter?action=liste&msg=suppr");
                    break;

                case "recherche":
                    String critere = req.getParameter("critere");
                    req.setAttribute("affectations", affecterDAO.rechercher(critere));
                    req.getRequestDispatcher("/affecter/liste.jsp").forward(req, resp);
                    break;

                default:
                    resp.sendRedirect("affecter?action=liste");
            }
        } catch (SuppressionImpossibleException ex) {
            resp.sendRedirect("affecter?action=liste&msg=occupe");
        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer codeemp = Integer.parseInt(req.getParameter("codeemp"));
        Integer codelieu = Integer.parseInt(req.getParameter("codelieu"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));

        try {
            String ancienCodeempStr = req.getParameter("ancienCodeemp");
            String msg;

            if (ancienCodeempStr == null || ancienCodeempStr.isEmpty()) {
                affecterDAO.ajouter(codeemp, codelieu, date);
                msg = "ajout";
            } else {
                Integer ancienCodeemp = Integer.parseInt(ancienCodeempStr);
                Integer ancienCodelieu = Integer.parseInt(req.getParameter("ancienCodelieu"));
                LocalDate ancienneDate = LocalDate.parse(req.getParameter("ancienneDate"));

                affecterDAO.modifier(ancienCodeemp, ancienCodelieu, ancienneDate, codeemp, codelieu, date);
                msg = "modif";
            }
            resp.sendRedirect("affecter?action=liste&msg=" + msg);

        } catch (DoublonException ex) {
            req.setAttribute("employes", employeDAO.listerTous());
            req.setAttribute("lieux", lieuDAO.listerTous());
            req.setAttribute("erreurForm", ex.getMessage());
            req.getRequestDispatcher("/affecter/form.jsp").forward(req, resp);

        } catch (AccesDonneesException ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}