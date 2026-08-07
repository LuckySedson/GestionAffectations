<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Liste des employés</title></head>
<body>
    <h1>Liste des employés</h1>

    <form action="employe" method="get">
        <input type="hidden" name="action" value="recherche"/>
        <input type="text" name="critere" placeholder="Rechercher par nom"/>
        <button type="submit">Rechercher</button>
    </form>

    <a href="employe?action=formAjout">+ Ajouter un employé</a>

    <table border="1">
        <tr>
            <th>Code</th><th>Nom</th><th>Prénom</th><th>Poste</th><th>Actions</th>
        </tr>
        <c:forEach var="emp" items="${employes}">
            <tr>
                <td>${emp.codeemp}</td>
                <td>${emp.nom}</td>
                <td>${emp.prenom}</td>
                <td>${emp.poste}</td>
                <td>
                    <a href="employe?action=formModif&codeemp=${emp.codeemp}">Modifier</a>
                    |
                    <a href="employe?action=supprimer&codeemp=${emp.codeemp}"
                       onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br/><a href="index.jsp">Accueil</a>
</body>
</html>