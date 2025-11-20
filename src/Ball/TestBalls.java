package Ball;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test pour la classe Balls
 * Elle crée une collection de balles, affiche leur état initial,
 * effectue une mise à jour (translation), affiche l'état après mise à jour,
 * puis réinitialise les balles et affiche l'état après réinitialisation
 */
public class TestBalls {

    public static void main(String[] args) {
        List<Point> seed = new ArrayList<>();
        seed.add(new Point(10, 100));
        seed.add(new Point(20, 50));
        seed.add(new Point(30, 60));
        seed.add(new Point(2, 10));

        Balls balls = new Balls(seed, 1000, 1000, 0);
        System.out.println("Etat initial : \n "+balls.toString());

        int dx = 10, dy = 10;
        System.out.println("---- Transaltion de "+dx+" en x et "+dy+" en y. -----");
        balls.step();
        System.out.println("Etat après translation : \n "+balls.toString());
        System.out.println("---- Réinit -----");
        balls.reInit();
        System.out.println("Etat apres reinit : \n "+balls.toString());

    }
}
