import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Score {
    private final Font smallFont = new Font("Silom", Font.BOLD, 18);
    private Image pacman;
    private Image mine;
    public int score, life, mine_number;
    public Score(String s) {
        pacman = new ImageIcon("images/" + s + "/left4.png").getImage();
        mine = new ImageIcon("images/Mine/mine.png").getImage();
        score = 0;
        life = 3;
        mine_number = 1;
    }

    public void drawScore(Graphics2D g, int Y) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255)); 
        s = "Score: " + score;  
        if (mine_number > 2) mine_number = 2;
        g.drawString(s, 150, Y + 20);
        for (int i = 0; i < life; i++) {
            g.drawImage(pacman, i * 28 + 8, Y + 3, null);
        }
        for (int i = 0; i < mine_number; i++) {
            g.drawImage(mine, i * 28 + 250, Y + 3, null);
        }
    }
}