<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Liste des lieux</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <header><h1>Liste des lieux</h1></header>
    <main>
        <div class="actions">
            <a href="lieu?action=formAjout" class="btn">+ Ajouter un lieu</a>
        </div>

        <table>
            <tr>
                <th>Code</th><th>Désignation</th><th>Province</th><th>Actions</th>
            </tr>
            <c:forEach var="l" items="${lieux}">
                <tr>
                    <td>${l.codelieu}</td>
                    <td>${l.designation}</td>
                    <td>${l.province}</td>
                    <td>
                        <a class="action-link" href="lieu?action=formModif&codelieu=${l.codelieu}">Modifier</a>
                        <a class="action-link delete" href="lieu?action=supprimer&codelieu=${l.codelieu}"
                           onclick="return confirm('Confirmer la suppression ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a class="retour" href="../index.jsp">← Accueil</a>
    </main>
</body>
</html>