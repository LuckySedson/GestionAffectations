<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Employés - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
    <script src="${pageContext.request.contextPath}/js/modal.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:12px;">
        <div>
            <h1>Liste des employés</h1>
            <div class="sous-titre">Consulter, rechercher, ajouter ou modifier un employé</div>
        </div>
        <div class="nav-header">
            <a class="btn-nav actif" href="${pageContext.request.contextPath}/employe?action=liste">Employés</a>
            <a class="btn-nav" href="${pageContext.request.contextPath}/lieu?action=liste">Lieux</a>
            <a class="btn-nav" href="${pageContext.request.contextPath}/affecter?action=liste">Affectations</a>
            <a class="btn-accueil" href="${pageContext.request.contextPath}/accueil">🏠 Accueil</a>
            <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
        </div>
    </header>
    <main>
        <%@ include file="/WEB-INF/jspf/banniere.jspf" %>

        <div class="actions">
            <form class="recherche" action="${pageContext.request.contextPath}/employe" method="get">
                <input type="hidden" name="action" value="recherche"/>
                <input type="text" name="critere" placeholder="Rechercher par nom, prénom ou code"/>
                <button type="submit" class="btn">Rechercher</button>
                <a href="${pageContext.request.contextPath}/employe?action=liste" class="btn" style="background-color:#6b7280;">↻ Actualiser</a>
            </form>
            <a href="${pageContext.request.contextPath}/employe?action=formAjout" class="btn">+ Ajouter un employé</a>
        </div>

        <div class="table-scroll">
            <c:choose>
                <c:when test="${empty employes}">
                    <div class="vide">
                        <span class="icone">📭</span>
                        Aucun employé trouvé.
                    </div>
                </c:when>
                <c:otherwise>
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
                                    <a class="action-link delete" href="javascript:void(0)"
                                       onclick="ouvrirConfirmation('Confirmer la suppression de ${emp.nom} ${emp.prenom} ?', function() {
                                           window.location.href='${pageContext.request.contextPath}/employe?action=supprimer&codeemp=${emp.codeemp}';
                                       });">Supprimer</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
    <%@ include file="/WEB-INF/jspf/modal.jspf" %>
</body>
</html>