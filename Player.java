import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Player {

    private Image [] pacmanup = new Image[5];
    private Image [] pacmandown = new Image[5];
    private Image [] pacmanleft = new Image[5];
    private Image [] pacmanright = new Image[5];
    public int view_x, view_y, pacman_x, pacman_y, speed;
    private int control = 0, current_image[] = {0, 1, 2, 3, 4, 3, 2, 1}, show = 0;

    public Player(String s) {
        pacmanup[0] = new ImageIcon("images/" + s + "/up1.png").getImage();
        pacmanup[1] = new ImageIcon("images/" + s + "/up1.png").getImage();
        pacmanup[2] = new ImageIcon("images/" + s + "/up2.png").getImage();
        pacmanup[3] = new ImageIcon("images/" + s + "/up3.png").getImage();
        pacmanup[4] = new ImageIcon("images/" + s + "/up4.png").getImage();
        pacmanleft[0] = new ImageIcon("images/" + s + "/left.png").getImage();
        pacmanleft[1] = new ImageIcon("images/" + s + "/left1.png").getImage();
        pacmanleft[2] = new ImageIcon("images/" + s + "/left2.png").getImage();
        pacmanleft[3] = new ImageIcon("images/" + s + "/left3.png").getImage();
        pacmanleft[4] = new ImageIcon("images/" + s + "/left4.png").getImage();
        pacmandown[0] = new ImageIcon("images/" + s + "/down1.png").getImage();
        pacmandown[1] = new ImageIcon("images/" + s + "/down1.png").getImage();
        pacmandown[2] = new ImageIcon("images/" + s + "/down2.png").getImage();
        pacmandown[3] = new ImageIcon("images/" + s + "/down3.png").getImage();
        pacmandown[4] = new ImageIcon("images/" + s + "/down4.png").getImage();
        pacmanright[0] = new ImageIcon("images/" + s + "/right.png").getImage();
        pacmanright[1] = new ImageIcon("images/" + s + "/right1.png").getImage();
        pacmanright[2] = new ImageIcon("images/" + s + "/right2.png").getImage();
        pacmanright[3] = new ImageIcon("images/" + s + "/right3.png").getImage();
        pacmanright[4] = new ImageIcon("images/" + s + "/right4.png").getImage();
    }

    public void drawPacman(Graphics2D g2d) {
        control = (control + 1) % 8;
        show = current_image[control];
        if (view_x == -1 && view_y == 0) {
            g2d.drawImage(pacmanleft[show], pacman_x, pacman_y, null);
        } 
        else if (view_x == 1 && view_y == 0) {
            g2d.drawImage(pacmanright[show], pacman_x , pacman_y , null);
        } 
        else if (view_x == 0 && view_y == -1) {
            g2d.drawImage(pacmanup[show], pacman_x, pacman_y, null); 
        } 
        else if (view_x == 0 && view_y == 1) {
            g2d.drawImage(pacmandown[show], pacman_x, pacman_y, null);
        }
        else if (view_x == 0 && view_y == 0) {
            g2d.drawImage(pacmanleft[0], pacman_x, pacman_y, null);
        }
    }

    public void change(int newSpeed) {
        pacman_x = pacman_x / (newSpeed) * newSpeed;
        pacman_y = pacman_y / (newSpeed) * newSpeed;
        speed = newSpeed;
    }
 
}