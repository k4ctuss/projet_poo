package immigration;

import core.Cell;
import core.MultiStateGrid;
import utils.ColorUtil;

import java.awt.*;
import java.util.*;

/**
 * Classe représentant la grille du jeu de l'immigration
 * Elle herite de la classe MultiStateGrid
 * Chaque cellule peut être dans un état de 0 à numberStates-1, chaque état
 * est représenté par une couleur différente
 * Les règles de transition d'état sont les suivantes :
 * si une cellule a au moins 3 voisins dans l'état suivant, elle passe à cet état
 * sinon elle conserve son état actuel
 * La grille gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public class ImmigrationGrid extends MultiStateGrid {

    /**
     * Constructeur de la grille de l'immigration, appel le constucteur parent
		 * @param nbCellWidth nombre de cellules en largeur de la grille
		 * @param nbCellHeight nombre de cellules en hauteur de la grille
		 * @param initialCell ensemble des cellules initialement vivantes
		 * @param numberStates nombre d'états possibles pour les cellules
		 * @param initialStateForCell map des cellules initiales avec leur état associé
     */
    public ImmigrationGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCell, int numberStates, HashMap<Cell, Integer> initialStateForCell){
        super(nbCellWidth, nbCellHeight, initialCell, numberStates, initialStateForCell);
    }

    @Override
    protected Color[] createPalette(int numberStates) {
        return ColorUtil.whiteToBlack(numberStates);
    }

    /**
		 * Passe à l'état suivant dans le jeu
		 * Pour chaque cellule vivante, on compte le nombre de voisins dans l'état suivant
		 * Si ce nombre est supérieur ou égal à 3, la cellule passe à l'état suivant
		 * Sinon, elle conserve son état actuel
		 * Ensuite, on vérifie les cellules mortes voisines des cellules vivantes
		 * Si une cellule morte a au moins 3 voisins dans l'état 1, elle devient vivante dans cet état
		 * 
		 */
    @Override
    public void nextStep(){
        Set<Cell> candidates = new HashSet<>();
        for(Cell c : currAlive){
            int currState = currStateCell.get(c);
            int nextState = (currState+1)%numberStates;
            int nbNeighborNextState = 0;
            for(Cell neighbor : getNeighbor(c)){
                if(currStateCell.getOrDefault(neighbor, 0)==nextState){
                        nbNeighborNextState++;
                }

                if(!isAlive(neighbor))candidates.add(neighbor);
            }

            if(nbNeighborNextState>=3){
                if(nextState!=0) {
                    nextStateCell.put(c, nextState);
                    c.setColor(palette[nextState]);
                    nextAlive.add(c);
                }
            }else{
                nextStateCell.put(c,currState);
                nextAlive.add(c);
            }
        }

        int targetState = 1;
        for(Cell c : candidates){
            int nbNeighborNextState = 0;
            for(Cell neighbor : getNeighbor(c)){
                if (currStateCell.getOrDefault(neighbor,0) == targetState){
                    nbNeighborNextState++;
                }
            }

            if (nbNeighborNextState>=3){
                nextStateCell.put(c,targetState);
                c.setColor(palette[targetState]);
                nextAlive.add(c);
            }
        }

        applyNextState();
    }


}
