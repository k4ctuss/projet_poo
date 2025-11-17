package immigration;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TestImmigrationSimulator {
    public static void main(String[] args) {

        GUISimulator window = new GUISimulator(500, 500, Color.WHITE);
        int nbState = 4;
        Set<Cell> initCells = new HashSet<>();
        int baseX = 0, baseY = 0;
        int[][] states = {
                {3, 0, 1, 1, 0},
                {3, 1, 1, 1, 2},
                {1, 1, 3, 2, 2},
                {0, 1, 2, 2, 2},
                {0, 3, 2, 2, 1},
        };

        for (int dy = 0; dy < states.length; dy++) {
            for (int dx = 0; dx < states[dy].length; dx++) {
                int s = states[dy][dx];
                if (s != 0) {
                    Cell c = new Cell(baseX + dx, baseY + dy, s);  // État directement dans Cell
                    initCells.add(c);
                }
            }
        }

        AutomateSimulator simu = new ImmigrationSimulator(50, window, 5, 5, initCells, nbState);

    }
}
