import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;



public class Mine {

    
    private Image mine, mine2;
    private int BLOCK_SIZE = 24;
    ArrayList<Point> site;
    ArrayList<Point> setMine;
    private int flag = 0;
    public Mine() {
        mine = new ImageIcon("images/Mine/mine.png").getImage();
        mine2 = new ImageIcon("images/Mine/mine2.png").getImage();
        site = new ArrayList<Point>();
        setMine = new ArrayList<Point>();
    }

    public void drawMine(Graphics2D g2d) {
        for (Point now : site) {
            g2d.drawImage(mine, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
        }
    }

    public void drawsetMine(Graphics2D g2d) {
        for (Point now : setMine) {
            if (flag == 0)
                g2d.drawImage(mine, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
            else 
                g2d.drawImage(mine2, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
        }
        flag ^= 1;
    }
    
    public boolean eat(int tmpX, int tmpY) {
        if (site.contains(new Point(tmpX, tmpY))) {
            site.remove(new Point(tmpX, tmpY));
            return true;
        }
        else   
            return false;
    }

    public boolean eatMine(int tmpX, int tmpY) {
        if (setMine.contains(new Point(tmpX, tmpY))) {
            setMine.remove(new Point(tmpX, tmpY));
            return true;
        }
        else {
            return false;
        }
    }
    public void addMine(int x, int y) {
        setMine.add(new Point(x, y));
    }

    public void addItem(int x, int y) {
        site.add(new Point(x, y));
    }
}