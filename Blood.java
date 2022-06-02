import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Point;

public class Blood {

    private Image blood;
    private int BLOCK_SIZE = 24;
    public int x, y;
    ArrayList<Point> site;
    public Blood(int x, int y) {
        blood = new ImageIcon("images/" + "playerOne" + "/left4.png").getImage();
        site = new ArrayList<Point>();
        this.x = x;
        this.y = y; 
    }

    public void drawBlood(Graphics2D g2d) {
        g2d.drawImage(blood, x * BLOCK_SIZE, y * BLOCK_SIZE, null);
    }

    public boolean eat(int tmpX, int tmpY) {
        if (tmpX == x && tmpY == y) {
            x = -1;
            y = -1;
            return true;
        }
        else 
            return false;
    }

    public void addNewItem(int x, int y) {

    }
}