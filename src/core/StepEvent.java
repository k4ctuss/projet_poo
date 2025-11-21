package core;

/**
 * Événement générique pour mettre à jour un objet Stepable
 * Peut être utilisé pour n'importe quel objet implémentant Stepable
 * (grilles, groupes de boids, ensemble de balles, etc.)
 */
public class StepEvent extends Event {
    private final Stepable stepable;
    private final long frequency;
    private final EventManager eventManager;
    
    /**
     * Crée un événement de mise à jour
     * @param date la date de l'événement
     * @param stepable l'objet à mettre à jour
     * @param eventManager le gestionnaire d'événements pour reschedule
     * @param frequency la fréquence de mise à jour (intervalle entre deux updates)
     */
    public StepEvent(long date, Stepable stepable, EventManager eventManager, long frequency) {
        super(date);
        this.stepable = stepable;
        this.frequency = frequency;
        this.eventManager = eventManager;
    }
    
    @Override
    public void execute() {
        // Effectue la mise à jour
        stepable.step();
        
        // Reschedule l'événement pour le prochain cycle
        eventManager.addEvent(new StepEvent(getDate() + frequency, stepable, eventManager, frequency));
    }
}
