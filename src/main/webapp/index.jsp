<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Accueil - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>Gestion des affectations des employés</h1>
            <div class="sous-titre">Application de gestion Employés / Lieux / Affectations</div>
        </div>
        <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
    </header>
    <main>
        <div class="hero">
            <h1>Bienvenue 👋</h1>
            <p>Gérez vos employés, vos lieux et leurs affectations en toute simplicité.</p>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="valeur">${nbEmployes}</div>
                <div class="label">Employés enregistrés</div>
            </div>
            <div class="stat-card">
                <div class="valeur">${nbLieux}</div>
                <div class="label">Lieux disponibles</div>
            </div>
            <div class="stat-card">
                <div class="valeur">${nbAffectations}</div>
                <div class="label">Affectations en cours</div>
            </div>
        </div>

        <nav class="accueil">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/employe?action=liste">
                        <span class="icone">👤</span>
                        Employés
                        <span class="desc">Gérer les employés</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/lieu?action=liste">
                        <span class="icone">📍</span>
                        Lieux
                        <span class="desc">Gérer les lieux</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/affecter?action=liste">
                        <span class="icone">🔗</span>
                        Affectations
                        <span class="desc">Gérer les affectations</span>
                    </a>
                </li>
            </ul>
        </nav>
    </main>
</body>
</html>