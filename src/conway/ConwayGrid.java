package conway;

import java.util.*;

public class ConwayGrid {

    private final int nbCellWidth;
    private final int nbCellHeight;
    private final Set<Cell> origin;
    private final Set<Cell> nextAlive;
    private final Set<Cell> currAlive;

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
