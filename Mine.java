import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;



public class Mine {

    
    private Image mine, mine2;
    private Image [] boob = new Image[3];
    private int BLOCK_SIZE = 24;
    ArrayList<Point> site;
    ArrayList<Point> setMine;
    private int flag = 0;
    public Mine() {
        mine = new ImageIcon("images/Mine/mine.png").getImage();
        mine2 = new ImageIcon("images/Mine/mine2.png").getImage();
        boob[0] = new ImageIcon("images/Mine/boom1.png").getImage();
        boob[1] = new ImageIcon("images/Mine/boom2.png").getImage();
        boob[2] = new ImageIcon("images/Mine/boom3.png").getImage();
        site = new ArrayList<Point>();
        setMine = new ArrayList<Point>();
        site.clear();
        setMine.clear();
    }

    public void drawMine(Graphics2D g2d) {
        for (Point now : site) {
            g2d.drawImage(mine, now.x * BLOCK_SIZE, now.y * BLOCK_SIZE, null);
        }
    }


    public void drawBoob(Graphics2D g2d, int id, int x, int y) {
        g2d.drawImage(boob[id], x, y, null);
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