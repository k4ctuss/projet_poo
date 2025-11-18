package core;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

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
    protected final Color[] palette;  // Palette de couleurs pour visualisation

    /**
     * Constructeur du simulateur d'automate cellulaire
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
        this.palette = createPalette(grid.getNumberStates());

        restart();
    }

    /**
     * Crée la palette de couleurs pour visualisation
     * À surcharger par les sous-classes pour des palettes spécifiques
     * @param numberStates nombre d'états à représenter
     * @return tableau de couleurs
     */
    protected abstract Color[] createPalette(int numberStates);


    private void draw(){
        gui.reset();

        for(Cell c : grid.getCurrAlive()){
            int state = c.getState();
            Color color = palette[state];
            gui.addGraphicalElement(new Rectangle(c.getX()*cellSize+cellSize/2, c.getY()*cellSize+cellSize/2, color, color, cellSize, cellSize));
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
