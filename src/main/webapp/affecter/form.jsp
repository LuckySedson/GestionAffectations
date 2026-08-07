<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Formulaire affectation</title></head>
<body>
    <h1>Ajouter une affectation</h1>

    <form action="affecter" method="post">
        <label>Employé :</label>
        <select name="codeemp" required>
            <c:forEach var="emp" items="${employes}">
                <option value="${emp.codeemp}">${emp.nom} ${emp.prenom}</option>
            </c:forEach>
        </select><br/>

        <label>Lieu :</label>
        <select name="codelieu" required>
            <c:forEach var="l" items="${lieux}">
                <option value="${l.codelieu}">${l.designation}</option>
            </c:forEach>
        </select><br/>

        <label>Date :</label>
        <input type="date" name="date" required/><br/>

        <button type="submit">Enregistrer</button>
    </form>

    <br/><a href="affecter?action=liste">Retour à la liste</a>
</body>
</html>