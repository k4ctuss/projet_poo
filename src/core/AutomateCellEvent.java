package core;



public class AutomateCellEvent extends Event {
    private final Grid grid;
    private final EventManager manager;

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
