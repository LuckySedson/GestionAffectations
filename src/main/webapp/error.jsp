<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erreur - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header>
        <h1>Service momentanément indisponible</h1>
    </header>
    <main>
        <div class="banniere erreur" style="animation:none; opacity:1;">
            ⚠️ ${empty erreur ? "Une erreur est survenue lors de l'accès aux données." : erreur}
            <br/>Vérifiez que le service MySQL est bien démarré, puis réessayez.
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Retour à l'accueil</a>
    </main>
</body>
</html>