<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Formulaire affectation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>Ajouter une affectation</h1></header>
    <main>
        <form class="formulaire" action="affecter" method="post">
            <label>Employé</label>
            <select name="codeemp" required>
                <c:forEach var="emp" items="${employes}">
                    <option value="${emp.codeemp}">${emp.nom} ${emp.prenom}</option>
                </c:forEach>
            </select>

            <label>Lieu</label>
            <select name="codelieu" required>
                <c:forEach var="l" items="${lieux}">
                    <option value="${l.codelieu}">${l.designation}</option>
                </c:forEach>
            </select>

            <label>Date</label>
            <input type="date" name="date" required/>

            <button type="submit">Enregistrer</button>
        </form>

        <a class="retour" href="affecter?action=liste">← Retour à la liste</a>
    </main>
</body>
</html>