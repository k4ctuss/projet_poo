# Projet POO - Simulateurs Multi-Agents

## 📋 Vue d'ensemble

Ce projet implémente plusieurs simulateurs multi-agents basés sur des automates cellulaires et des systèmes de boids :

- **Automates cellulaires** : Game of Life (Conway), Jeu d'Immigration, Modèle de Schelling
- **Systèmes de boids** : Simulation d'essaims avec interactions prédateur/proie

Le projet utilise une architecture orientée objet avec une hiérarchie de classes commune et un gestionnaire d'événements discrets pour orchestrer les mises à jour.

## 📁 Structure du projet

```
projet_poo/
├── src/                          # Code source Java
│   ├── core/                     # Classes fondamentales
│   │   ├── Cell.java            # Cellule d'automate avec position et état
│   │   ├── Grid.java            # Grille abstraite implémentant Stepable
│   │   ├── Stepable.java        # Interface pour objets mises à jour par étapes
│   │   ├── AutomateSimulator.java  # Simulateur de base pour automates
│   │   ├── Event.java           # Événement daté pour gestionnaire d'événements
│   │   ├── StepEvent.java       # Événement générique pour mettre à jour Stepables
│   │   └── EventManager.java    # Gestionnaire d'événements discrets
│   ├── conway/                  # Implémentation du Jeu de la Vie
│   │   ├── ConwayGrid.java      # Grille du Jeu de la Vie
│   │   ├── ConwaySimulator.java # Simulateur du Jeu de la Vie
│   │   └── TestConwaySimulator.java # Test avec GUI
│   ├── immigration/             # Implémentation du Jeu d'Immigration
│   │   ├── ImmigrationGrid.java # Grille du Jeu d'Immigration
│   │   ├── ImmigrationSimulator.java # Simulateur du Jeu d'Immigration
│   │   ├── TestImmigrationSimulator.java # Test avec GUI (petit)
│   │   └── TestImmigrationBig.java # Test avec GUI (80x80, aléatoire)
│   ├── schelling/               # Implémentation du Modèle de Schelling
│   │   ├── SchellingGrid.java   # Grille du Modèle de Schelling
│   │   ├── SchellingSimulator.java # Simulateur du Modèle de Schelling
│   │   └── TestSchellingSimulator.java # Test avec GUI
│   ├── boids/                   # Implémentation des Boids
│   │   ├── Boid.java            # Agent individuel (position, vélocité, forces)
│   │   ├── BoidGroup.java       # Groupe de boids avec règles (séparation, alignement, cohésion)
│   │   ├── BoidGroupConfig.java # Configuration immuable d'un groupe
│   │   ├── PreyGroup.java       # Groupe de proies (fuient les prédateurs)
│   │   ├── PredatorGroup.java   # Groupe de prédateurs (chassent les proies)
│   │   ├── BoidSimulator.java   # Simulateur de boids avec EventManager
│   │   ├── BoidTest.java        # Test d'un groupe simple équilibré
│   │   ├── BoidMultiGroupTest.java # Test de 3 groupes isolés
│   │   └── BoidPreyPredatorTest.java # Test prédateur/proie avec interactions
│   ├── Ball/                    # Implémentation des Balles (bonus)
│   ├── utils/                   # Utilitaires
│   │   └── ColorUtil.java       # Générateur de couleurs
│   └── TestInvader.java         # Test initial (balles)
├── lib/                         # Librairies externes
│   └── gui.jar                  # Librairie graphique pour simulateurs
├── doc/                         # Documentation JavaDoc
├── Makefile                     # Fichier de compilation et exécution
├── projet_poo.iml              # Configuration IntelliJ IDEA
└── README.md                    # Ce fichier
```

## 🚀 Compilation et Exécution

### Avec Makefile

