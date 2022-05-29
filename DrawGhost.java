import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawGhost {

    private Image []ghostleft; 
    private Image []ghostright; 
    private Image []ghostup;
    private Image []ghostdown;
    public int nextx, nexty, x, y, control = 0;
   
    public void loadImages(String s) {
        String Dir = "images/ghost" + s + "/" + s;
        ghostleft[0] = new ImageIcon(Dir + "1left.png").getImage();
        ghostleft[1] = new ImageIcon(Dir + "2left.png").getImage();

        ghostright[0] = new ImageIcon(Dir + "1right.png").getImage();
        ghostright[1] = new ImageIcon(Dir + "2right.png").getImage();

        ghostup[0] = new ImageIcon(Dir + "1up.png").getImage();
        ghostup[1] = new ImageIcon(Dir + "2up.png").getImage();

        ghostdown[0] = new ImageIcon(Dir + "1down.png").getImage();
        ghostdown[1] = new ImageIcon(Dir + "2down.png").getImage();
    }
    
    public DrawGhost() {
        ghostleft = new Image[2];
        ghostright = new Image[2];
        ghostup = new Image[2];
        ghostdown = new Image[2];
    }

    public void drawGhost(Graphics2D g2d) {
        if (nextx == -1 && nexty == 0)
            g2d.drawImage(ghostleft[control], x, y, null);
        else if (nextx == 0 && nexty == -1) 
            g2d.drawImage(ghostup[control], x, y, null);
        else if (nextx == 1 && nexty == 0)
            g2d.drawImage(ghostright[control], x, y, null);
        else
            g2d.drawImage(ghostdown[control], x, y, null);
        control = control ^ 1;
    }

}