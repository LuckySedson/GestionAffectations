<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Liste des lieux</title></head>
<body>
    <h1>Liste des lieux</h1>

    <a href="lieu?action=formAjout">+ Ajouter un lieu</a>

    <table border="1">
        <tr>
            <th>Code</th><th>Désignation</th><th>Province</th><th>Actions</th>
        </tr>
        <c:forEach var="l" items="${lieux}">
            <tr>
                <td>${l.codelieu}</td>
                <td>${l.designation}</td>
                <td>${l.province}</td>
                <td>
                    <a href="lieu?action=formModif&codelieu=${l.codelieu}">Modifier</a>
                    |
                    <a href="lieu?action=supprimer&codelieu=${l.codelieu}"
                       onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br/><a href="index.jsp">Accueil</a>
</body>
</html>