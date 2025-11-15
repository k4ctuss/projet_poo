package core;

import java.awt.*;
import java.util.Objects;

/**
 * Représente une cellule vivante dans le jeu de la vie de Conway
 * une cellule possede des coordonnées x et y
 * Deux cellules sont égales si elles ont les mêmes coordonnées
 * Le hashcode est basé sur les coordonnées de la cellule. sa couleur peut changer sans affecter le hashcode
 */
public class Cell {

    private final int x; // immuable pour ne pas changer les clés des hashset
    private final int y;

    private Color color;

    /**
     * Constructeur d'une cellule de couleur blanche par défaut
     * @param x coordonnée x de la cellule (doit être positive)
     * @param y coordonnée y de la cellule (doit être positive)
     */
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Deux cellules sont égales si elles ont les mêmes coordonnées
     * @return true si les cellules ont les mêmes coordonnées, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Cell c) {
            return c.x == x && c.y == y;
        }
        return false;
    }
    /**
     * Le hashcode est basé sur les coordonnées de la cellule
     * @return le hashcode de la cellule
     */
    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

}

