import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawGhost {

    private Image ghost_left, ghost_right;
    public int nextx, nexty, x, y;
   
    public void loadImages(String s) {
        String Dir = "images/ghost" + s + "/ghost" + s;
        ghost_left = new ImageIcon(Dir + "Left.jpeg").getImage();
        ghost_right = new ImageIcon(Dir + "Right.jpeg").getImage();
    }
    
    public void drawGhost(Graphics2D g2d) {
        if (nextx + nexty == -1)
            g2d.drawImage(ghost_left, x, y, null);
        else 
            g2d.drawImage(ghost_right, x, y, null);
    }

}