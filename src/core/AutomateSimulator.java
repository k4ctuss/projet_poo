package core;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

/**
 * Classe représentant le simulateur d'un jeu d'automate cellulaire
 * Elle implémente l'interface Simulable pour permettre l'interaction avec l'interface graphique
 * Elle contient une grille de cellules et une référence à l'interface graphique
 * Elle permet de passer à l'étape suivante et de redémarrer la simulation
 * Dessiner consiste à dessiner toutes les cellules vivantes sur la grille
 */
public abstract class AutomateSimulator implements Simulable{

    private final Grid grid;
    private final GUISimulator gui;
    private final int cellSize;

    /**
     * Constructeur du simulateur de Conway
     * @param cellSize taille en pixels d'une cellule
     * @param gui référence à l'interface graphique
     * @param grid refence un grille de cellules
     * @throws IllegalArgumentException si la taille des cellules est inférieure ou égale à 0
     */
    public AutomateSimulator(int cellSize, GUISimulator gui, Grid grid){
        if(cellSize <= 0){
            throw new IllegalArgumentException("Cell size must be strictly positive.");
        }
        this.cellSize = cellSize;
        this.gui = gui;
        this.gui.setSimulable(this);
        this.grid = grid;

        restart();
    }


    private void draw(){
        gui.reset();

        for(Cell c : grid.getCurrAlive()){
            gui.addGraphicalElement(new Rectangle(c.getX()*cellSize, c.getY()*cellSize, c.getColor(), c.getColor(), cellSize, cellSize));
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
