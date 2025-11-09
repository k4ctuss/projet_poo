import java.awt.*;

public class Ball {

    // coordonnée de la balle
    private int x;
    private int y;
    // vitesse verticale
    private int vx = 10;
    // vitesse horizontale
    private int vy = 10;

    public Ball(Ball other){
        this(other.x, other.y);
        this.vx = other.vx;
        this.vy = other.vy;
    }
    public Ball(Point point){
        this(point.x, point.y);
    }
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

    public void reset(Ball other){
        x = other.x;
        y = other.y;
        vx = other.vx;
        vy = other.vy;
    }

}
