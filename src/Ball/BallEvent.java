package Ball;

import core.Event;
import core.EventManager;

/**
 * Événement pour mettre à jour les positions des balles
 */
public class BallEvent extends Event {

    private final EventManager manager;
    private final Balls balls;

    /**
     * Constructeur de l'événement de mise à jour des balles
     * @param date date d'exécution de l'événement
     * @param balls collection de balles à mettre à jour
     * @param manager gestionnaire d'événements pour planifier le prochain événement
     */
    public BallEvent(long date, Balls balls, EventManager manager) {
        super(date);
        this.balls = balls;
        this.manager = manager;
    }

    /**
     * Exécute l'événement : met à jour les positions des balles
     * et planifie le prochain événement
     */
    @Override
    public void execute() {
        balls.step();
        manager.addEvent(new BallEvent(getDate()+1, balls, manager));
    }

}
