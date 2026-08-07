<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire lieu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>${empty lieu ? "Ajouter" : "Modifier"} un lieu</h1></header>
    <main>
        <form class="formulaire" action="lieu" method="post">
            <input type="hidden" name="codelieu" value="${lieu.codelieu}"/>

            <label>Désignation</label>
            <input type="text" name="designation" value="${lieu.designation}" required/>

            <label>Province</label>
            <input type="text" name="province" value="${lieu.province}"/>

            <button type="submit">Enregistrer</button>
        </form>

        <a class="retour" href="lieu?action=liste">← Retour à la liste</a>
    </main>
</body>
</html>