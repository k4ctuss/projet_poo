import java.util.*;
import java.awt.Point;
import java.util.stream.Collectors;

public class Balls {

    private final List<Point> initials;
    private final List<Point> currents;

    public Balls(List<Point> seed){
        this.initials = new ArrayList<>(seed.size());
        this.currents = new ArrayList<>(seed.size());
        for(Point p : seed){
            Point cp  = new Point(p); // copie pour la composition
            this.initials.add(new Point(cp)); // copie de cp pour garder l'état initial
            this.currents.add(cp);
        }
    }

    public List<Point> getCurrents() {
        return currents;
    }

    public void translate(int dx, int dy){
        for(Point p : currents){
            p.translate(dx, dy);
        }
    }

    public void reInit(){
        for(int i = 0; i < currents.size(); i++){
            currents.get(i).setLocation(initials.get(i));
        }
    }

    @Override
    public String toString(){
        return currents.stream()
                .map(point -> "x:"+ point.x+";y:"+point.y)
                .collect(Collectors.joining(", "));
    }

}
