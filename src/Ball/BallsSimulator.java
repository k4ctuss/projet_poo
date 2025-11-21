package Ball;

import core.EventManager;
import core.StepEvent;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;
import java.util.List;

/**
 * Simulateur de balles rebondissantes dans une fenêtre graphique
 * Il gère une collection de balles et utilise un EventManager pour orchestrer les mises à jour
 */
public class BallsSimulator implements Simulable {

    private final Balls balles;
    private final GUISimulator gui;
    private EventManager eventManager;

    /**
     * Constructeur du simulateur de balles avec un rayon par défaut
     * @param gui l'interface graphique
     * @param seed liste des positions initiales des balles
     */
    public BallsSimulator(GUISimulator gui, List<Point> seed){
        this(gui, seed, 10);
    }

    /**
     * Constructeur du simulateur de balles
     * @param gui l'interface graphique
     * @param seed liste des positions initiales des balles
     * @param ballRadius rayon des balles
     */
    public BallsSimulator(GUISimulator gui, List<Point> seed, int ballRadius){
        this.gui = gui;
        this.balles = new Balls(seed, gui.getPanelWidth(), gui.getPanelHeight(), ballRadius);
        this.gui.setSimulable(this);
        this.eventManager = new EventManager();
        eventManager.addEvent(new StepEvent(1, balles, eventManager, 1));
        draw();
    }

    @Override
    public void next(){
        eventManager.next();
        draw();
    }

    @Override
    public void restart(){
        eventManager.restart();
        this.balles.restart();
        draw();
        eventManager.addEvent(new StepEvent(1, balles, eventManager, 1));
    }

    private void draw(){
        gui.reset();

        for (int[] coord : balles.getListCoordinate()){
            gui.addGraphicalElement(new Oval(coord[0], coord[1], Color.WHITE, Color.WHITE, balles.getBallRadius()));
        }
    }
}
