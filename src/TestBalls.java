import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestBalls {

    public static void main(String[] args) {
        List<Point> seed = new ArrayList<>();
        seed.add(new Point(10, 100));
        seed.add(new Point(20, 50));
        seed.add(new Point(30, 60));
        seed.add(new Point(2, 10));

        Balls balls = new Balls(seed);
        System.out.println("Etat initial : \n "+balls.toString());

        int dx = 10, dy = -5;
        System.out.println("---- Transaltion de "+dx+" en x et "+dy+" en y. -----");
        balls.translate(dx, dy);
        System.out.println("Etat après translation : \n "+balls.toString());
        System.out.println("---- Réinit -----");
        balls.reInit();
        System.out.println("Etat apres reinit : \n "+balls.toString());

    }
}
