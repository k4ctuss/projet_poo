package Ball;

import java.util.*;
import java.awt.Point;
import java.util.stream.Collectors;

public class Balls {

    private final List<Ball> initials;
    private final List<Ball> currents;

    public Balls(List<Point> seed){
        this.initials = new ArrayList<>(seed.size());
        this.currents = new ArrayList<>(seed.size());
        for(Point p : seed){
            Ball b  = new Ball(p); // copie pour la composition
            this.initials.add(new Ball(b)); // copie de b pour garder l'état initial
            this.currents.add(b);
        }
    }

    public List<int[]> getListCoordinate(){
        return currents.stream().map(ball -> new int[]{ball.getX(), ball.getY()}).toList();
    }


    public void step(int width, int height, int radius){
        for(Ball b: currents){
            b.update(width, height, radius);
        }
    }
    public void reInit(){
        for(int i = 0; i < currents.size(); i++){
            currents.get(i).reset(initials.get(i));
        }
    }

    @Override
    public String toString(){
        return currents.stream()
                .map(ball -> "x:"+ ball.getX()+";y:"+ball.getY())
                .collect(Collectors.joining(", "));
    }

}
