package boids;

import gui.GUISimulator;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Test simple avec un seul groupe de boids
 * Configuration équilibrée avec les 3 règles (séparation, alignement, cohésion)
 * - Angle de vue : 270 degrés
 * - Voisinage moyen : 75
 * - Poids équilibrés : séparation=1.5, alignement=1.0, cohésion=0.8
 * - 20 boids bleus
 */
public class BoidTest {
    
    public static void main(String[] args) {
        // Paramètres de la simulation
        final int width = 800;
        final int height = 600;
        
        // Créer la GUI
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);
        
        // Configuration classique/normale
        BoidGroupConfig config = new BoidGroupConfig(
            75.0,                      // perceptionRadius (voisinage moyen)
            3.0 * Math.PI / 2.0,       // viewAngle (270 degrés)
            1.5,                       // separationWeight
            1.0,                       // alignmentWeight
            0.8,                       // cohesionWeight
            4.0,                       // maxSpeed
            0.05,                      // maxForce
            Color.BLUE                 // color
        );
        
        // Créer et initialiser le groupe
        BoidGroup group = new BoidGroup(config, width, height);
        group.init(20, width, height);  // 20 boids
        
        // Créer le simulateur
        
        BoidSimulator simulator = new BoidSimulator(gui);
        simulator.addBoidGroup(group, 1);  // Fréquence d'update = 1
        
        // Lancer la simulation
        gui.setSimulable(simulator);
    }
}
