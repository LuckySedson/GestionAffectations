1) Prérequis à installer
- telecharger Java 22
- Intellij IDEA Community 2025.2.2 + plugin "Smart Tomcat" (Settings → Plugins → rechercher "Smart Tomcat")
- Apache Tomcat 11.0.24 (a extraire dans C:\Tomcat\apache-tomcat-11.0.24)
- MySQL Server 8.0 via MySQL Workbench

2) Créer la base de données MySQL
- Dans MySQL Workbench : Mettre root le mot de passe 
- CREATE DATABASE gestion_affectations CHARACTER SET utf8mb4;

3) Ouverture du projet
- Dans Intellij IDEA :
  - ouvrir le projet dans /GestionAffectations
  - clic droit sur pom.xml → Maven → Reload Project
  - config artifact WAR : File → Project Structure → Artifacts → Web Application: Archive → for [module]
  - Configurer Smart Tomcat : Run → Edit Configurations → + → Smart Tomcat
  - Configurer hibernate.cfg.xml : normalement le mdp est deja OK sinon modifie le

4) Lancement du projet
- Connexion internet
- Cliquer sur Run
- Ouvrir http://localhost:8080/GestionAffectations/

5) Depannage
- ClassNotFoundException: com.mysql.cj.jdbc.Driver : Vérifier que WEB-INF/lib contient bien le connector MySQL
- Page blanche / erreur 500 sur accès BD : Vérifier que le service MySQL est démarré
- CSS/JS non appliqués : 	Vider le cache navigateur (Ctrl+Shift+R) après rebuild