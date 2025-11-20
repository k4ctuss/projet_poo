package core;

import java.util.HashSet;
import java.util.*;

/**
 * Classe représentant une grille 2D qui contient des élément
 * Elle gère également le wrapping des éléments aux bords de la grille
 * Elle permet de redémarrer la grille à son état initial
 */
public abstract class Grid<T> {
    private final int width;
    private final int height;
    protected final Set<T> origin;
    protected final Set<T> currAlive;

    /**
     * Constructeur de la grille d'automate cellulaire
     * @param width nombre de cellules en largeur de la grille
     * @param height nombre de cellules en hauteur de la grille
     * @param initialCollection ensemble de type T initialement vivantes
     * @throws IllegalArgumentException si la largeur ou la hauteur est inférieure ou égale à 0
     *
     */
    public Grid(int width, int height, Set<T> initialCollection){
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.width = width;
        this.height = height;
        this.origin = Set.copyOf(initialCollection);
        this.currAlive = new HashSet<>();
    }

    /**
     * Accesseur des cellules actuellement vivantes
     * @return un ensemble non modifiable des cellules actuellement vivantes
     */
    public Set<T> getCurrAlive() {
        return Collections.unmodifiableSet(currAlive);
    }

    protected int wrapX(int x){
        int r = x%width;
        return r<0? r+width: r;
    }

    protected int wrapY(int y){
        int r = y%height;
        return r<0? r+height: r;
    }

    /**
     * Passe à l'étape suivante de la simulation en appliquant les règles du jeu de l'automate simulé
     */
    public abstract void nextStep();

    protected void clear(){
        this.currAlive.clear();
    }

    public void restart(){
        clear();
    }


}

