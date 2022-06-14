import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Maze {

    private Color dotColor = new Color(192, 192, 0);
    private Color player1Color = new Color(255, 254, 56);
    private Color player2Color = new Color(0, 255, 0);
    private Color mazeColor = new Color(5, 100, 5);
    private Color fenceColor = new Color(255, 160, 155);
    private int BLOCK_SIZE = 24, N_BLOCKS;
    public int []data = new int[30 * 30];
    private int count;
    private int doubleTime = 0;
    public int player;
    public boolean checkMaze() {
        for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            if ((data[i] & 48) != 0) {
                return false;
            }
        }

        return true;
    }

    public Maze(int N_BLOCKS) {
        this.N_BLOCKS = N_BLOCKS;
        player = 0;
        count = 0;
        doubleTime = 0;
    }

    public void drawMaze(Graphics2D g2d) {

        int i = 0;
        for (int y = 0; y < N_BLOCKS * BLOCK_SIZE; y += BLOCK_SIZE) {
            for (int x = 0; x < N_BLOCKS * BLOCK_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));

                if ((data[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((data[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 16) != 0) { 
                    if (player == 1)
                        g2d.setColor(player1Color);
                    else 
                        g2d.setColor(player2Color);
                    if (doubleTime > 0) {
                        if (doubleTime >= 30 || doubleTime % 6 <= 2)
                            g2d.fillOval(x + 9, y + 9, 6, 6);
                    }
                    else {
                        g2d.setColor(dotColor);
                        g2d.fillRect(x + 11, y + 11, 2, 2);
                    }
                }

                if ((data[i] & 32) != 0 && count % 6 <= 2) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x + 6, y + 6, 12, 12);
                }
                i++;
            }
        }
        g2d.setColor(fenceColor);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(12 * BLOCK_SIZE + 2, 11 * BLOCK_SIZE, 14 * BLOCK_SIZE - 2, 11 * BLOCK_SIZE);
        data[10 * N_BLOCKS + 12] |= 64;
        data[10 * N_BLOCKS + 13] |= 64;
        if (doubleTime > 0) {
            doubleTime--;
        }
        if (doubleTime == 0) player = 0;
        
        count++;
    }

    public void doubleScore(int PLAYER) {
        doubleTime = 150;
        player = PLAYER;
    }
}