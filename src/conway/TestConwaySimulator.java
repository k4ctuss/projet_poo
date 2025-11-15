package conway;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TestConwaySimulator {

    /**
     * Construire un glider à partir d'une position de départ (x0, y0)
     * @param x0 coordonnée x de la position de départ
     * @param y0 coordonnée y de la position de départ
     * @return un ensemble de cellules représentant un glider
     */
    static Set<Cell> buildGlider(int x0, int y0){
        Set<Cell> glider = new HashSet<>();
        glider.add(new Cell(x0+1, y0));
        glider.add(new Cell(x0+2, y0+1));
        glider.add(new Cell(x0, y0+2));
        glider.add(new Cell(x0+1, y0+2));
        glider.add(new Cell(x0+2, y0+2));

        return glider;
    }

    public static void main(String[] args) {

        GUISimulator window = new GUISimulator(500, 500, Color.BLACK);
        Set<Cell> initCells = new HashSet<>();
        // cell pour la forme de l'exemple du sujet
        initCells.add(new Cell(11, 11));
        initCells.add(new Cell(11, 12));
        initCells.add(new Cell(12, 11));
        initCells.add(new Cell(13, 12));
        initCells.add(new Cell(14, 14));

        // cellule pour créer un glider
        initCells.addAll(buildGlider(20, 20));
        initCells.addAll(buildGlider(40, 10));


        AutomateSimulator simu = new ConwaySimulator(10, window, 50, 50, initCells);

    }
}
