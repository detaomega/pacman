import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ice {

    private Image ice;

    public int x = 24, y = 24;
    private int BLOCK_SIZE = 24;
    public Ice(int x, int y) {
        ice = new ImageIcon("images/Ice/ice.png").getImage();
        this.x = x;
        this.y = y;
    }
    
    public void drawIce(Graphics2D g2d) {
        g2d.drawImage(ice, x * BLOCK_SIZE, y * BLOCK_SIZE, null);
    }

    public boolean eat(int tmpX, int tmpY) {
        if (tmpX == x && tmpY == y) {
            x = -1;
            y = -1;
            return true;
        }
        else {
            return false;
        }
    }
}