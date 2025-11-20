package boids;

import java.util.List;

/**
 * Groupe de proies (petits poissons, sardines, etc.)
 * Comportement : cohésion + alignement + séparation (groupe serré)
 * + FUITE des prédateurs lorsqu'ils sont détectés
 */
public class PreyGroup extends BoidGroup {
    
    /**
     * Constructeur d'un groupe de proies
     * @param config configuration du groupe (poids, rayons, vitesses, etc.)
     * @param width largeur de la zone de simulation
     * @param height hauteur de la zone de simulation
     */
    public PreyGroup(BoidGroupConfig config, int width, int height) {
        super(config, width, height);
    }
    
    /**
     * Règles spécifiques aux proies :
     * Fuir les prédateurs détectés dans le voisinage
     * @param boid le boid proie auquel appliquer les règles
     */
    @Override
    protected void applyInteractionRules(Boid boid) {
        // Chercher les prédateurs dans le voisinage
        List<Boid> predators = getBoidsFromGroup(boid, "predators");
        
        if(!predators.isEmpty()) {
            double[] flee = ruleFlee(boid, predators);
            // Priorité très élevée à la fuite
            boid.applyForce(flee[0] * 5.0, flee[1] * 5.0);
        }
    }
    
    /**
     * Règle de fuite : s'éloigner du prédateur le plus proche
     * Force inverse à la direction du prédateur
     * x''_flee = -(position_predator_closest - position_self) * poids_fuite
     * @param boid le boid proie
     * @param predators liste des prédateurs proches
     * @return force de fuite (fx, fy)
     */
    private double[] ruleFlee(Boid boid, List<Boid> predators) {
        // Trouver le prédateur le plus proche
        Boid closestPredator = getClosest(boid, predators);
        
        // Force d'éloignement : vecteur opposé à la direction du prédateur
        double dx = boid.getX() - closestPredator.getX();
        double dy = boid.getY() - closestPredator.getY();
        double dist = Math.sqrt(dx*dx + dy*dy);
        
        if(dist > 0) {
            return new double[]{(dx / dist) * 2.0, (dy / dist) * 2.0};
        }
        return new double[]{0, 0};
    }
}
