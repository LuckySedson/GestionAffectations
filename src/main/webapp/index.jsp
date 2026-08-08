<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header>
        <h1>Gestion des affectations des employés</h1>
        <div class="sous-titre">Application de gestion Employés / Lieux / Affectations</div>
    </header>
    <main>
        <nav class="accueil">
            <ul>
                <li><a href="${pageContext.request.contextPath}/employe?action=liste">Gérer les employés</a></li>
                <li><a href="${pageContext.request.contextPath}/lieu?action=liste">Gérer les lieux</a></li>
                <li><a href="${pageContext.request.contextPath}/affecter?action=liste">Gérer les affectations</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>