package schelling;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;
import utils.ColorUtil;

import java.awt.Color;
import java.util.Set;

/**
 * Classe représentant le simulateur du modèle de Schelling
 * Elle hérite de la classe AutomateSimulator et initialise une grille de Schelling
 * avec les paramètres spécifiés
 * 
 */
public class SchellingSimulator extends AutomateSimulator {

		/**
		 * Constructeur du simulateur de Schelling
		 * @param cellSize taille en pixels d'une cellule
		 * @param gui référence à l'interface graphique
		 * @param nbCellWidth nombre de cellules en largeur de la futur grille
		 * @param nbCellHeight nombre de cellules en hauteur de la futur grille
		 * @param initialCells ensemble des cellules initialement occupées
		 * @param numberState nombre d'états possibles pour les cellules (couleurs des familles)
		 * @param originVacantHabitation ensemble des cellules initialement vacantes
		 * @param seuil seuil de dissatisfaction
		 */
    public SchellingSimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initialCells,
                              int numberState, Set<Cell> originVacantHabitation, int seuil) {
        super(cellSize, gui, new SchellingGrid(nbCellWidth, nbCellHeight, initialCells, numberState,
                originVacantHabitation, seuil));
    }

	/**
	 * Crée une palette de couleurs pastel pour représenter les différents états des cellules
	 * @param numberStates nombre d'états possibles pour les cellules
	 * @return un tableau de couleurs représentant la palette
	 * @see utils.ColorUtil#pastelHSB(int)
	 */
	@Override
	protected Color[] createPalette(int numberStates) {
		return ColorUtil.pastelHSB(numberStates);
	}
}
