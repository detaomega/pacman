import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Score {
    private final Font smallFont = new Font("Silom", Font.BOLD, 18);
    private Image pacman;
    private Image mine;
    public int score, life, mine_number, highest = 0;
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
        g.drawString(s, 150, Y + 20);
        for (int i = 0; i < life; i++) {
            g.drawImage(pacman, i * 28 + 8, Y + 3, null);
        }
        for (int i = 0; i < mine_number; i++) {
            g.drawImage(mine, i * 28 + 340, Y + 3, null);
        }
    }

    public void drawHighestScore(Graphics2D g) {
        Font scoreFont = new Font("Silom", Font.BOLD, 20);
        highest = Math.max(highest, score);
        g.setFont(scoreFont);
        g.setColor(new Color(219, 219, 249)); 
        String s = "Highest Score: " + highest;  
        g.drawString(s, 210, 680);
    }
    
    public void highestScore(int x) {
        highest = x;
    }
}