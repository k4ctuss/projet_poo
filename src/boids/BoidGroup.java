package boids;

import core.Stepable;
import java.util.*;

/**
 * Groupe de boids avec règles communes (séparation, alignement, cohésion)
 * Les interactions intra-groupe sont gérées ici
 * Les interactions inter-groupes (prédateurs/proies) sont gérées dans les sous-classes
 */
public class BoidGroup implements Stepable {
    protected List<Boid> boids;
    protected List<Boid> boidsOrigin;  // Copie des boids initiaux pour restart()
    protected BoidGroupConfig config;
    protected Map<String, BoidGroup> otherGroups;  // Autres groupes pour interactions
    protected int width;  // Largeur de la zone pour wrapping
    protected int height; // Hauteur de la zone pour wrapping
    
    /**
     * Constructeur d'un groupe de boids
     * @param config configuration du groupe (poids, rayons, vitesses, etc.)
     * @param width largeur de la zone de simulation
     * @param height hauteur de la zone de simulation
     */
    public BoidGroup(BoidGroupConfig config, int width, int height) {
        this.boids = new ArrayList<>();
        this.boidsOrigin = new ArrayList<>();
        this.config = config;
        this.otherGroups = new HashMap<>();
        this.width = width;
        this.height = height;
    }
    
    /**
     * Ajoute un boid au groupe
     * @param boid le boid à ajouter
     */
    public void addBoid(Boid boid) {
        this.boids.add(boid);
        // Créer une copie pour l'état initial
        this.boidsOrigin.add(new Boid(
            boid.getX(), boid.getY(),
            boid.getDx(), boid.getDy(),
            boid.getColor()
        ));
    }
    
    /**
     * Initialise le groupe avec n boids générés aléatoirement
     * @param n nombre de boids à générer
     * @param width largeur de la zone de génération
     * @param height hauteur de la zone de génération
     */
    public void init(int n, int width, int height) {
        for(int i = 0; i < n; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            double dx = (Math.random() - 0.5) * 4;  // Aléatoire entre -2 et 2
            double dy = (Math.random() - 0.5) * 4;  // Aléatoire entre -2 et 2
            
            Boid boid = new Boid(x, y, dx, dy, config.color());
            addBoid(boid);
        }
    }
    
    /**
     * Ajoute une référence à un autre groupe pour les interactions
     * @param groupName nom unique du groupe ("predators", "preys", etc.)
     * @param group le groupe interagissant
     */
    public void addInteractionGroup(String groupName, BoidGroup group) {
        this.otherGroups.put(groupName, group);
    }
    
    /**
     * Retourne la liste non modifiable des boids du groupe
     */
    public List<Boid> getBoids() {
        return Collections.unmodifiableList(boids);
    }
    
    /**
     * Retourne les voisins d'un boid dans le rayon et angle de perception (même groupe)
     * Utilise la méthode isInNeighborhood du boid
     * @param boid le boid dont on cherche les voisins
     * @return liste des voisins du boid
     */
    protected List<Boid> getNeighbors(Boid boid) {
        List<Boid> neighbors = new ArrayList<>();
        for(Boid other : boids) {
            if(boid != other && boid.isInNeighborhood(other, config.perceptionRadius(), config.viewAngle())) {
                neighbors.add(other);
            }
        }
        return neighbors;
    }
    
    /**
     * Retourne les boids d'un groupe interagissant dans le rayon et angle de perception
     * @param boid le boid dont on cherche les cibles
     * @param groupName nom du groupe cible ("predators", "preys", etc.)
     * @return liste des boids cibles du groupe interagissant
     */
    protected List<Boid> getBoidsFromGroup(Boid boid, String groupName) {
        List<Boid> targets = new ArrayList<>();
        if(!otherGroups.containsKey(groupName)) return targets;
        
        BoidGroup otherGroup = otherGroups.get(groupName);
        for(Boid other : otherGroup.getBoids()) {
            if(boid.isInNeighborhood(other, config.perceptionRadius(), config.viewAngle())) {
                targets.add(other);
            }
        }
        return targets;
    }
    
    /**
     * Règle de séparation : éviter les boids proches du même groupe
     * @param boid le boid dont on calcule la force de séparation
     * @param neighbors liste des voisins du même groupe
     * @return une force (fx, fy)
     */
    protected double[] ruleSeparation(Boid boid, List<Boid> neighbors) {
        double fx = 0, fy = 0;
        int count = 0;
        
        for(Boid other : neighbors) {
            double dist = boid.distanceTo(other);
            if(dist > 0 && dist < config.perceptionRadius()) {
                double dx = boid.getX() - other.getX();
                double dy = boid.getY() - other.getY();
                double mag = Math.sqrt(dx*dx + dy*dy);
                fx += (dx / mag) / dist;
                fy += (dy / mag) / dist;
                count++;
            }
        }
        
        if(count > 0) {
            fx /= count;
            fy /= count;
        }
        
        return new double[]{fx, fy};
    }
    
