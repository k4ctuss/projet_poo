package boids;

import core.Event;
import core.EventManager;

/**
 * Événement qui déclenche la mise à jour d'un groupe de boids
 * Chaque groupe peut avoir sa propre fréquence d'update (pas de temps spécifique)
 * L'événement crée automatiquement l'événement suivant pour l'animation perpétuelle
 */
public class BoidUpdateEvent extends Event {
    private final BoidGroup group;
    private final EventManager eventManager;
    private final BoidSimulator sim;

    /**
     * Constructeur d'un événement d'update de groupe
     * @param date date de l'événement
     * @param group le groupe de boids à mettre à jour
     * @param eventManager le gestionnaire d'événements
     * @param sim simulateur des boid
     */
    public BoidUpdateEvent(long date, BoidGroup group, EventManager eventManager, BoidSimulator sim) {
        super(date);
        this.group = group;
        this.eventManager = eventManager;
        this.sim = sim;
    }
    
    /**
     * Exécute la mise à jour du groupe :
     * 1. Applique les règles (calcule les forces)
     * 2. Met à jour les positions
     * Crée l'événement suivant pour l'animation perpétuelle
     */
    @Override
    public void execute() {
        // Appliquer les règles (calcul des forces)
        group.applyRules();
        // Mettre à jour les positions
        group.update();
        sim.draw();
        
        // Créer l'événement suivant pour continuer l'animation
        eventManager.addEvent(new BoidUpdateEvent(
            getDate() + sim.getDelay(group),
            group, 
            eventManager, 
            sim
        ));
    }
}
