import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

public class Cherry {

    private Image cherry;
    private int BLOCK_SIZE = 24;
    ArrayList<Point> site;
    public Cherry() {
        cherry = new ImageIcon("images/Cherry/cherry.png").getImage();
        site = new ArrayList<Point>();
    }

    public void drawCherry(Graphics2D g2d) {
        for (Point now : site) {
            g2d.drawImage(cherry, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
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