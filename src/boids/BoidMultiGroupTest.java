package boids;

import gui.GUISimulator;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Test avec 3 groupes de boids isolés
 * Chaque groupe teste une seule règle :
 * - Groupe 1 (rouge) : ALIGNEMENT SEULEMENT
 * - Groupe 2 (vert) : SÉPARATION SEULEMENT
 * - Groupe 3 (bleu) : COHÉSION SEULEMENT
 * 
 * Chaque groupe : 10 boids
 * Angle de vue : 270 degrés
 * Voisinage : 75
 */
public class BoidMultiGroupTest {
    
    public static void main(String[] args) {
        // Paramètres de la simulation
        final int width = 800;
        final int height = 600;
        
        // Créer la GUI
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);
        
        // Configuration commune à tous les groupes
        double perceptionRadius = 75.0;
        double viewAngle = 3.0 * Math.PI / 2.0;  // 270 degrés
        double maxSpeed = 4.0;
        double maxForce = 0.05;
        
        // Groupe 1 : ALIGNEMENT SEULEMENT (séparation=0, alignement=1.0, cohésion=0)
        BoidGroupConfig configAlignment = new BoidGroupConfig(
            perceptionRadius,
            viewAngle,
            0.0,      // separationWeight = 0
            1.0,      // alignmentWeight = 1.0
            0.0,      // cohesionWeight = 0
            maxSpeed,
            maxForce,
            Color.RED
        );
        BoidGroup alignmentGroup = new BoidGroup(configAlignment, width, height);
        alignmentGroup.init(10, width, height);
        
        // Groupe 2 : SÉPARATION SEULEMENT (séparation=1.5, alignement=0, cohésion=0)
        BoidGroupConfig configSeparation = new BoidGroupConfig(
            perceptionRadius,
            viewAngle,
            1.5,      // separationWeight = 1.5
            0.0,      // alignmentWeight = 0
            0.0,      // cohesionWeight = 0
            maxSpeed,
            maxForce,
            Color.GREEN
        );
        BoidGroup separationGroup = new BoidGroup(configSeparation, width, height);
        separationGroup.init(10, width, height);
        
        // Groupe 3 : COHÉSION SEULEMENT (séparation=0, alignement=0, cohésion=0.8)
        BoidGroupConfig configCohesion = new BoidGroupConfig(
            perceptionRadius,
            viewAngle,
            0.0,      // separationWeight = 0
            0.0,      // alignmentWeight = 0
            0.8,      // cohesionWeight = 0.8
            maxSpeed,
            maxForce,
            Color.BLUE
        );
        BoidGroup cohesionGroup = new BoidGroup(configCohesion, width, height);
        cohesionGroup.init(10, width, height);
        
        // Créer le simulateur avec les 3 groupes
        
        BoidSimulator simulator = new BoidSimulator(gui);
        simulator.addBoidGroup(alignmentGroup, 1);
        simulator.addBoidGroup(separationGroup, 1);
        simulator.addBoidGroup(cohesionGroup, 1);
        
        // Lancer la simulation
        gui.setSimulable(simulator);
    }
}
