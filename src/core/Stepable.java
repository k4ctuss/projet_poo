package core;

/**
 * Interface pour les objets qui peuvent être mis à jour pas à pas
 * Permet de gérer de manière générique grilles, groupes d'agents, etc.
 */
public interface Stepable {
    /**
     * Effectue une étape de mise à jour
     */
    void step();
    
    /**
     * Redémarre l'objet à son état initial
     */
    void restart();
}
