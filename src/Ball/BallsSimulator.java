package Ball;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;
import java.util.List;

public class BallsSimulator implements Simulable {

    private final Balls balles;
    private final GUISimulator gui;

    private final int ballRadius;

    public BallsSimulator(GUISimulator gui, List<Point> seed){
        this(gui, seed, 10);
    }

    public BallsSimulator(GUISimulator gui, List<Point> seed, int ballRadius){
        this.gui = gui;
        this.balles = new Balls(seed);
        this.ballRadius = ballRadius;
        draw();
    }
    @Override
    public void next(){
        this.balles.step(gui.getWidth(), gui.getHeight(), ballRadius);
        draw();
    }

    @Override
    public void restart(){
        this.balles.reInit();
        draw();
    }

    private void draw(){
        gui.reset();

        for (int[] coord : balles.getListCoordinate()){
            gui.addGraphicalElement(new Oval(coord[0], coord[1], Color.WHITE, Color.WHITE, 10, 10));
        }
    }
}
