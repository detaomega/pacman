import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ice {

    private Image ice;
    public int x = 24, y = 24; 
    public boolean isfreeze = false;
    

    public Ice() {
        ice = new ImageIcon("images/Ice/ice.png").getImage();
    }
    
    public boolean freeze() {
        return isfreeze;
    }

    public void drawIce(Graphics2D g2d) {
        g2d.drawImage(ice, x, y, null);
    }


}