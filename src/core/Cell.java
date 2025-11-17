package core;

import java.util.Objects;

/**
 * Représente une cellule vivante dans un automate cellulaire
 * une cellule possede des coordonnées x et y et un état entier
 * Deux cellules sont égales si elles ont les mêmes coordonnées
 * Le hashcode est basé sur les coordonnées de la cellule. l'état peut changer sans affecter le hashcode
 */
public class Cell {

    private final int x; // immuable pour ne pas changer les clés des hashset
    private final int y;
    private int state;   // état de la cellule (0=mort, 1+=vivant)

    /**
     * Constructeur d'une cellule avec état par défaut 0
     * @param x coordonnée x de la cellule
     * @param y coordonnée y de la cellule
     */
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.state = 0;
    }

    /**
     * Constructeur d'une cellule avec état spécifié
     * @param x coordonnée x de la cellule
     * @param y coordonnée y de la cellule
     * @param state état initial de la cellule
     */
    public Cell(int x, int y, int state){
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state){
        this.state = state;
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

