package conway;

import core.Cell;
import core.Grid;
import java.util.*;

/**
 * Classe représentant la grille du jeu de la vie de Conway
 * Elle contient les cellules vivantes actuelles et permet de calculer l'état suivant de la grille
 * Elle gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public class ConwayGrid extends Grid {

    /** 
     * Constructeur de la grille de Conway, appel le constucteur parent
     * @param nbCellWidth nombre de cellules en largeur de la grille
     * @param nbCellHeight nombre de cellules en hauteur de la grille
     * @param initialCells ensemble des cellules initialement vivantes
     *
    */
    public ConwayGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCells){
        super(nbCellWidth, nbCellHeight, initialCells);
    }


    /**
     * Passe à l'étape suivante de la simulation en appliquant les règles du jeu de la vie de Conway
     * Les cellules vivantes avec 2 ou 3 voisins vivants restent en vie
     * Les cellules mortes avec exactement 3 voisins vivants deviennent vivantes
     * 
     */
    @Override
    public void nextStep(){
        Set<Cell> candidate = new HashSet<>();
        // pour chaque cellule on regard si elle sera encore en vie a t+1 et on met ses voisins morts en candidats
        for(Cell c : currAlive){
            int nbNeighborAlive = 0; 
            for (Cell neighbor : getNeighbor(c)){
                if(isAlive(neighbor)){
                    nbNeighborAlive++;
                }else{
                    candidate.add(neighbor);
                }
            }
            if (nbNeighborAlive == 2 || nbNeighborAlive == 3){
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

}
