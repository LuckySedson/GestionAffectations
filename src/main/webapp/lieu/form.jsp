<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${empty lieu ? "Ajouter" : "Modifier"} un lieu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>${empty lieu ? "Ajouter" : "Modifier"} un lieu</h1>
            <div class="sous-titre">Renseignez les informations ci-dessous</div>
        </div>
        <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
    </header>

    <div class="page-formulaire">
        <div class="carte-formulaire">
            <span class="icone-form">📍</span>
            <h1>${empty lieu ? "Ajouter" : "Modifier"} un lieu</h1>
            <div class="sous-titre-form">Tous les champs marqués sont requis</div>

            <c:if test="${not empty erreurForm}">
                <div class="banniere erreur">⚠️ ${erreurForm}</div>
            </c:if>
            <form class="formulaire" action="${pageContext.request.contextPath}/lieu" method="post">
                <input type="hidden" name="codelieu" value="${lieu.codelieu}"/>

                <label>Désignation</label>
                <input type="text" name="designation" value="${lieu.designation}" required/>

                <label>Province</label>
                <input type="text" name="province" value="${lieu.province}"/>

                <button type="submit">Enregistrer</button>
            </form>

            <div class="retour-centre">
                <a class="btn-retour" href="${pageContext.request.contextPath}/lieu?action=liste">← Retour à la liste</a>
            </div>
        </div>
    </div>
</body>
</html>