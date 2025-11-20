package boids;

import java.awt.Color;

/**
 * Classe représentant un boid dans le modèle de boids
 * Chaque boid a une position, une direction et une couleur
 * Elle fournit des méthodes pour calculer la distance à un autre boid
 * et pour vérifier si un autre boid est dans son angle de vue et son voisinage
 */
public class Boid {
    private double x, y; // position
    private double dx,dy; // vecteur de direction (vélocité)
    private double fx, fy; // forces accumulées (accélération)
    private Color color; // couleur du boid

    /**
     * Constructeur d'un boid avec position, direction et couleur spécifiées
     * @param x position x du boid
     * @param y position y du boid
     * @param dx composante x du vecteur de direction
     * @param dy composante y du vecteur de direction
     * @param color couleur du boid
     */
    public Boid(double x, double y, double dx, double dy, Color color){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.fx = 0;
        this.fy = 0;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Calcule la distance euclidienne entre ce boid et un autre boid
     * @param other l'autre boid
     * @return la distance entre les deux boids
     */
    public double distanceTo(Boid other){
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Vérifie si un autre boid est dans l'angle de vue de ce boid
     * @param other l'autre boid
     * @param viewAngle l'angle de vue en radians
     * @return true si l'autre boid est dans l'angle de vue, false sinon
     */
    public boolean isInViewAngle(Boid other, double viewAngle){
        double angleToOther = Math.atan2(other.y - this.y, other.x - this.x);
        double boidAngle = Math.atan2(this.dy, this.dx);
        double angleDiff = Math.abs(angleToOther - boidAngle);
        angleDiff = Math.min(angleDiff, 2 * Math.PI - angleDiff); // angle minimal
        return angleDiff <= viewAngle / 2;
    }

    /**
     * Vérifie si un autre boid est dans le voisinage et l'angle de vue de ce boid
     * @param other l'autre boid
     * @param neighborhoodRadius le rayon du voisinage
     * @param viewAngle l'angle de vue en radians
     * @return true si l'autre boid est dans le voisinage et l'angle de vue, false sinon
     */
    public boolean isInNeighborhood(Boid other, double neighborhoodRadius, double viewAngle){
        return this.distanceTo(other) <= neighborhoodRadius && this.isInViewAngle(other, viewAngle);
    }

    /**
     * Applique une force à ce boid (accumulation des forces pour l'accélération)
     * Selon Newton : x''_n+1 = (1/m) * Σfi (masse = 1)
     * @param fx composante x de la force
     * @param fy composante y de la force
     */
    public void applyForce(double fx, double fy) {
        this.fx += fx;
        this.fy += fy;
    }

    /**
     * Met à jour la vélocité et la position selon le schéma explicite de Newton
     * x'_n+1 = x'_n + x''_n+1 (vélocité)
     * x_n+1 = x_n + x'_n+1 (position)
     * Avec wrapping toroïdal aux bords de la fenêtre
     * @param maxForce limite de la force appliquée (pour limiter l'accélération)
     * @param maxSpeed limite de la vélocité
     * @param width largeur de la zone (pour wrapping)
     * @param height hauteur de la zone (pour wrapping)
     */
    public void update(double maxForce, double maxSpeed, int width, int height) {
        // Limiter la force appliquée
        double forceMag = Math.sqrt(fx*fx + fy*fy);
        if(forceMag > maxForce) {
            fx = (fx / forceMag) * maxForce;
            fy = (fy / forceMag) * maxForce;
        }
        
        // Mise à jour vélocité : x'_n+1 = x'_n + x''_n+1
        dx += fx;
        dy += fy;
        
        // Limiter la vélocité
        double speedMag = Math.sqrt(dx*dx + dy*dy);
        if(speedMag > maxSpeed) {
            dx = (dx / speedMag) * maxSpeed;
            dy = (dy / speedMag) * maxSpeed;
        }
        
        // Mise à jour position : x_n+1 = x_n + x'_n+1
        x += dx;
        y += dy;
        
        // Wrapping toroïdal : les boids réapparaissent de l'autre côté
        x = ((x % width) + width) % width;
        y = ((y % height) + height) % height;
        
        // Réinitialiser les forces pour le prochain pas
        fx = 0;
        fy = 0;
    }
}
