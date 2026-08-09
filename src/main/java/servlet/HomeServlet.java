package servlet;

import dao.EmployeDAO;
import dao.LieuDAO;
import dao.AffecterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/accueil")
public class HomeServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();
    private final LieuDAO lieuDAO = new LieuDAO();
    private final AffecterDAO affecterDAO = new AffecterDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("nbEmployes", employeDAO.listerTous().size());
        req.setAttribute("nbLieux", lieuDAO.listerTous().size());
        req.setAttribute("nbAffectations", affecterDAO.listerTous().size());

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}