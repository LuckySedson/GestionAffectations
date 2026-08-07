<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Liste des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>Liste des affectations</h1></header>
    <main>
        <div class="actions">
            <a href="affecter?action=formAjout" class="btn">+ Ajouter une affectation</a>
        </div>

        <table>
            <tr>
                <th>Employé</th><th>Lieu</th><th>Date</th><th>Actions</th>
            </tr>
            <c:forEach var="a" items="${affectations}">
                <tr>
                    <td>${a.employe.nom} ${a.employe.prenom}</td>
                    <td>${a.lieu.designation}</td>
                    <td>${a.id.date}</td>
                    <td>
                        <a class="action-link delete"
                           href="affecter?action=supprimer&codeemp=${a.id.codeemp}&codelieu=${a.id.codelieu}&date=${a.id.date}"
                           onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a class="retour" href="../index.jsp">← Accueil</a>
    </main>
</body>
</html>