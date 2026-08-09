<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${empty affectation ? "Ajouter" : "Modifier"} une affectation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>${empty affectation ? "Ajouter" : "Modifier"} une affectation</h1>
            <div class="sous-titre">Sélectionnez l'employé, le lieu et la date</div>
        </div>
        <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
    </header>

    <div class="page-formulaire">
        <div class="carte-formulaire">
            <span class="icone-form">🔗</span>
            <h1>${empty affectation ? "Ajouter" : "Modifier"} une affectation</h1>
            <div class="sous-titre-form">Tous les champs sont requis</div>

            <form class="formulaire" action="${pageContext.request.contextPath}/affecter" method="post">

                <c:if test="${not empty affectation}">
                    <input type="hidden" name="ancienCodeemp" value="${affectation.id.codeemp}"/>
                    <input type="hidden" name="ancienCodelieu" value="${affectation.id.codelieu}"/>
                    <input type="hidden" name="ancienneDate" value="${affectation.id.date}"/>
                </c:if>

                <label>Employé</label>
                <select name="codeemp" required>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.codeemp}"
                            ${not empty affectation && affectation.employe.codeemp == emp.codeemp ? 'selected' : ''}>
                            ${emp.nom} ${emp.prenom}
                        </option>
                    </c:forEach>
                </select>

                <label>Lieu</label>
                <select name="codelieu" required>
                    <c:forEach var="l" items="${lieux}">
                        <option value="${l.codelieu}"
                            ${not empty affectation && affectation.lieu.codelieu == l.codelieu ? 'selected' : ''}>
                            ${l.designation}
                        </option>
                    </c:forEach>
                </select>

                <label>Date</label>
                <input type="date" name="date" value="${affectation.id.date}" required/>

                <button type="submit">Enregistrer</button>
            </form>

            <div class="retour-centre">
                <a class="btn-retour" href="${pageContext.request.contextPath}/affecter?action=liste">← Retour à la liste</a>
            </div>
        </div>
    </div>
</body>
</html>