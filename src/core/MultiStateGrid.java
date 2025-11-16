package core;

import java.awt.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe représentant la grille d'un jeu de Cellule multi-états
 * Elle herite de la classe Grid et ajoute la gestion des états des cellules
 * Chaque cellule peut être dans un état de 0 à numberStates-1, chaque état
 * est représenté par une couleur différente
 */

public abstract class MultiStateGrid extends Grid{
    protected final Color[] palette;
    protected final int numberStates;
    protected final HashMap<Cell, Integer> originStateCell;
    protected final HashMap<Cell, Integer> currStateCell;
    protected final HashMap<Cell, Integer> nextStateCell;

    public MultiStateGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCell, int numberStates, HashMap<Cell, Integer> initialStateForCell){
        super(nbCellWidth, nbCellHeight, initialCell);
        this.numberStates = numberStates;
        this.palette = createPalette(numberStates);
        this.originStateCell = new HashMap<>(initialStateForCell);
        this.currStateCell = new HashMap<>();
        this.nextStateCell = new HashMap<>();
    }

    protected abstract Color[] createPalette(int numberStates);

    private void clearState(){
        this.currStateCell.clear();
        this.nextStateCell.clear();
    }

    /**
     * Applique l'état suivant à la grille
     * Met à jour les cellules vivantes et leurs états
     */
    protected void applyNextState(){
        // cleanUp the current state
        this.currAlive.clear();
        this.currStateCell.clear();

        //assign state
        this.currAlive.addAll(nextAlive);
        this.currStateCell.putAll(nextStateCell);

        //cleanup
        this.nextAlive.clear();
        this.nextStateCell.clear();
    }

    /**
     * Redémarre la grille à son état initial
     * Remet les cellules vivantes et leurs états initiaux
     */
    @Override
    public void restart(){
        super.restart();
        clearState();
        this.currStateCell.putAll(originStateCell);
        for(Cell c : currAlive){
            c.setColor(palette[currStateCell.get(c)]);
        }
    }

}
