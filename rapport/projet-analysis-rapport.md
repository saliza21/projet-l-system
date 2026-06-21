# Rapport d'analyse du projet de jeu de stratégie

## 1. Organisation

Ce rapport d'analyse fait suite au devoir de contrôle continu réalisé dans le cadre du cours de Génie Logiciel de la L3 Informatique à l'Université de Caen.

Conformément aux instructions fournies, ce devoir a été réalisé en groupe de 4 étudiants. Un dépôt Git a été créé pour chaque groupe, nommé selon le modèle "durand-dupont-smith-doe" pour le nom court, et "Génie Logiciel Durand, Dupont, Smith, Doe" pour le nom long. Les enseignants Christophe Charrier, François Ledoyen, Guillaume Letellier, Amal Mahboubi et Yann Mathet ont été ajoutés en tant que managers du dépôt.

Le 2 décembre 2024 à 13h, le code de chaque groupe a été extrait de son dépôt pour correction. Chaque groupe devait fournir les éléments suivants à la racine du dépôt :

- Un répertoire `src` contenant le code commenté
- Un répertoire `doc` contenant la Javadoc
- Un répertoire `dist` contenant l'exécutable (un fichier .jar, et d'éventuelles ressources nécessaires à l'exécution)
- Un répertoire `rapport` contenant un mini-rapport au format PDF

## 2. Évaluation

L'évaluation du devoir de contrôle continu porte principalement sur la qualité de l'architecture logicielle et du code produit. Les critères d'évaluation sont les suivants :

- **Facilité de compréhension** : répartition en packages et en classes cohérente, noms de classes et de méthodes éloquents, commentaires dans le code lorsque c'est utile.
- **Maintenabilité et évolutivité** : absence de code spaghetti, pas d'inter-dépendance entre packages, pas de redondance, mise en œuvre de patterns permettant l'intégration de nouveaux éléments ou algorithmes.
- **Robustesse** : tests effectués.

Bien que l'ergonomie, le design et les fonctionnalités optionnelles soient appréciés, ils ne constituent pas l'essentiel de la note. Une application répondant fonctionnellement aux exigences du sujet, mais ne respectant pas les critères d'évaluation, n'obtiendrait pas la moyenne.

## 3. Conception du jeu de stratégie

### 3.1. Règles du jeu

Le jeu de stratégie opposera entre 2 et n joueurs sur une grille en 2 dimensions, de taille paramétrable. Chaque combattant dispose initialement d'une certaine énergie et d'armes de différents types, avec des munitions en nombre limité.

À chaque tour de jeu, les joueurs jouent l'un après l'autre selon un ordre initialement défini ou tiré aléatoirement. Les actions possibles sont :
- Déplacement d'une case (4 directions)
- Dépôt d'une mine ou d'une bombe sur l'une des 8 cases voisines
- Utilisation d'un tir horizontal ou vertical
- Déclenchement du bouclier, protégeant des tirs et bombes lors du prochain tour
- Ne rien faire pour économiser son énergie

La grille peut contenir des murs (cases non utilisables) et des pastilles d'énergie que les combattants peuvent récupérer.

Une mine explose lorsqu'un combattant se place sur sa case. Une bombe explose au bout d'un certain délai et impacte les combattants se trouvant sur l'une des 8 cases voisines. Les bombes et mines peuvent être visibles de tous ou seulement du joueur qui les a posées.

Les explosions et les tirs impactent l'énergie des combattants. Un déplacement et l'utilisation du bouclier ont aussi un coût en énergie. Un combattant perd le jeu lorsque son énergie devient négative ou nulle.

### 3.2. Conception de l'application

Conformément aux bonnes pratiques de conception et de développement vues en cours, l'objectif est de produire un code propre, modulaire, réutilisable et facilitant les futures extensions.

En particulier, l'architecture MVC (Modèle-Vue-Contrôleur) sera mise en place, avec une partie modèle complètement indépendante de l'interface graphique (vue et contrôleur). Cela permettra d'utiliser le jeu selon d'autres modalités (ligne de commande, réseau, etc.).

L'application sera facilement paramétrable, via des constantes clairement identifiées ou un fichier de configuration.

La gestion de la visibilité partielle des bombes et mines pour certains joueurs sera réalisée à l'aide du pattern Proxy, évitant ainsi de dupliquer le modèle complet en modèles partiels.

Différents types de combattants seront créés à l'aide d'une Factory, avec des caractéristiques variées (énergie, arsenal, etc.).

Le remplissage initial de la grille (murs, pastilles d'énergie, position des joueurs) et les stratégies des joueurs robots seront implémentés via le pattern Strategy.

### 3.3. Interface graphique

Une fenêtre principale montrera le jeu dans son intégralité, avec une liste des combattants et leurs caractéristiques. Des fenêtres secondaires seront générées, une par joueur, affichant uniquement ce que chacun peut voir.

Des composants tels que JTable seront utilisés pour afficher les informations des combattants de manière structurée.

## 4. Conclusion

La conception du jeu de stratégie présentée ici montre une approche modulaire et orientée objet, mettant en œuvre des principes de génie logiciel comme l'architecture MVC, l'utilisation de patterns de conception et la séparation des responsabilités.

Cette structure devrait permettre une bonne maintenabilité et extensibilité du projet, facilitant l'ajout de nouvelles fonctionnalités et l'évolution du jeu dans le temps.

Le rapport contient les principales informations sur l'organisation du devoir, les critères d'évaluation et les choix de conception détaillés. Il devrait fournir une base solide pour la compréhension et l'évaluation du travail réalisé par les étudiants.
