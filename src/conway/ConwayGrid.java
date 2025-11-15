package conway;

import java.util.*;

/**
 * Classe représentant la grille du jeu de la vie de Conway
 * Elle contient les cellules vivantes actuelles et permet de calculer l'état suivant de la grille
 * Elle gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public class ConwayGrid {

    private final int nbCellWidth;
    private final int nbCellHeight;
    private final Set<Cell> origin;
    private final Set<Cell> nextAlive;
    private final Set<Cell> currAlive;

    /** 
     * Constructeur de la grille de Conway
     * @param nbCellWidth nombre de cellules en largeur de la grille
     * @param nbCellHeight nombre de cellules en hauteur de la grille
     * @param initialCells ensemble des cellules initialement vivantes
     * @throws IllegalArgumentException si la largeur ou la hauteur est inférieure ou égale à 0
     * 
    */
    public ConwayGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCells){
        if (nbCellWidth <= 0 || nbCellHeight <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.nbCellWidth = nbCellWidth;
        this.nbCellHeight = nbCellHeight;
        this.origin = initialCells;
        this.currAlive = new HashSet<>();
        currAlive.addAll(initialCells); // initialisation
        this.nextAlive = new HashSet<>();
    }


    public Set<Cell> getCurrAlive() {
        return currAlive;
    }

    private boolean isAlive(Cell c){
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
    private Cell[] getNeighbor(Cell c){

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
     * Passe à l'étape suivante de la simulation en appliquant les règles du jeu de la vie de Conway
     * Les cellules vivantes avec 2 ou 3 voisins vivants restent en vie
     * Les cellules mortes avec exactement 3 voisins vivants deviennent vivantes
     * 
     */
    public void nextStep(){
        Set<Cell> candidate = new HashSet<>();
        // pour chaque cellule on regard si elle sera encore en vie a t+1 et on met ses voisins morts en candidats
        for(Cell c : currAlive){
            int nbNeighborAlive = 3; // on set à 3 et compare à 0 ou 1 pour gagner des cycles
            for (Cell neighbor : getNeighbor(c)){
                if(isAlive(neighbor)){
                    nbNeighborAlive--;
                }else{
                    candidate.add(neighbor);
                }
            }
            if (nbNeighborAlive == 0 || nbNeighborAlive == 1){
                nextAlive.add(c);
            }
        }

        // on test maintenant les candidats à la vie
        for(Cell c : candidate){
            int nbNeighborAlive = 3; // on set à 3 et compare à 0 ou 1 pour gagner des cycles
            for (Cell neighbor : getNeighbor(c)){
                if(isAlive(neighbor)){
                    nbNeighborAlive--;
                }
            }
            if (nbNeighborAlive == 0){
                nextAlive.add(c);
            }
        }

        this.currAlive.clear();
        this.currAlive.addAll(nextAlive);
        this.nextAlive.clear();

    }

    private void clear(){
        this.nextAlive.clear();
        this.currAlive.clear();
    }

    public void restart(){
        clear();
        this.currAlive.addAll(this.origin);
    }


}