```bash
# Compiler tous les fichiers
make compile

# Lancer les tests des automates cellulaires
make testConway           # Jeu de la Vie
make testImmigration      # Jeu d'Immigration (petit)
make testImmigrationBig   # Jeu d'Immigration (80x80, aléatoire)
make testSchelling        # Modèle de Schelling

# Lancer les tests des boids
make testBoidSimple       # Groupe simple (20 boids équilibrés)
make testBoidMultiGroup   # 3 groupes isolés (alignement, séparation, cohésion)
make testBoidPreyPredator # Proie vs Prédateur (interactions)

# Tests bonus
make testBalls            # Simulation de balles

# Utilitaires
make testAll              # Lance tous les tests
make clean                # Supprime les fichiers compilés
make help                 # Affiche l'aide
```

### Avec un IDE

**IntelliJ IDEA / NetBeans / Eclipse** :
1. Ouvrir le projet
2. Ajouter `lib/gui.jar` au classpath du projet
3. Clic droit sur le fichier test → Run

## 🎯 Description des modules

### Core (`core/`)
- **Cell.java** : Représente une cellule avec position (x, y) et état (int)
- **Stepable.java** : Interface pour tous les objets simables (Grid, BoidGroup, Balls)
  - `step()` : Effectue une étape de mise à jour
  - `restart()` : Réinitialise l'objet
- **Grid.java** : Grille abstraite implémentant Stepable (gère cellules vivantes, snapshot, wrapping)
- **AutomateSimulator.java** : Classe abstraite pour simulateurs d'automates (gère GUI, palette de couleurs)
- **Event.java** : Événement daté pour gestionnaire d'événements (classe abstraite)
- **StepEvent.java** : Événement générique pour mettre à jour n'importe quel Stepable
  - Remplace BoidUpdateEvent, BallEvent, et AutomateCellEvent
  - Paramètres : Stepable, EventManager, fréquence d'update
- **EventManager.java** : Orchestre l'exécution d'événements datés dans l'ordre

### Automates Cellulaires (`conway/`, `immigration/`, `schelling/`)

Chaque automate cellulaire implémente :
- **Grid** : Implémente Stepable, contient les cellules et implémente les règles de transition
  - `step()` appelle `nextStep()` (transition cellulaire)
  - `restart()` réinitialise à l'état initial
- **Simulator** : Gère la visualisation (palette, dessin) et utilise StepEvent avec l'EventManager
- **Test** : Programme avec main() pour lancer la simulation avec GUI

**Spécificités** :

| Automate | États | Règles | Particularité |
|----------|-------|--------|---------------|
| **Conway** | 0=mort, 1=vivant | 2-3 voisins vivants | Motifs émergents |
| **Immigration** | 0-N (N familles) | Cycle d'états | États multiples |
| **Schelling** | 0-N (N familles) | Satisfaction/déplacement | Position dynamique |

### Système de Boids (`boids/`)

Simulation d'essaims avec interactions :

- **Boid.java** : Agent individuel avec forces, vélocité, position
  - Méthodes : `applyForce()`, `update()`
  - Accumule les forces, puis met à jour selon Newton

- **BoidGroup.java** : Groupe de boids implémentant Stepable
  - Règles intra-groupe : séparation, alignement, cohésion
  - `applyRules()` : calcule les forces
  - `update()` : intègre la vélocité et position
  - `step()` : appelle applyRules() puis update()
  - `restart()` : réinitialise les boids à l'état initial
  - Supporte interactions inter-groupes

- **PreyGroup.java** : Groupe de proies
  - Fuit les prédateurs (`ruleFlee()`)
  - Cohésion forte pour rester groupé

- **PredatorGroup.java** : Groupe de prédateurs
  - Chasse les proies (`ruleHunt()`)
  - Cohésion faible (chasseurs solitaires)

- **BoidSimulator.java** : Simulateur avec EventManager
  - Gère plusieurs groupes avec fréquences d'update différentes
  - Configure les interactions prédateur/proie

- **StepEvent.java** : Événement générique (utilisé pour tous les Stepables)
  - Appelle `stepable.step()` pour mettre à jour l'objet
  - Reschedule automatiquement l'événement suivant
  - Utilisé par BoidSimulator, AutomateSimulator, et BallsSimulator

### Tests Boids

1. **BoidTest.java** : Un groupe équilibré
   - 20 boids bleus
   - Config : sep=1.5, align=1.0, coh=0.8
   - Montre un essaim cohésif

