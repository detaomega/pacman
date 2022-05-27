import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class DrawPacman {

    private Image [] pacmanup = new Image[4];
    private Image [] pacmandown = new Image[4];
    private Image [] pacmanleft = new Image[4];
    private Image [] pacmanright = new Image[4];
    public int view_x, view_y, pacman_x, pacman_y;
    private int control = 0, current_image[] = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1}, show = 0;

    public void loadImages(String s) {
        // pacman = new ImageIcon("images/" + s + "/pacman.png").getImage();
        pacmanup[0] = new ImageIcon("images/" + s + "/up.png").getImage();
        pacmanup[1] = new ImageIcon("images/" + s + "/up1.png").getImage();
        pacmanup[2] = new ImageIcon("images/" + s + "/up2.png").getImage();
        pacmanup[3] = new ImageIcon("images/" + s + "/pacman.png").getImage();
        pacmanleft[0] = new ImageIcon("images/" + s + "/left.png").getImage();
        pacmanleft[1] = new ImageIcon("images/" + s + "/left1.png").getImage();
        pacmanleft[2] = new ImageIcon("images/" + s + "/left2.png").getImage();
        pacmanleft[3] = new ImageIcon("images/" + s + "/pacman.png").getImage();
        pacmandown[0] = new ImageIcon("images/" + s + "/down.png").getImage();
        pacmandown[1] = new ImageIcon("images/" + s + "/down1.png").getImage();
        pacmandown[2] = new ImageIcon("images/" + s + "/down2.png").getImage();
        pacmandown[3] = new ImageIcon("images/" + s + "/pacman.png").getImage();
        pacmanright[0] = new ImageIcon("images/" + s + "/right.png").getImage();
        pacmanright[1] = new ImageIcon("images/" + s + "/right1.png").getImage();
        pacmanright[2] = new ImageIcon("images/" + s + "/right2.png").getImage();
        pacmanright[3] = new ImageIcon("images/" + s + "/pacman.png").getImage();
    }

    
    public void drawPacman(Graphics2D g2d) {
        control = (control + 1) % 24;
        show = current_image[control];
        System.out.println(9);
        if (view_x == -1 && view_y == 0) {
            drawPacnanLeft(g2d);

        } 
        else if (view_x == 1 && view_y == 0) {
            drawPacmanRight(g2d);
        } 
        else if (view_x == 0 && view_y == -1) {
            drawPacmanUp(g2d);
        } 
        else if (view_x == 0 && view_y == 1){
            drawPacmanDown(g2d);
        }
        
    }
    
    private void drawPacmanUp(Graphics2D g2d) {
        g2d.drawImage(pacmanup[show], pacman_x, pacman_y, null);    
    
    }

    private void drawPacmanDown(Graphics2D g2d) {
        g2d.drawImage(pacmandown[show], pacman_x, pacman_y, null);
        
    }

    private void drawPacnanLeft(Graphics2D g2d) {
        
        g2d.drawImage(pacmanleft[show], pacman_x, pacman_y, null);
        
    }

    private void drawPacmanRight(Graphics2D g2d) {
        g2d.drawImage(pacmanright[show], pacman_x , pacman_y , null);
    }

}