    /**
     * Règle d'alignement : aller dans la même direction que les voisins
     * @param boid le boid dont on calcule la force d'alignement
     * @param neighbors liste des voisins du même groupe
     * @return une force (fx, fy)
     */
    protected double[] ruleAlignment(Boid boid, List<Boid> neighbors) {
        if(neighbors.isEmpty()) return new double[]{0, 0};
        
        double avgDx = 0, avgDy = 0;
        for(Boid other : neighbors) {
            avgDx += other.getDx();
            avgDy += other.getDy();
        }
        avgDx /= neighbors.size();
        avgDy /= neighbors.size();
        
        return new double[]{avgDx - boid.getDx(), avgDy - boid.getDy()};
    }
    
    /**
     * Règle de cohésion : aller vers le centre du groupe
     * @param boid le boid dont on calcule la force de cohésion
     * @param neighbors liste des voisins du même groupe
     * @return une force (fx, fy)
     */
    protected double[] ruleCohesion(Boid boid, List<Boid> neighbors) {
        if(neighbors.isEmpty()) return new double[]{0, 0};
        
        double centerX = 0, centerY = 0;
        for(Boid other : neighbors) {
            centerX += other.getX();
            centerY += other.getY();
        }
        centerX /= neighbors.size();
        centerY /= neighbors.size();
        
        return new double[]{centerX - boid.getX(), centerY - boid.getY()};
    }
    
    /**
     * Applique les règles de comportement à tous les boids du groupe
     * Combine séparation, alignement, cohésion selon les poids de la config
     * Les règles inter-groupes sont appliquées dans applyInteractionRules (surchargée)
     */
    public void applyRules() {
        for(Boid boid : boids) {
            List<Boid> neighbors = getNeighbors(boid);
            
            // Règles intra-groupe (séparation, alignement, cohésion)
            double[] sep = ruleSeparation(boid, neighbors);
            double[] align = ruleAlignment(boid, neighbors);
            double[] coh = ruleCohesion(boid, neighbors);
            
            // Appliquer avec les poids de la config
            boid.applyForce(sep[0] * config.separationWeight(), sep[1] * config.separationWeight());
            boid.applyForce(align[0] * config.alignmentWeight(), align[1] * config.alignmentWeight());
            boid.applyForce(coh[0] * config.cohesionWeight(), coh[1] * config.cohesionWeight());
            
            // Règles inter-groupes (surchargées dans les sous-classes)
            applyInteractionRules(boid);
        }
    }
    
    /**
     * À surcharger : règles d'interaction avec d'autres groupes
     * Par défaut : pas d'interaction
     * PreyGroup → fuir les prédateurs
     * PredatorGroup → chasser les proies
     * @param boid le boid auquel appliquer les règles
     */
    protected void applyInteractionRules(Boid boid) {
        // Vide par défaut car pas d'interaction à surcharger dans les sous-classes
    }

    /**
     * Retourne le boid le plus proche parmi une liste donnée
     * @param ref le boid de référence
     * @param boids liste des boids candidats
     * @return le boid le plus proche
     */
    protected Boid getClosest(Boid ref, List<Boid> boids){
        Boid closest = boids.get(0);
        double closestDist = ref.distanceTo(closest);

        for(Boid candidat : boids) {
            double dist = ref.distanceTo(candidat);
            if(dist < closestDist) {
                closestDist = dist;
                closest = candidat;
            }
        }
        return closest;
    }

    
    /**
     * Met à jour tous les boids du groupe
     * 1. Applique les forces (accélération) avec applyRules()
     * 2. Intègre la vélocité et la position avec update()
     */
    public void update() {
        // Les forces ont déjà été appliquées par applyRules()
        // Il faut juste mettre à jour les positions
        for(Boid boid : boids) {
            boid.update(config.maxForce(), config.maxSpeed(), width, height);
        }
    }
    
    @Override
    public void step() {
        applyRules();
        update();
    }
    
    /**
     * Réinitialise le groupe à son état initial
     * Recopie les boids initiaux sauvegardés
     */
    @Override
    public void restart() {
        boids.clear();
        for(Boid original : boidsOrigin) {
            boids.add(new Boid(
                original.getX(), original.getY(),
                original.getDx(), original.getDy(),
                original.getColor()
            ));
        }
    }
}
