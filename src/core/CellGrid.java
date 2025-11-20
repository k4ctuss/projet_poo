package core;

import java.util.HashMap;
import java.util.Set;

/**
 * Classe représentant une grille du jeu d'automate cellulaire
 * Elle contient les cellules vivantes actuelles et permet de calculer l'état suivant de la grille
 * Elle gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public abstract class CellGrid extends Grid<Cell>{

    protected final int numberStates;  // Nombre d'états possibles (2 (vivant ou mort) pour Conway, N pour Immigration/Schelling)
    protected HashMap<Cell, Integer> snapshotState;  // Snapshot de l'état courant pour lire pendant nextStep()

    public CellGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCells, int numberStates){
        super(nbCellWidth, nbCellHeight, initialCells);
        if (numberStates <= 0) {
            throw new IllegalArgumentException("numberStates must be positive");
        }
        this.numberStates = numberStates;
        this.snapshotState = new HashMap<>();
    }

    /**
     * Renvoie les cellules voisines d'une cellule donnée en tenant compte du wrapping
     * @param c la cellule dont on veut les voisins
     * @return un tableau des cellules voisines
     */
    protected Cell[] getNeighbor(Cell c){

        return new Cell[]{
                new Cell(wrapX(c.getX()-1), wrapY(c.getY()-1)),
                new Cell(c.getX(), wrapY(c.getY()-1)),
                new Cell(wrapX(c.getX()+1), wrapY(c.getY()-1)),
                new Cell(wrapX(c.getX()-1), c.getY()),
                new Cell(wrapX(c.getX()+1), c.getY()),
                new Cell(wrapX(c.getX()-1), wrapY(c.getY()+1)),
                new Cell(c.getX(), wrapY(c.getY()+1)),
                new Cell(wrapX(c.getX()+1), wrapY(c.getY()+1))
        };
    }

    @Override
    public void restart(){
        super.restart();
        for(Cell c : this.origin){
            Cell clone = new Cell(c.getX(), c.getY(), c.getState());
            this.currAlive.add(clone);
            this.snapshotState.put(clone, clone.getState());
        }
    }

    public int getNumberStates() {
        return numberStates;
    }

    /**
     * Retourne l'état d'une cellule depuis le snapshot (lecture sûre pendant nextStep)
     * @param c la cellule
     * @return l'état de la cellule
     */
    protected int getStateSnapshot(Cell c) {
        return snapshotState.getOrDefault(c, 0);
    }

    /**
     * Crée un snapshot sûr des états courants pour nextStep
     * À appeler UNE FOIS au début de nextStep, pas dans la boucle
     */
    protected void buildSnapshot() {
        snapshotState = new HashMap<>();
        for(Cell c : currAlive) {
            snapshotState.put(c, c.getState());
        }
    }
}
