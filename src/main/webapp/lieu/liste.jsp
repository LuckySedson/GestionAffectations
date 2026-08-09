<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Lieux - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:12px;">
        <div>
            <h1>Liste des lieux</h1>
            <div class="sous-titre">Consulter, ajouter ou modifier un lieu</div>
        </div>
        <div class="nav-header">
            <a class="btn-nav" href="${pageContext.request.contextPath}/employe?action=liste">Employés</a>
            <a class="btn-nav actif" href="${pageContext.request.contextPath}/lieu?action=liste">Lieux</a>
            <a class="btn-nav" href="${pageContext.request.contextPath}/affecter?action=liste">Affectations</a>
            <a class="btn-accueil" href="${pageContext.request.contextPath}/accueil">🏠 Accueil</a>
            <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
        </div>
    </header>
    <main>
        <%@ include file="/WEB-INF/jspf/banniere.jspf" %>

        <div class="actions">
            <form class="recherche" action="${pageContext.request.contextPath}/lieu" method="get">
                <input type="hidden" name="action" value="recherche"/>
                <input type="text" name="critere" placeholder="Rechercher par désignation ou province"/>
                <button type="submit" class="btn">Rechercher</button>
                <a href="${pageContext.request.contextPath}/lieu?action=liste" class="btn" style="background-color:#6b7280;">↻ Actualiser</a>
            </form>
            <a href="${pageContext.request.contextPath}/lieu?action=formAjout" class="btn">+ Ajouter un lieu</a>
        </div>

        <c:choose>
            <c:when test="${empty lieux}">
                <div class="vide">
                    <span class="icone">📭</span>
                    Aucun lieu trouvé.
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Code</th><th>Désignation</th><th>Province</th><th>Actions</th>
                    </tr>
                    <c:forEach var="l" items="${lieux}">
                        <tr>
                            <td>${l.codelieu}</td>
                            <td>${l.designation}</td>
                            <td>${l.province}</td>
                            <td>
                                <a class="action-link" href="${pageContext.request.contextPath}/lieu?action=formModif&codelieu=${l.codelieu}">Modifier</a>
                                <a class="action-link delete" href="${pageContext.request.contextPath}/lieu?action=supprimer&codelieu=${l.codelieu}"
                                   onclick="return confirm('Confirmer la suppression de ${l.designation} ?');">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </main>
</body>
</html>