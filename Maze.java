import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Maze {

    private Color dotColor = new Color(192, 192, 0);
    private Color mazeColor = new Color(5, 100, 5);
    private int BLOCK_SIZE = 24, N_BLOCKS;
    public int []data = new int[30 * 30];
    
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
    }

    public void drawMaze(Graphics2D g2d) {

        int i = 0;
        for (int y = 0; y < N_BLOCKS * BLOCK_SIZE; y += BLOCK_SIZE) {
            for (int x = 0; x < N_BLOCKS * BLOCK_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));

                if ((data[i] & 1) != 0) { // 左邊界
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 2) != 0) { // 上邊界
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((data[i] & 4) != 0) { // 右邊界
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 8) != 0) { // 下邊界
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((data[i] & 16) != 0) { // 果實
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                i++;
            }
        }
    }
}