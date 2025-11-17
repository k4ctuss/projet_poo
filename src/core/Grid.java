package core;

import java.util.HashSet;
import java.util.*;

/**
 * Classe représentant une grille du jeu d'automate cellulaire
 * Elle contient les cellules vivantes actuelles et permet de calculer l'état suivant de la grille
 * Elle gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public abstract class Grid {
    private final int nbCellWidth;
    private final int nbCellHeight;
    protected final int numberStates;  // Nombre d'états possibles (2 (vivant ou mort) pour Conway, N pour Immigration/Schelling)
    protected final Set<Cell> origin;
    protected final Set<Cell> currAlive;
    protected HashMap<Cell, Integer> snapshotState;  // Snapshot de l'état courant pour lire pendant nextStep()

    /**
     * Constructeur de la grille d'automate cellulaire
     * @param nbCellWidth nombre de cellules en largeur de la grille
     * @param nbCellHeight nombre de cellules en hauteur de la grille
     * @param initialCells ensemble des cellules initialement vivantes
     * @param numberStates nombre d'états possibles pour les cellules
     * @throws IllegalArgumentException si la largeur ou la hauteur est inférieure ou égale à 0
     *
     */
    public Grid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCells, int numberStates){
        if (nbCellWidth <= 0 || nbCellHeight <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        if (numberStates <= 0) {
            throw new IllegalArgumentException("numberStates must be positive");
        }
        this.nbCellWidth = nbCellWidth;
        this.nbCellHeight = nbCellHeight;
        this.numberStates = numberStates;
        this.origin = Set.copyOf(initialCells);
        this.currAlive = new HashSet<>();
        this.snapshotState = new HashMap<>();
    }

    /**
     * Accesseur des cellules actuellement vivantes
     * @return un ensemble non modifiable des cellules actuellement vivantes
     */
    public Set<Cell> getCurrAlive() {
        return Collections.unmodifiableSet(currAlive);
    }

    private int wrapX(int x){
        int r = x%nbCellWidth;
        return r<0? r+nbCellWidth: r;
    }

    private int wrapY(int y){
        int r = y%nbCellHeight;
        return r<0? r+nbCellHeight: r;
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

    /**
     * Passe à l'étape suivante de la simulation en appliquant les règles du jeu de l'automate simulé
     */
    public abstract void nextStep();

    protected void clear(){
        this.currAlive.clear();
        this.snapshotState.clear();
    }

    public void restart(){
        clear();
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

