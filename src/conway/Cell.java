package conway;

import java.util.Objects;
import java.util.Set;

public class Cell {

    private final int x; // immuable pour ne pas changer les clés des hashset
    private final int y;

    // prevoir un set pour les cellules voisine mortes

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Cell c) {
            return c.x == x && c.y == y;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

}

