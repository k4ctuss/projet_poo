package conway;

import core.AutomateSimulator;
import core.Cell;
import core.AutomateCellEvent;
import gui.GUISimulator;
import java.awt.Color;
import java.util.Set;

/**
 * Classe représentant le simulateur du jeu de la vie de Conway
 * Elle implémente l'interface Simulable pour permettre l'interaction avec l'interface graphique
 * Elle contient une grille de cellules et une référence à l'interface graphique
 * Elle permet de passer à l'étape suivante et de redémarrer la simulation
 * Dessiner consiste à dessiner toutes les cellules vivantes sur la grille
 */
public class ConwaySimulator extends AutomateSimulator {

    /**
     * Constructeur du simulateur de Conway
     * @param cellSize taille en pixels d'une cellule
     * @param gui référence à l'interface graphique
     * @param nbCellWidth nombre de cellules en largeur de la futur grille
     * @param nbCellHeight nombre de cellules en hauteur de la futur grille
     * @param initialCells ensemble des cellules initialement vivantes
     */
    public ConwaySimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initialCells){

        super(cellSize, gui, new ConwayGrid(nbCellWidth,nbCellHeight, initialCells));
    }

    /**
     * Crée la palette de couleurs pour Conway
     * Retourne une palette avec une seule couleur (blanc pour vivant)
     * @param numberStates nombre d'états (toujours 1 pour Conway)
     * @return tableau contenant une couleur blanche
     */
    @Override
    protected Color[] createPalette(int numberStates) {
        return new Color[] { Color.BLACK, Color.WHITE };
    }

}
