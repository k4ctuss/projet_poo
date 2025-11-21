package Ball;

import core.Stepable;
import java.util.*;
import java.awt.Point;
import java.util.stream.Collectors;

/**
 * Classe représentant une collection de balles rebondissant dans une fenêtre
 * Elle gère une liste de balles initiales et courantes
 * La méthode step met à jour la position de toutes les balles
 * La méthode restart réinitialise toutes les balles à leur état initial
 */
public class Balls implements Stepable {

    private final List<Ball> initials;
    private final List<Ball> currents;
    private final int ballRadius;

    private final int width, height;

    /**
     * Constructeur d'une collection de balles à partir d'une liste de positions initiales
     * @param seed liste des positions initiales des balles
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     * @param ballRadius rayon des balles
     */
    public Balls(List<Point> seed, int width, int height, int ballRadius){
        this.initials = new ArrayList<>(seed.size());
        this.currents = new ArrayList<>(seed.size());
        this.ballRadius = ballRadius;
        this.width = width;
        this.height = height;
        for(Point p : seed){
            Ball b  = new Ball(p); // copie pour la composition
            this.initials.add(new Ball(b)); // copie de b pour garder l'état initial
            this.currents.add(b);
        }
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public List<int[]> getListCoordinate(){
        return currents.stream().map(ball -> new int[]{ball.getX(), ball.getY()}).toList();
    }

    /**
     * Met à jour la position de toutes les balles
     */
    @Override
    public void step(){
        for(Ball b: currents){
            b.update(width, height, ballRadius);
        }
    }
    
    @Override
    public void restart(){
        for(int i = 0; i < currents.size(); i++){
            currents.get(i).reset(initials.get(i));
        }
    }
    /**
     * Retourne une représentation textuelle des positions des balles
     * @return chaîne de caractères représentant les positions des balles
     */
    @Override
    public String toString(){
        return currents.stream()
                .map(ball -> "x:"+ ball.getX()+";y:"+ball.getY())
                .collect(Collectors.joining(", "));
    }

}
