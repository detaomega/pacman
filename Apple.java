import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

public class Apple {

    private Image apple;
    private int BLOCK_SIZE = 24;
    ArrayList<Point> site;
    public Apple() {
        apple = new ImageIcon("images/Apple/apple.png").getImage();
        site = new ArrayList<Point>();
    }

    public void drawApple(Graphics2D g2d) {
        for (Point now : site) {
            g2d.drawImage(apple, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
        }
    }

    public boolean eat(int tmpX, int tmpY) {
        if (site.contains(new Point(tmpX, tmpY))) {
            site.remove(new Point(tmpX, tmpY));
            return true;
        }
        else   
            return false;
    }

    public void addItem(int x, int y) {
        site.add(new Point(x, y));
    }
}