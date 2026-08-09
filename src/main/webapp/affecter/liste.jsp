<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Affectations - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:12px;">
        <div>
            <h1>Liste des affectations</h1>
            <div class="sous-titre">Affecter un employé à un lieu, à une date donnée</div>
        </div>
        <div class="nav-header">
            <a class="btn-nav" href="${pageContext.request.contextPath}/employe?action=liste">Employés</a>
            <a class="btn-nav" href="${pageContext.request.contextPath}/lieu?action=liste">Lieux</a>
            <a class="btn-nav actif" href="${pageContext.request.contextPath}/affecter?action=liste">Affectations</a>
            <a class="btn-accueil" href="${pageContext.request.contextPath}/accueil">🏠 Accueil</a>
            <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
        </div>
    </header>
    <main>
        <%@ include file="/WEB-INF/jspf/banniere.jspf" %>

        <div class="actions">
            <form class="recherche" action="${pageContext.request.contextPath}/affecter" method="get">
                <input type="hidden" name="action" value="recherche"/>
                <input type="text" name="critere" placeholder="Rechercher par employé ou lieu"/>
                <button type="submit" class="btn">Rechercher</button>
                <a href="${pageContext.request.contextPath}/affecter?action=liste" class="btn" style="background-color:#6b7280;">↻ Actualiser</a>
            </form>
            <a href="${pageContext.request.contextPath}/affecter?action=formAjout" class="btn">+ Ajouter une affectation</a>
        </div>

        <table>
            <tr>
                <th>Employé</th>
                <th>Lieu</th>
                <th>
                    Date
                    <a href="${pageContext.request.contextPath}/affecter?action=liste&tri=asc" style="font-size:11px; text-decoration:none;">▲</a>
                    <a href="${pageContext.request.contextPath}/affecter?action=liste&tri=desc" style="font-size:11px; text-decoration:none;">▼</a>
                </th>
                <th>Actions</th>
            </tr>
            <c:forEach var="a" items="${affectations}">
                <tr>
                    <td>${a.employe.nom} ${a.employe.prenom}</td>
                    <td>${a.lieu.designation}</td>
                    <td>${a.id.date}</td>
                    <td>
                        <a class="action-link"
                           href="${pageContext.request.contextPath}/affecter?action=formModif&codeemp=${a.id.codeemp}&codelieu=${a.id.codelieu}&date=${a.id.date}">Modifier</a>
                        <a class="action-link delete"
                           href="${pageContext.request.contextPath}/affecter?action=supprimer&codeemp=${a.id.codeemp}&codelieu=${a.id.codelieu}&date=${a.id.date}"
                           onclick="return confirm('Confirmer la suppression de cette affectation ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</body>
</html>