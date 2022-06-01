import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cherry {

    private Image cherry;
    private int BLOCK_SIZE = 24;
    public int x, y;

    public Cherry(int x, int y) {
        cherry = new ImageIcon("images/Cherry/cherry.png").getImage();
        this.x = x;
        this.y = y; 
    }

    public void drawCherry(Graphics2D g2d) {
        g2d.drawImage(cherry, x * BLOCK_SIZE, y * BLOCK_SIZE, null);
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
}