package immigration;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestImmigrationBig {
    public static void main(String[] args) {

        GUISimulator window = new GUISimulator(800, 800, Color.WHITE);
        int nbState = 5;
        int gridWidth = 80;
        int gridHeight = 80;
        Set<Cell> initCells = new HashSet<>();
        
        Random rand = new Random();
        
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                int state = rand.nextInt(nbState); // État aléatoire entre 0 et 4
                if (state != 0) {
                    Cell c = new Cell(x, y, state);
                    initCells.add(c);
                }
            }
        }

        AutomateSimulator simu = new ImmigrationSimulator(10, window, gridWidth, gridHeight, initCells, nbState);

    }
}
