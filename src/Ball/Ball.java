package Ball;

import java.awt.*;

/**
 * Classe représentant une balle rebondissant dans une fenêtre
 * Elle a une position (x, y) et une vitesse (vx, vy)
 * La méthode update permet de mettre à jour la position en fonction de la vitesse
 * et de gérer les rebonds contre les bords de la fenêtre
 */
public class Ball {

    // coordonnée de la balle
    private int x;
    private int y;
    // vitesse verticale
    private int vx = 10;
    // vitesse horizontale
    private int vy = 10;

    /**
     * Constructeur d'une balle à une position donnée
     * @param other balle à copier
     */
    public Ball(Ball other){
        this(other.x, other.y);
        this.vx = other.vx;
        this.vy = other.vy;
    }
    /**
     * Constructeur d'une balle à une position donnée
     * @param point position initiale de la balle
     */
    public Ball(Point point){
        this(point.x, point.y);
    }

    /**
     * Constructeur d'une balle à une position donnée
     * @param x position x initiale
     * @param y position y initiale
     */
    public Ball(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Met à jour la position de la balle en fonction de sa vitesse
     * et gère les rebonds contre les bords de la fenêtre
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     * @param radius rayon de la balle
     */
    public void update(int width, int height, int radius){
        int nextX = x+vx, nextY = y+vy;

        //rebond horizontal
        if(nextX < radius){
            vx = -vx;
            nextX = -nextX; // on le remet de autant qu'il est allé en négatif
        }else if(nextX > width-radius){
            vx = -vx;
            nextX -= nextX%(width-radius);
        }

        // rebond vertical
        if(nextY < radius){
            vy = -vy;
            nextY = -nextY; // on le remet de autant qu'il est allé en négatif
        }else if(nextY > height-radius){
            vy = -vy;
            nextY -= nextY%(height-radius);
        }

        x = nextX;
        y = nextY;
    }

    /**
     * Réinitialise la balle à l'état d'une autre balle
     * @param other balle dont on copie l'état
     */
    public void reset(Ball other){
        x = other.x;
        y = other.y;
        vx = other.vx;
        vy = other.vy;
    }

}
