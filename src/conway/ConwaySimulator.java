package conway;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.Set;

/**
 * Classe représentant le simulateur du jeu de la vie de Conway
 * Elle implémente l'interface Simulable pour permettre l'interaction avec l'interface graphique
 * Elle contient une grille de cellules et une référence à l'interface graphique
 * Elle permet de passer à l'étape suivante et de redémarrer la simulation
 * Dessiner consiste à dessiner toutes les cellules vivantes sur la grille
 */
public class ConwaySimulator implements Simulable {

    private final ConwayGrid grid;
    private final GUISimulator gui;
    private final int cellSize;

    /**
     * Constructeur du simulateur de Conway
     * @param cellSize taille en pixels d'une cellule
     * @param gui référence à l'interface graphique
     * @param nbCellWidth nombre de cellules en largeur de la futur grille
     * @param nbCellHeight nombre de cellules en hauteur de la futur grille
     * @param initalCells ensemble des cellules initialement vivantes
     * @throws IllegalArgumentException si la taille des cellules est inférieure ou égale à 0
     */
    public ConwaySimulator(int cellSize, GUISimulator gui, int nbCellWidth, int nbCellHeight, Set<Cell> initalCells){
        if(cellSize <= 0){
            throw new IllegalArgumentException("Cell size must be strictly positive.");
        }
        this.cellSize = cellSize;
        this.gui = gui;
        this.gui.setSimulable(this);
        this.grid = new ConwayGrid(nbCellWidth,nbCellHeight, initalCells);

        draw();
    }


    private void draw(){
        gui.reset();

        for(Cell c : grid.getCurrAlive()){
            gui.addGraphicalElement(new Rectangle(c.getX()*cellSize, c.getY()*cellSize, Color.WHITE, Color.WHITE, cellSize, cellSize));
        }

    }
    @Override
    public void next(){
        grid.nextStep();
        draw();
    }

    @Override
    public void restart(){
        grid.restart();
        draw();
    }



}
