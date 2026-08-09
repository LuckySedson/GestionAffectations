<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Accueil - Gestion des affectations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <header style="display:flex; justify-content:space-between; align-items:center;">
        <div>
            <h1>Gestion des affectations des employés</h1>
            <div class="sous-titre">Application de gestion Employés / Lieux / Affectations</div>
        </div>
        <button class="theme-toggle" onclick="basculerTheme()">🌓 Thème</button>
    </header>

    <main class="accueil-page">
        <nav class="accueil">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/employe?action=liste">
                        <span class="icone">👤</span>
                        <span class="infos">
                            <span class="titre-carte">Employés</span>
                            <span class="desc">Gérer les employés</span>
                        </span>
                        <span class="badge">${nbEmployes}</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/lieu?action=liste">
                        <span class="icone">📍</span>
                        <span class="infos">
                            <span class="titre-carte">Lieux</span>
                            <span class="desc">Gérer les lieux</span>
                        </span>
                        <span class="badge">${nbLieux}</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/affecter?action=liste">
                        <span class="icone">🔗</span>
                        <span class="infos">
                            <span class="titre-carte">Affectations</span>
                            <span class="desc">Gérer les affectations</span>
                        </span>
                        <span class="badge">${nbAffectations}</span>
                    </a>
                </li>
            </ul>
        </nav>

        <div class="dashboard">
            <div class="graphique-carte">
                <h2>📊 Affectations par lieu</h2>
                <canvas id="graphLieux"></canvas>
            </div>
            <div class="graphique-carte">
                <h2>📊 Employés par poste</h2>
                <canvas id="graphPostes"></canvas>
            </div>
        </div>
    </main>

    <script>
        const couleurTexte = getComputedStyle(document.documentElement).getPropertyValue('--text').trim();
        const palette = ['#2c7be5', '#4ade80', '#f0656a', '#f5a623', '#9c6ade', '#22c1c3', '#e07a5f'];

        new Chart(document.getElementById('graphLieux'), {
            type: 'pie',
            data: {
                labels: ${labelsLieux},
                datasets: [{
                    data: ${valeursLieux},
                    backgroundColor: palette
                }]
            },
            options: {
                maintainAspectRatio: false,
                plugins: { legend: { position: 'bottom', labels: { color: couleurTexte, boxWidth: 14, font: { size: 13 } } } }
            }
        });

        new Chart(document.getElementById('graphPostes'), {
            type: 'bar',
            data: {
                labels: ${labelsPostes},
                datasets: [{
                    label: 'Nombre d\'employés',
                    data: ${valeursPostes},
                    backgroundColor: '#2c7be5',
                    borderRadius: 6
                }]
            },
            options: {
                maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: {
                    x: { ticks: { color: couleurTexte, font: { size: 12 } } },
                    y: { ticks: { color: couleurTexte, font: { size: 12 } }, beginAtZero: true }
                }
            }
        });
    </script>
</body>
</html>