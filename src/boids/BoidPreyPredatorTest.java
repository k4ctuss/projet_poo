package boids;

import gui.GUISimulator;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Test avec prédateurs et proies
 * - Proies (bleu) : légères, rapides, se regroupent (cohésion forte)
 * - Prédateurs (rouge) : lourds, plus lents, moins cohésifs
 * - Les proies fuient les prédateurs
 * - Les prédateurs chassent les proies
 * Chaque groupe : 10 boids
 * Angle de vue : 270 degrés
 * Fréquences d'update différentes : proies rapides (1), prédateurs lents (2)
 */
public class BoidPreyPredatorTest {
    
    public static void main(String[] args) {
        // Paramètres de la simulation
        final int width = 800;
        final int height = 600;
        
        // Créer la GUI
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);
        
        // Configuration des proies : légères, rapides, cohésion forte
        // Angle : 270 degrés
        BoidGroupConfig preyConfig = new BoidGroupConfig(
            120.0,                     // perceptionRadius (plus grand pour détecter prédateurs plus loin)
            3.0 * Math.PI / 2.0,       // viewAngle (270 degrés)
            1.0,                       // separationWeight (réduit - moins important que la fuite)
            0.6,                       // alignmentWeight (réduit - fuite prioritaire)
            0.5,                       // cohesionWeight (réduit aussi - fuite prioritaire)
            7,                       // maxSpeed (un peu plus rapides)
            0.3,                      // maxForce (augmenté pour des réactions plus vives)
            Color.BLUE                 // color
        );
        PreyGroup preyGroup = new PreyGroup(preyConfig, width, height);
        preyGroup.init(10, width, height);  // 10 proies
        
        // Configuration des prédateurs : lourds, plus lents, peu cohésifs
        // Angle : 270 degrés
        BoidGroupConfig predatorConfig = new BoidGroupConfig(
            130.0,                     // perceptionRadius (plus grand pour détecter proies)
            3.0 * Math.PI / 2.0,       // viewAngle (270 degrés)
            0.7,                       // separationWeight (faible - chasseur)
            0.4,                       // alignmentWeight (très faible - chasseur indépendant)
            0.1,                       // cohesionWeight (très faible - chasseurs solitaires)
            6.0,                       // maxSpeed (un peu plus rapides)
            0.3,                      // maxForce (augmenté pour des réactions plus vives)
            Color.RED                  // color
        );
        PredatorGroup predatorGroup = new PredatorGroup(predatorConfig, width, height);
        predatorGroup.init(10, width, height);  // 10 prédateurs
        
        // Créer le simulateur
        
        BoidSimulator simulator = new BoidSimulator(gui);
        
        // Configurer les interactions prédateur/proie
        simulator.setupInteraction(preyGroup, "predators", predatorGroup);
        simulator.setupInteraction(predatorGroup, "preys", preyGroup);
        
        // Ajouter les groupes avec des fréquences adaptées
        simulator.addBoidGroup(preyGroup, 1);          // Proies rapides : update chaque pas
        simulator.addBoidGroup(predatorGroup, 1);      // Prédateurs lents : update tous les 2 pas
        
        // Lancer la simulation
        gui.setSimulable(simulator);
    }
}
