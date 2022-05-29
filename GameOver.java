import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameOver {

    private Image pic;
    public GameOver() {
        pic = new ImageIcon("images/gameover.jpeg").getImage();
    }

    public void showImage(Graphics g2d) {
        g2d.drawImage(pic, 0, 300, null);
    }
}