2. **BoidMultiGroupTest.java** : 3 groupes isolés
   - Groupe ROUGE : alignement seulement
   - Groupe VERT : séparation seulement
   - Groupe BLEU : cohésion seulement
   - Chaque groupe teste une règle seule

3. **BoidPreyPredatorTest.java** : Écosystème prédateur/proie
   - 10 proies bleu (rapides, cohésion forte)
   - 10 prédateurs rouge (lents, cohésion faible)
   - Proies fuient, prédateurs chassent

## 🏗️ Architecture

### Hiérarchie des classes

```
Simulable (interface)
├── AutomateSimulator (abstract)
│   ├── ConwaySimulator
│   ├── ImmigrationSimulator
│   └── SchellingSimulator
├── BoidSimulator
└── BallsSimulator

Stepable (interface)
├── Grid (abstract, implements Stepable)
│   ├── ConwayGrid
│   ├── ImmigrationGrid
│   └── SchellingGrid
├── BoidGroup (implements Stepable)
│   ├── PreyGroup
│   └── PredatorGroup
└── Balls (implements Stepable)

Event (abstract)
└── StepEvent (générique pour tous les Stepables)
```

### Design Patterns

1. **Template Method** : `AutomateSimulator.next()` / `draw()` + `createPalette()` abstrait
2. **Strategy** : Différentes règles via sous-classes `PreyGroup` / `PredatorGroup`
3. **Observer** : EventManager avec Events qui notifient via `step()`
4. **Composite** : BoidSimulator gère plusieurs BoidGroups
5. **Snapshot** : `snapshotState` pour lectures sûres pendant itération
6. **Interface-based Design** : Stepable abstraite le concept de mise à jour (polymorphisme)
7. **Generic Event** : StepEvent fonctionne avec n'importe quel Stepable (pas de duplication)

## 📊 Résultat attendu

### Automates cellulaires
- **Conway** : Motifs stables ou oscillants
- **Immigration** : Cycles d'états colorés
- **Schelling** : Ségrégation progressive basée sur le seuil

### Boids
- **Simple** : Essaim cohésif formant des vagues
- **MultiGroup** : Chaque groupe montre son comportement spécifique
- **Proie/Prédateur** : Dynamique chasse/fuite, potentiellement chaotique

## 🔧 Configuration et paramètres

### Pour modifier les simulations

**Automates cellulaires** : Voir les fichiers `Test*.java`
- Dimension de la grille
- Nombre d'états (couleurs)
- Seuils/règles spécifiques

**Boids** : Voir les fichiers de configuration dans `BoidTest.java`
- Perception radius
- Angle de vue
- Poids des règles (séparation, alignement, cohésion)
- Vitesses et forces max
- Fréquences d'update

## 🔄 Système Stepable et StepEvent

### Concept

Le projet utilise une interface unifiée `Stepable` pour tous les objets qui peuvent être mis à jour pas à pas :
- **Grid** (automates cellulaires)
- **BoidGroup** (groupes de boids)
- **Balls** (collection de balles)

Cette uniformité permet d'utiliser un seul type d'événement : **StepEvent**

### Avantages

- ✅ Pas de duplication de code (1 event au lieu de 3+)
- ✅ Facile d'ajouter de nouveaux Stepables
- ✅ Polymorphisme via l'interface Stepable
- ✅ Tous les simulateurs utilisent le même EventManager pattern

### Exemple d'utilisation

```java
// N'importe quel Stepable peut être géré de la même façon
eventManager.addEvent(new StepEvent(0, grid, eventManager, 1));
eventManager.addEvent(new StepEvent(0, boidGroup, eventManager, 2));
eventManager.addEvent(new StepEvent(1, balls, eventManager, 1));
```

## 📖 Compilation & Dépendances

- **Java 8+** requis
- **gui.jar** : Librairie fournie pour interface graphique
- **Makefile** : Automatise compilation et exécution

## 📝 Notes de développement

- Code commenté en français suivant la convention des spécifications
- Chaque classe inclut JavaDoc expliquant les équations physiques/mathématiques
- Architecture extensible : facile d'ajouter de nouveaux automates ou groupes de boids
- EventManager permet de gérer des fréquences d'update différentes par groupe
