package core;


/**
 * Événement pour mettre à jour l'état d'un automate cellulaire à chaque étape de temps
 * Il utilise une instance de Grid pour gérer l'état des cellules
 * et un EventManager pour planifier les événements futurs
 */
public class AutomateCellEvent extends Event {
    private final Grid grid;
    private final EventManager manager;

    /**
     * Constructeur d'un événement d'automate cellulaire
     * @param date date de l'événement
     * @param grid la grille de l'automate cellulaire
     * @param manager le gestionnaire d'événements
     */
    public AutomateCellEvent(long date, Grid grid, EventManager manager) {
        super(date);
        this.grid = grid;
        this.manager = manager;
    }

    @Override
    public void execute() {
        grid.nextStep();
        manager.addEvent(new AutomateCellEvent(getDate() + 1, grid, manager));
    }
}
