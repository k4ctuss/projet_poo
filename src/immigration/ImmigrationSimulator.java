package immigration;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;

import java.util.HashMap;
import java.util.Set;

/**
 * Classe représentant le simulateur du jeu de l'immigration
 * Elle hérite de la class AutomateSimulator pour gérer la simulation:
 * - le passage à l'état suivant
 * - le redémarrage de la simulation
 * - le dessin de la grille
 */
public class ImmigrationSimulator extends AutomateSimulator {

		/**
		 * Constructeur du simulateur de l'immigration
         * @param cellSize taille en pixels d'une cellule
         * @param gui référence à l'interface graphique
         * @param nbCellWidth nombre de cellules en largeur de la futur grille
         * @param nbCellHeight nombre de cellules en hauteur de la futur grille
         * @param initialCells ensemble des cellules initialement vivantes
		 * @param numberState nombre d'états possibles pour les cellules
		 * @param initialStateForCell map des cellules initiales avec leur état associé
		 */
    public ImmigrationSimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initialCells,
                                int numberState, HashMap<Cell, Integer> initialStateForCell){
        super(cellSize, gui, new ImmigrationGrid(nbCellWidth, nbCellHeight, initialCells, numberState, initialStateForCell));
    }
}
