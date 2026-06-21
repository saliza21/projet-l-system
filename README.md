# ⚔️ Combat Game

Un jeu de stratégie au tour par tour développé en Java dans le cadre du cours de **Génie Logiciel – L3 Informatique** à l'Université de Caen.

---

## 📋 Description

Combat Game est un jeu de stratégie multijoueur en 2D où des combattants s'affrontent sur une grille paramétrable. Chaque joueur dispose d'une énergie initiale et d'un arsenal varié, et doit éliminer ses adversaires en gérant intelligemment ses ressources.

---

## 🎮 Règles du jeu

- **2 à N joueurs** s'affrontent sur une grille 2D de taille configurable.
- Chaque joueur possède de l'**énergie** et des **armes** avec des munitions limitées.
- À chaque tour, un joueur peut effectuer **une** des actions suivantes :
  - 🚶 Se déplacer d'une case (4 directions)
  - 💣 Poser une **mine** ou une **bombe** sur une case voisine
  - 🔫 Effectuer un **tir** horizontal ou vertical
  - 🛡️ Activer le **bouclier** (protection pour le tour suivant)
  - ⏸️ Ne rien faire (pour économiser de l'énergie)
- La grille peut contenir des **murs** (cases bloquées) et des **pastilles d'énergie** récupérables.
- Une **mine** explose quand un combattant marche dessus.
- Une **bombe** explose après un délai et affecte les 8 cases voisines.
- Un joueur est **éliminé** quand son énergie atteint 0 ou devient négative.

---

## 🏗️ Architecture

Le projet suit le patron **MVC (Modèle-Vue-Contrôleur)** avec une séparation stricte des responsabilités.

```
src/main/java/un/caen/
├── Main.java                  # Point d'entrée
├── config/
│   └── Config.java            # Paramètres globaux du jeu
├── controller/
│   └── GameController.java    # Contrôleur principal
├── model/
│   ├── Game.java              # Logique du jeu
│   ├── feild/
│   │   └── Grid.java          # Grille de jeu
│   ├── items/
│   │   ├── Item.java          # Classe abstraite des items
│   │   ├── Bomb.java
│   │   ├── Mine.java
│   │   ├── Gun.java
│   │   ├── Weapon.java
│   │   ├── Energy.java
│   │   ├── Trace.java
│   │   └── Wall.java
│   └── player/
│       ├── Player.java        # Classe principale du joueur
│       ├── Fighter.java       # Interface combattant
│       ├── HeavyFighter.java  # Combattant lourd
│       ├── LightFighter.java  # Combattant léger
│       ├── PlayerFactory.java # Pattern Factory
│       └── PlayerProxy.java   # Pattern Proxy (visibilité partielle)
├── util/
│   ├── Cell.java
│   ├── Direction.java
│   ├── Position.java
│   ├── PlayerAction.java
│   └── Strategy.java          # Pattern Strategy
└── view/
    ├── GameBoardView.java     # Vue principale
    ├── ConsoleView.java       # Vue console
    ├── PlayerFrame.java       # Fenêtre par joueur
    └── GridModel.java         # Modèle de la grille (vue)
```

### Design Patterns utilisés

| Pattern | Utilisation |
|--------|-------------|
| **MVC** | Séparation Modèle / Vue / Contrôleur |
| **Factory** | Création des différents types de combattants |
| **Proxy** | Gestion de la visibilité partielle des mines/bombes |
| **Strategy** | Remplissage de la grille & IA des robots |

---

## ⚙️ Configuration

Tous les paramètres du jeu sont centralisés dans `Config.java` :

| Paramètre | Valeur par défaut | Description |
|-----------|:-----------------:|-------------|
| `GRID_WIDTH` | 6 | Largeur de la grille |
| `GRID_HEIGHT` | 6 | Hauteur de la grille |
| `CELL_SIZE` | 50 px | Taille d'une cellule |
| `NUMBER_OF_PLAYERS` | 2 | Nombre de joueurs |
| `DEFAULT_ENERGY` | 100 | Énergie de départ |
| `DEFAULT_GUN_AMMO` | 12 | Munitions par défaut |
| `DEFAULT_MINE_COUNTDOWN` | 10 | Délai d'explosion des mines |
| `MAX_WALL` | 5 | Nombre maximum de murs |
| `DEFAULT_ENERGYKIT_VALUE` | 50 | Valeur d'une pastille d'énergie |

---

## 🚀 Lancer le jeu

### Prérequis

- **Java 8+**
- **Maven** (pour la compilation)

### Compilation

```bash
mvn clean package
```

### Exécution

```bash
java -jar dist/combat-game.jar
```

---

## 📁 Structure du projet

```
combat-game/
├── src/          # Code source Java
├── dist/         # Exécutable (.jar)
├── doc/          # Javadoc générée
└── rapport/      # Rapport d'analyse du projet
```

---

## 📄 Licence

Projet académique – Université de Caen, L3 Informatique – Génie Logiciel (2024).
