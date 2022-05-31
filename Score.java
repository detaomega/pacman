import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Score {
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private Image pacman;
    public int score, life;
    public Score(String s) {
        pacman = new ImageIcon("images/" + s + "/left4.png").getImage();
        score = -1;
        life = 3;
    }

    public void drawScore(Graphics2D g, int Y) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255)); 
        s = "Score: " + score;
        g.drawString(s, 150, Y + 20);
        for (int i = 0; i < life; i++) {
            g.drawImage(pacman, i * 28 + 8, Y + 3, null);
        }
    }
}