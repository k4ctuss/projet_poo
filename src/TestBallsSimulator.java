import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestBallsSimulator {

    public static void main(String[] args) {
        GUISimulator window = new GUISimulator(1000, 500, Color.BLACK);
        List<Point> seed = new ArrayList<>();
        seed.add(new Point(500, 100));
        seed.add(new Point(700, 50));
        seed.add(new Point(250, 400));
        seed.add(new Point(10, 300));
        BallsSimulator bSimulator = new BallsSimulator(window, seed);
        window.setSimulable(bSimulator);
    }
}
