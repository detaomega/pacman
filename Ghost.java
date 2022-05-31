import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ghost {

    private Image []ghostleft; 
    private Image []ghostright; 
    private Image []ghostup;
    private Image []ghostdown;  
    private Image []weak;
    private Image []weak2;
    private Image eye;
    public int nextx, nexty, x, y, control = 0, ori_x, ori_y, count = 0, speed;
    public int state = 0;


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

        weak[0] = new ImageIcon("images/ghostweak/ghost1.png").getImage();
        weak[1] = new ImageIcon("images/ghostweak/ghost2.png").getImage();
        weak2[0] = new ImageIcon("images/ghostweak/ghost3.png").getImage();
        weak2[1] = new ImageIcon("images/ghostweak/ghost4.png").getImage();
        eye = new ImageIcon("images/ghostEye/eye.png").getImage();
    
    }
    
    public Ghost() {
        weak = new Image[2];
        weak2 = new Image[2];
        ghostright = new Image[2];
        ghostup = new Image[2];
        ghostleft = new Image[2];
        ghostdown = new Image[2];
        ghostright = new Image[2];
    }
    private void timer() {
        count++;
        if (count >= 112) {
            if (count % 4 >= 2) state = 2;
            else state = 1;
        }
        if (count == 140) {
            state = 0;
            count = 0;
            change(3);
        }
    }


    public void drawGhost(Graphics2D g2d) {
        if (state == 1) {
            g2d.drawImage(weak[control], x, y, null);
            timer();
        }
        else if (state == 2) {
            g2d.drawImage(weak2[control], x, y, null);
            timer();
        }
        else if (nextx == -1 && nexty == 0)
            g2d.drawImage(ghostleft[control], x, y, null);
        else if (nextx == 0 && nexty == -1) 
            g2d.drawImage(ghostup[control], x, y, null);
        else if (nextx == 1 && nexty == 0)
            g2d.drawImage(ghostright[control], x, y, null);
        else
            g2d.drawImage(ghostdown[control], x, y, null);
        control = control ^ 1;
    }

    public void change(int newSpeed) {
        x = x / (newSpeed) * newSpeed;
        y = y / (newSpeed) * newSpeed;
        speed = newSpeed;
    }
    
    public void reset() {
        x = ori_x;
        y = ori_y;
        count = 0;
        state = 0;
        change(3);
    }
}