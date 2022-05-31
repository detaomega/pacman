import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Apple {

    private Image apple;
    public int x = 24, y = 24;

    

    public Apple() {
        apple = new ImageIcon("images/Apple/apple.png").getImage();
    }
    


    public void drawApple(Graphics2D g2d) {
        g2d.drawImage(apple, x, y, null);
    }


}