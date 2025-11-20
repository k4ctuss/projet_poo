package boids;

import java.util.List;

/**
 * Groupe de prédateurs (gros poissons, requins, etc.)
 * Comportement : cohésion faible + alignement faible + séparation
 * + CHASSE des proies lorsqu'elles sont détectées
 */
public class PredatorGroup extends BoidGroup {
    
    /**
     * Constructeur d'un groupe de prédateurs
     * @param config configuration du groupe (poids, rayons, vitesses, etc.)
     * @param width largeur de la zone de simulation
     * @param height hauteur de la zone de simulation
     */
    public PredatorGroup(BoidGroupConfig config, int width, int height) {
        super(config, width, height);
    }
    
    /**
     * Règles spécifiques aux prédateurs :
     * Chasser les proies détectées dans le voisinage
     * @param boid le boid prédateur auquel appliquer les règles
     */
    @Override
    protected void applyInteractionRules(Boid boid) {
        // Chercher les proies dans le voisinage
        List<Boid> preys = getBoidsFromGroup(boid, "preys");
        
        if(!preys.isEmpty()) {
            double[] hunt = ruleHunt(boid, preys);
            // Priorité très élevée à la chasse
            boid.applyForce(hunt[0] * 4.0, hunt[1] * 4.0);
        }
    }
    
    /**
     * Règle de chasse : se diriger vers la proie la plus proche
     * Force vers la position de la proie
     * x''_hunt = (position_prey_closest - position_self) * poids_chasse
     * @param boid le boid prédateur
     * @param preys liste des proies proches
     * @return force de chasse (fx, fy)
     */
    private double[] ruleHunt(Boid boid, List<Boid> preys) {
        // Trouver la proie la plus proche
        Boid closestPrey = getClosest(boid, preys);

        // Force de chasse : vecteur vers la proie
        double dx = closestPrey.getX() - boid.getX();
        double dy = closestPrey.getY() - boid.getY();
        double dist = Math.sqrt(dx*dx + dy*dy);
        
        if(dist > 0) {
            return new double[]{(dx / dist) * 1.5, (dy / dist) * 1.5};
        }
        return new double[]{0, 0};
    }
}
