<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Formulaire lieu</title></head>
<body>
    <h1>${empty lieu ? "Ajouter" : "Modifier"} un lieu</h1>

    <form action="lieu" method="post">
        <input type="hidden" name="codelieu" value="${lieu.codelieu}"/>

        <label>Désignation :</label>
        <input type="text" name="designation" value="${lieu.designation}" required/><br/>

        <label>Province :</label>
        <input type="text" name="province" value="${lieu.province}"/><br/>

        <button type="submit">Enregistrer</button>
    </form>

    <br/><a href="lieu?action=liste">Retour à la liste</a>
</body>
</html>