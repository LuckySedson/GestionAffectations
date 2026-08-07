<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Liste des affectations</title></head>
<body>
    <h1>Liste des affectations</h1>

    <a href="affecter?action=formAjout">+ Ajouter une affectation</a>

    <table border="1">
        <tr>
            <th>Employé</th><th>Lieu</th><th>Date</th><th>Actions</th>
        </tr>
        <c:forEach var="a" items="${affectations}">
            <tr>
                <td>${a.employe.nom} ${a.employe.prenom}</td>
                <td>${a.lieu.designation}</td>
                <td>${a.id.date}</td>
                <td>
                    <a href="affecter?action=supprimer&codeemp=${a.id.codeemp}&codelieu=${a.id.codelieu}&date=${a.id.date}"
                       onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br/><a href="index.jsp">Accueil</a>
</body>
</html>