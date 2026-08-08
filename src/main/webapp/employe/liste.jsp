<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Employés - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>Liste des employés</h1>
            <div class="sous-titre">Consulter, rechercher, ajouter ou modifier un employé</div>
        </div>
        <a class="btn-accueil" href="${pageContext.request.contextPath}/index.jsp">🏠 Accueil</a>
    </header>
    <main>
        <%@ include file="/WEB-INF/jspf/banniere.jspf" %>

        <div class="actions">
            <form class="recherche" action="${pageContext.request.contextPath}/employe" method="get">
                <input type="hidden" name="action" value="recherche"/>
                <input type="text" name="critere" placeholder="Rechercher par nom"/>
                <button type="submit" class="btn">Rechercher</button>
                <a href="${pageContext.request.contextPath}/employe?action=liste" class="btn" style="background-color:#6b7280;">↻ Actualiser</a>
            </form>
            <a href="${pageContext.request.contextPath}/employe?action=formAjout" class="btn">+ Ajouter un employé</a>
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
                        <a class="action-link" href="${pageContext.request.contextPath}/employe?action=formModif&codeemp=${emp.codeemp}">Modifier</a>
                        <a class="action-link delete" href="${pageContext.request.contextPath}/employe?action=supprimer&codeemp=${emp.codeemp}"
                           onclick="return confirm('Confirmer la suppression de ${emp.nom} ${emp.prenom} ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</body>
</html>