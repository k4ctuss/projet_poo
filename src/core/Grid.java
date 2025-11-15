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
    protected final Set<Cell> origin;
    protected final Set<Cell> nextAlive;
    protected final Set<Cell> currAlive;

    /**
     * Constructeur de la grille de Conway
     * @param nbCellWidth nombre de cellules en largeur de la grille
     * @param nbCellHeight nombre de cellules en hauteur de la grille
     * @param initialCells ensemble des cellules initialement vivantes
     * @throws IllegalArgumentException si la largeur ou la hauteur est inférieure ou égale à 0
     *
     */
    public Grid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCells){
        if (nbCellWidth <= 0 || nbCellHeight <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.nbCellWidth = nbCellWidth;
        this.nbCellHeight = nbCellHeight;
        this.origin = new HashSet<>(initialCells);
        this.currAlive = new HashSet<>();
        currAlive.addAll(initialCells); // initialisation
        this.nextAlive = new HashSet<>();
    }

    /**
     * Accesseur des cellules actuellement vivantes
     * @return un ensemble non modifiable des cellules actuellement vivantes
     */
    public Set<Cell> getCurrAlive() {
        return Collections.unmodifiableSet(currAlive);
    }

    protected boolean isAlive(Cell c){
        return currAlive.contains(c);
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
        this.nextAlive.clear();
        this.currAlive.clear();
    }

    public void restart(){
        clear();
        this.currAlive.addAll(this.origin);
    }


}

