import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;
import java.util.List;

public class BallsSimulator implements Simulable {

    private final Balls balles;
    private final GUISimulator gui;
    public BallsSimulator(GUISimulator gui, List<Point> seed){
        this.gui = gui;
        this.balles = new Balls(seed);
    }
    @Override
    public void next(){
        this.balles.translate(10, 10);
        draw();
    }

    @Override
    public void restart(){
        this.balles.reInit();
        draw();
    }

    private void draw(){
        gui.reset();

        for (Point p : balles.getCurrents()){
            gui.addGraphicalElement(new Oval(p.x, p.y, Color.WHITE, Color.WHITE, 10, 10));
        }
    }
}
