<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${empty employe ? "Ajouter" : "Modifier"} un employé</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>${empty employe ? "Ajouter" : "Modifier"} un employé</h1>
            <div class="sous-titre">Renseignez les informations ci-dessous</div>
        </div>
        <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
    </header>

    <div class="page-formulaire">
        <div class="carte-formulaire">
            <span class="icone-form">👤</span>
            <h1>${empty employe ? "Ajouter" : "Modifier"} un employé</h1>
            <div class="sous-titre-form">Tous les champs marqués sont requis</div>

            <c:if test="${not empty avertissement}">
                <div class="banniere erreur">⚠️ ${avertissement}</div>
            </c:if>

            <form class="formulaire" action="${pageContext.request.contextPath}/employe" method="post">
                <input type="hidden" name="codeemp" value="${employe.codeemp}"/>
                <input type="hidden" name="confirmerDoublon" value="${not empty avertissement ? 'true' : 'false'}"/>

                <label>Nom</label>
                <input type="text" name="nom" value="${employe.nom}" required/>

                <label>Prénom</label>
                <input type="text" name="prenom" value="${employe.prenom}"/>

                <label>Poste</label>
                <input type="text" name="poste" value="${employe.poste}"/>

                <button type="submit">${not empty avertissement ? "Confirmer et enregistrer" : "Enregistrer"}</button>
            </form>

            <div class="retour-centre">
                <a class="btn-retour" href="${pageContext.request.contextPath}/employe?action=liste">← Retour à la liste</a>
            </div>
        </div>
    </div>
</body>
</html>