<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>Gestion des affectations des employés</h1></header>
    <main>
        <nav class="accueil">
            <ul>
                <li><a href="employe?action=liste">Gérer les employés</a></li>
                <li><a href="lieu?action=liste">Gérer les lieux</a></li>
                <li><a href="affecter?action=liste">Gérer les affectations</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>