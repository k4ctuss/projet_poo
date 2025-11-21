package conway;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class    TestConwayGlider {

    /**
     * Construire un Gosper Glider Gun à partir d'une position de départ (x0, y0)
     * Le Gosper Glider Gun génère un glider toutes les 30 générations
     * @param x0 coordonnée x de la position de départ
     * @param y0 coordonnée y de la position de départ
     * @return un ensemble de cellules représentant un Gosper Glider Gun
     */
    static Set<Cell> buildGosperGliderGun(int x0, int y0){
        Set<Cell> gun = new HashSet<>();

        // Bloc gauche (petit carré)
        gun.add(new Cell(x0, y0+4, 1));
        gun.add(new Cell(x0, y0+5, 1));
        gun.add(new Cell(x0+1, y0+4, 1));
        gun.add(new Cell(x0+1, y0+5, 1));

        // Partie gauche du canon
        gun.add(new Cell(x0+10, y0+4, 1));
        gun.add(new Cell(x0+10, y0+5, 1));
        gun.add(new Cell(x0+10, y0+6, 1));
        gun.add(new Cell(x0+11, y0+3, 1));
        gun.add(new Cell(x0+11, y0+7, 1));
        gun.add(new Cell(x0+12, y0+2, 1));
        gun.add(new Cell(x0+12, y0+8, 1));
        gun.add(new Cell(x0+13, y0+2, 1));
        gun.add(new Cell(x0+13, y0+8, 1));
        gun.add(new Cell(x0+14, y0+5, 1));
        gun.add(new Cell(x0+15, y0+3, 1));
        gun.add(new Cell(x0+15, y0+7, 1));
        gun.add(new Cell(x0+16, y0+4, 1));
        gun.add(new Cell(x0+16, y0+5, 1));
        gun.add(new Cell(x0+16, y0+6, 1));
        gun.add(new Cell(x0+17, y0+5, 1));

        // Partie centrale
        gun.add(new Cell(x0+20, y0+2, 1));
        gun.add(new Cell(x0+20, y0+3, 1));
        gun.add(new Cell(x0+20, y0+4, 1));
        gun.add(new Cell(x0+21, y0+2, 1));
        gun.add(new Cell(x0+21, y0+3, 1));
        gun.add(new Cell(x0+21, y0+4, 1));
        gun.add(new Cell(x0+22, y0+1, 1));
        gun.add(new Cell(x0+22, y0+5, 1));
        gun.add(new Cell(x0+24, y0, 1));
        gun.add(new Cell(x0+24, y0+1, 1));
        gun.add(new Cell(x0+24, y0+5, 1));
        gun.add(new Cell(x0+24, y0+6, 1));

        // Bloc droit (petit carré)
        gun.add(new Cell(x0+34, y0+2, 1));
        gun.add(new Cell(x0+34, y0+3, 1));
        gun.add(new Cell(x0+35, y0+2, 1));
        gun.add(new Cell(x0+35, y0+3, 1));

        return gun;
    }

    public static void main(String[] args) {

        GUISimulator window = new GUISimulator(800, 600, Color.BLACK);
        Set<Cell> initCells = new HashSet<>();

        // Créer un Gosper Glider Gun qui va générer des gliders
        initCells.addAll(buildGosperGliderGun(5, 15));

        AutomateSimulator simu = new ConwaySimulator(10, window, 80, 60, initCells);

    }
}
