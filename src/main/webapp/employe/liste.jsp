<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Liste des employés</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>Liste des employés</h1></header>
    <main>
        <div class="actions">
            <form class="recherche" action="employe" method="get">
                <input type="hidden" name="action" value="recherche"/>
                <input type="text" name="critere" placeholder="Rechercher par nom"/>
                <button type="submit" class="btn">Rechercher</button>
            </form>
            <a href="employe?action=formAjout" class="btn">+ Ajouter un employé</a>
        </div>

        <table>
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
                        <a class="action-link" href="employe?action=formModif&codeemp=${emp.codeemp}">Modifier</a>
                        <a class="action-link delete" href="employe?action=supprimer&codeemp=${emp.codeemp}"
                           onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a class="retour" href="../index.jsp">← Accueil</a>
    </main>
</body>
</html>