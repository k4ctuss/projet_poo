package conway;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.Set;

public class ConwaySimulator implements Simulable {

    private final ConwayGrid grid;
    private final GUISimulator gui;
    private final int cellSize;

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
