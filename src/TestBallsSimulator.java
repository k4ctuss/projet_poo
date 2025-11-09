import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestBallsSimulator {

    public static void main(String[] args) {
        GUISimulator window = new GUISimulator(500, 500, Color.BLACK);
        List<Point> seed = new ArrayList<>();
        seed.add(new Point(10, 100));
        seed.add(new Point(20, 50));
        seed.add(new Point(30, 60));
        seed.add(new Point(2, 10));
        BallsSimulator bSimulator = new BallsSimulator(window, seed);
        window.setSimulable(bSimulator);
    }
}
