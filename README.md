# 🌿 L-System Simulation

Une application Java de simulation et visualisation de **Systèmes de Lindenmayer (L-Systems)**, avec rendu **2D** (Swing) et **3D** (JavaFX) interactif.

---

## 📋 Description

Les L-Systems sont des systèmes de réécriture formels utilisés pour modéliser la croissance de structures fractales et biologiques (plantes, arbres, etc). L'utilisateur définit un **axiome** et des **règles de production**, choisit un nombre d'itérations, puis visualise le résultat graphiquement — en 2D à fond noir avec un tracé vert, ou en 3D avec des cylindres animés.

---

## ✨ Fonctionnalités

- 🔤 Saisie libre d'un **axiome** et de **règles de production** via l'interface
- ➕ Ajout des règles une par une dans une liste (bouton **Add Rule**)
- 🔁 Choix du **nombre d'itérations** et de l'**angle** de rotation
- 🖼️ Rendu **2D** avec auto-centrage et auto-scaling du dessin dans le canvas
- 🌳 Rendu **3D** animé (rotation automatique sur l'axe Y, drag souris pour orbiter, scroll pour zoomer)
- 🎲 Support des **règles stochastiques** (probabilistes, ex: `F=FF:0.7`)
- 🔗 Support des **règles contextuelles** (pré/post-contexte, ex: `A<B>C=D`)
- 📦 **6 présets 2D classiques** accessibles depuis le menu

---

## 🏗️ Architecture

Le projet suit le patron **MVC** avec un système d'**Observer** (`ModeleEcoutable` / `EcouteurModele`).

```
src/
├── Main.java                            # Point d'entrée — lance LSystemView via SwingUtilities
├── controller/
│   └── LSystemController.java           # Contrôleur : parsing des règles, génération, présets
├── model/
│   ├── LSystem.java                     # Modèle : axiome + liste de règles
│   ├── LSystemInterpreter.java          # Moteur de réécriture (itérations, applyRules)
│   ├── LSystem2DInterpreter.java        # Rendu 2D turtle graphics (Swing/Graphics2D)
│   ├── LSystem3DInterpreter.java        # Rendu 3D (JavaFX, cylinders + rotations)
│   ├── LSystelVisualInterpreter.java    # Interface commune des rendus (drawResults)
│   ├── ILSystemRule.java                # Interface règle
│   ├── LSystemRule.java                 # Règle simple         ex: F=FF+F
│   ├── LSystemStochastiqueRule.java     # Règle stochastique   ex: F=FF:0.7
│   ├── LSystemContextuelRule.java       # Règle contextuelle   ex: A<B>C=D
│   └── BranchState.java                 # État sauvegardé dans la pile (x, y, angle)
├── utils/
│   ├── ModeleEcoutable.java             # Interface Observer
│   ├── AbstrcatModeleEcoutable.java     # Implémentation abstraite (fireChangement)
│   └── EcouteurModele.java              # Interface Écouteur (modeleMisAJour)
└── view/
    ├── LSystemView.java                 # Vue principale (JFrame) — menu + inputs + canvas
    └── CanvasPanel.java                 # JPanel de dessin 2D
```

### Types de règles supportées

| Type | Syntaxe saisie | Exemple |
|------|----------------|---------|
| **Simple** | `symbole=remplacement` | `F=FF+F-F` |
| **Stochastique** | `symbole=remplacement:probabilité` | `F=FF:0.7` (la somme des probas pour un même symbole doit être = 1) |
| **Contextuelle** | `préctx<symbole>postctx=remplacement` | `A<B>C=D` |

### Symboles de dessin (turtle graphics)

| Symbole | Action 2D | Action 3D |
|---------|-----------|-----------|
| `F` | Avancer et tracer une ligne | Tracer un cylindre vers l'avant |
| `X` | — | Cylindre orienté angle=90°, randomAngle=0° |
| `Y` | — | Cylindre orienté angle=0°, randomAngle=0° |
| `Z` | — | Cylindre orienté angle=0°, randomAngle=90° |
| `+` | Tourner à droite (+angle) | currentAngle += angle, randomAngle += 45 |
| `-` | Tourner à gauche (-angle) | currentAngle -= angle, randomAngle -= 90 |
| `[` | Sauvegarder position + angle | Empiler position, angle, randomAngle |
| `]` | Restaurer position + angle | Dépiler position, angle, randomAngle |

---

## 🎮 Utilisation

1. Lance l'application — la vue par défaut est en **2D**
2. Saisis un **axiome** (ex: `F`) dans le champ *Axiom*
3. Écris une **règle** dans le champ *Rules* (ex: `F=F[+F]F[-F]F`) puis clique **Add Rule**
4. Répète l'étape 3 pour ajouter plusieurs règles
5. **Sélectionne les règles** dans la liste, renseigne l'**angle** et les **itérations**
6. Clique **Generate** pour afficher le dessin
7. Via le menu **Interpreters** → choisis **2D** ou **3D**
8. Via le menu **standards 2D** → charge un préset (a à f) directement

> En **3D** : drag la souris pour faire orbiter la caméra, scroll pour zoomer. La structure tourne automatiquement sur l'axe Y.

---

## 📐 Présets intégrés (menu "standards 2D")

| Préset | Axiome | Règle | Angle | Itérations |
|--------|--------|-------|-------|-----------|
| **a** | `F-F-F-F` | `F=FF-F-F-F-F-F+F` | 90° | 4 |
| **b** | `F-F-F-F` | `F=FF-F-F-F-FF` | 90° | 4 |
| **c** | `F-F-F-F` | `F=FF-F+F-F-FF` | 90° | 3 |
| **d** | `F-F-F-F` | `F=FF-F--F-F` | 90° | 4 |
| **e** | `F-F-F-F` | `F=F-FF--F-F` | 90° | 5 |
| **f** | `F-F-F-F` | `F=F-F+F-F-F` | 90° | 4 |

---

## 🚀 Lancer le projet

### Prérequis

- **Java 17** (Amazon Corretto 17 recommandé)
- **JavaFX** (openjfx — requis pour le rendu 3D et l'intégration Swing/FX)
- **IntelliJ IDEA** (configuration `.idea` incluse avec les librairies JavaFX préconfigurées)

### Compilation & exécution (IntelliJ)

1. Ouvre le projet dans IntelliJ IDEA
2. Vérifie que les librairies JavaFX sont bien configurées (`.idea/libraries/`)
3. Lance `Main.java`

### Compilation manuelle

```bash
# Adapter les chemins vers ton installation JavaFX
javac --module-path /path/to/javafx/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.swing \
      -sourcepath src -d out \
      src/Main.java

java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.swing \
     -cp out Main
```

---
