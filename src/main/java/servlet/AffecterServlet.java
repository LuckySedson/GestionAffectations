package servlet;

import dao.AffecterDAO;
import dao.EmployeDAO;
import dao.LieuDAO;
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

        String action = req.getParameter("action");
        if (action == null) action = "liste";

        switch (action) {
            case "liste":
                req.setAttribute("affectations", affecterDAO.listerTous());
                req.getRequestDispatcher("/affecter/liste.jsp").forward(req, resp);
                break;

            case "formAjout":
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

            default:
                resp.sendRedirect("affecter?action=liste");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer codeemp = Integer.parseInt(req.getParameter("codeemp"));
        Integer codelieu = Integer.parseInt(req.getParameter("codelieu"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));

        affecterDAO.ajouter(codeemp, codelieu, date);

        resp.sendRedirect("affecter?action=liste&msg=ajout");
    }
}