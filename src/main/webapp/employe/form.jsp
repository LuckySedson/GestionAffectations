<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire employé</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>${empty employe ? "Ajouter" : "Modifier"} un employé</h1></header>
    <main>
        <form class="formulaire" action="employe" method="post">
            <input type="hidden" name="codeemp" value="${employe.codeemp}"/>

            <label>Nom</label>
            <input type="text" name="nom" value="${employe.nom}" required/>

            <label>Prénom</label>
            <input type="text" name="prenom" value="${employe.prenom}"/>

            <label>Poste</label>
            <input type="text" name="poste" value="${employe.poste}"/>

            <button type="submit">Enregistrer</button>
        </form>

        <a class="retour" href="employe?action=liste">← Retour à la liste</a>
    </main>
</body>
</html>