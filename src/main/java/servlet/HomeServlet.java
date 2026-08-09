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
import java.util.List;

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

        req.setAttribute("labelsLieux", construireJsonLabels(affecterDAO.compterParLieu()));
        req.setAttribute("valeursLieux", construireJsonValeurs(affecterDAO.compterParLieu()));

        req.setAttribute("labelsPostes", construireJsonLabels(employeDAO.compterParPoste()));
        req.setAttribute("valeursPostes", construireJsonValeurs(employeDAO.compterParPoste()));

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private String construireJsonLabels(List<Object[]> lignes) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lignes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(lignes.get(i)[0]).append("\"");
        }
        return sb.append("]").toString();
    }

    private String construireJsonValeurs(List<Object[]> lignes) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lignes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(lignes.get(i)[1]);
        }
        return sb.append("]").toString();
    }
}