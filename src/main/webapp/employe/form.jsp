<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Formulaire employé</title></head>
<body>
    <h1>${empty employe ? "Ajouter" : "Modifier"} un employé</h1>

    <form action="employe" method="post">
        <input type="hidden" name="codeemp" value="${employe.codeemp}"/>

        <label>Nom :</label>
        <input type="text" name="nom" value="${employe.nom}" required/><br/>

        <label>Prénom :</label>
        <input type="text" name="prenom" value="${employe.prenom}"/><br/>

        <label>Poste :</label>
        <input type="text" name="poste" value="${employe.poste}"/><br/>

        <button type="submit">Enregistrer</button>
    </form>

    <br/><a href="employe?action=liste">Retour à la liste</a>
</body>
</html>