package immigration;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;
import utils.ColorUtil;

import java.awt.Color;
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
		 */
    public ImmigrationSimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initialCells, int numberState){
        super(cellSize, gui, new ImmigrationGrid(nbCellWidth, nbCellHeight, initialCells, numberState));
    }

    /**
     * Crée la palette de couleurs pour Immigration (noir à blanc)
     * @param numberStates nombre d'états à représenter
     * @return tableau de couleurs de noir à blanc
     */
    @Override
    protected Color[] createPalette(int numberStates) {
        return ColorUtil.whiteToBlack(numberStates);
    }
}
