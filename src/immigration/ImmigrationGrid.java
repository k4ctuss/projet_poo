package immigration;

import core.Cell;
import core.CellGrid;
import core.Grid;
import utils.ColorUtil;

import java.util.*;

/**
 * Classe représentant la grille du jeu de l'immigration
 * Elle hérite de la classe Grid
 * Chaque cellule peut être dans un état de 0 à numberStates-1
 * Les règles de transition d'état sont les suivantes :
 * si une cellule a au moins 3 voisins dans l'état suivant, elle passe à cet état
 * sinon elle conserve son état actuel
 * La grille gère également le wrapping des cellules aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public class ImmigrationGrid extends CellGrid {

    /**
     * Constructeur de la grille de l'immigration, appel le constucteur parent
     * @param nbCellWidth nombre de cellules en largeur de la grille
     * @param nbCellHeight nombre de cellules en hauteur de la grille
     * @param initialCell ensemble des cellules initialement vivantes
     * @param numberStates nombre d'états possibles pour les cellules
     */
    public ImmigrationGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCell, int numberStates){
        super(nbCellWidth, nbCellHeight, initialCell, numberStates);
    }

    /**
		 * Passe à l'état suivant dans le jeu
		 * Pour chaque cellule vivante, on compte le nombre de voisins dans l'état suivant
		 * Si ce nombre est supérieur ou égal à 3, la cellule passe à l'état suivant
		 * Sinon, elle conserve son état actuel
		 * Ensuite, on vérifie les cellules mortes voisines des cellules vivantes
		 * Si une cellule morte a au moins 3 voisins dans l'état 1, elle devient vivante dans cet état
		 */
    @Override
    public void nextStep(){
        buildSnapshot();  // Crée le snapshot une fois au début
        
        Set<Cell> candidates = new HashSet<>();
        for(Iterator<Cell> it = currAlive.iterator(); it.hasNext(); ){
            Cell c = it.next();
            int currState = getStateSnapshot(c);
            int nextState = (currState + 1) % numberStates;
            int nbNeighborNextState = 0;
            
            for(Cell neighbor : getNeighbor(c)){
                if(getStateSnapshot(neighbor) == nextState){
                    nbNeighborNextState++;
                }
                if(!snapshotState.containsKey(neighbor)) {
                    candidates.add(neighbor);
                }
            }

            if(nbNeighborNextState >= 3){
                if(nextState != 0) {
                    c.setState(nextState);
                }else{
                    it.remove(); // La cellule meurt, on la retire de currAlive
                }
            }
        }

        int targetState = 1;
        for(Cell c : candidates){
            int nbNeighborState1 = 0;
            for(Cell neighbor : getNeighbor(c)){
                if(getStateSnapshot(neighbor) == targetState){
                    nbNeighborState1++;
                }
            }

            if(nbNeighborState1 >= 3){
                c.setState(targetState);
                currAlive.add(c);
            }
        }
    }


}
