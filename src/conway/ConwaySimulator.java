package conway;

import core.AutomateSimulator;
import core.Cell;
import gui.GUISimulator;
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
     * @throws IllegalArgumentException si la taille des cellules est inférieure ou égale à 0
     */
    public ConwaySimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initialCells){

        super(cellSize, gui, new ConwayGrid(nbCellWidth,nbCellHeight, initialCells));
    }






}
