# GitViewer
> Application mobile permettant de consulter les projets GitHub des utilisateurs.

**Ont été implémentés durant le développement :**

- Un écran avec une liste d'éléments (liste des utilisateurs github).
- Un écran avec le détail d'un élément (liste des projets d'un utilisateur spécifique)
- Appel WebSercice à une API Rest (https://api.github.com/)
- Stockage de données en cache (Liste d'utilisateur en favoris)
- Architecture avec Singleton, Design Patterns, Modèle MVC et principe SOLID
- Barre de recherche (possibilité de rechercher un utilisateur github spécifique)
- Une page de liste favoris en + de la page de liste global
____________________________________________________

**Fonctionnalités :**

*Page principale :*
>Sur la page principale, on peut voir s'afficher des utilisateurs de GitHub, avec les boutons situés en bas de la fenêtre, on a la possibilité de changer de page (chaque page affiche 20 utilisateurs).
>Les utilisateurs sont affichés avec leur image de profile, leur pseudo ainsi que le lien vers leur profil github

<img src="http://cerenity.net/img/Screenshot_20200531-120001_GithubV.jpg" width="40%">


*Recherche :*
>Un bouton recherche permet de chercher un utilisateur spécifique sur GitHub

>le pseudo exacte doit être entré, sinon une erreur est affichée

<img src="http://cerenity.net/img/Screenshot_20200531-120158_GithubV.jpg" width="40%">

*Favoris :*
>le bouton favoris permet de voir ses utilisateurs favoris, cette liste est sauvegardée dans un fichier json en local.

>**Les fonctions permettant d'ajouter ou de supprimer des utilisateurs de cette liste ont posées de gros problèmes et sont toujours en cours d'implémentation. Pour le moment le fichier gson se rempli automatiquement lors du premier lancement de l'application avec la première page d'utilisateurs de GitHub afin de montrer que la liste des utilisateurs favoris fonctionne. L'idée finale est de pouvoir ajouter ou supprimer un utilisateur des favoris depuis sa page de détails, (les boutons sont actuellement présents mais non-fonctionnels)**

<img src="http://cerenity.net/img/Screenshot_20200531-120147_GithubV.jpg" width="40%">


*Détails d'un utilisateur :*
>Lorsque l'on clique sur un utilisateur, la page de détails s'ouvre, on peut alors consulter la liste de ses projets publiques

<img src="http://cerenity.net/img/Screenshot_20200531-120207_GithubV.jpg" width="40%">
____________________________________________________

