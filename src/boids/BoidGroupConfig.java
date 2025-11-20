package boids;

import java.awt.*;

/**
 * Configuration immuable pour le Groupe de boids
 * Contient les paramètres de perception et de comportement des boids
 * @param perceptionRadius rayon de perception des boids
 * @param viewAngle angle de vue des boids en radians
 * @param separationWeight poids de la force de séparation
 * @param alignmentWeight poids de la force d'alignement
 * @param cohesionWeight poids de la force de cohésion
 * @param maxSpeed vitesse maximale des boids
 * @param maxForce force maximale appliquée aux boids
 */
public record BoidGroupConfig(
        double perceptionRadius,
        double viewAngle,
        double separationWeight,
        double alignmentWeight,
        double cohesionWeight,
        double maxSpeed,
        double maxForce,
        Color color
) {
    /**
     * Constructeur avec validation des paramètres
     * @throws IllegalArgumentException si un paramètre est invalide (rayon ou vitesse négative, angle hors de l'intervalle (0, 2π], poids négatifs)
     */
    public BoidGroupConfig {
        if (perceptionRadius <= 0) throw new IllegalArgumentException("perceptionRadius > 0");
        if (viewAngle <= 0 || viewAngle > Math.PI * 2) throw new IllegalArgumentException("viewAngle in (0,2π]");
        if (separationWeight < 0 || alignmentWeight < 0 || cohesionWeight < 0) throw new IllegalArgumentException("weights >= 0");
        if (maxSpeed <= 0) throw new IllegalArgumentException("maxSpeed > 0");
        if (maxForce <= 0) throw new IllegalArgumentException("maxForce > 0");
    }

    public static BoidGroupConfig defaults() {
        return new BoidGroupConfig(50.0, Math.PI * 2, 1.5, 1.0, 0.8, 4.0, 0.05, Color.WHITE);
    }
}
