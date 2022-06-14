import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Item { 


    private Cherry cherry;
    private Apple apple;   
    private Blood blood;
    private Ice ice;
    private Mine mine;
    private int generateTime;
    private int [][]map;
    private int [][]occ = new int[30][30];
    public Item(int map[][]) {
        ice = new Ice();
        apple = new Apple(); 
        blood = new Blood();
        cherry = new Cherry();
        mine = new Mine();
        ice.addItem(5, 12);
        apple.addItem(20, 12);
        blood.addItem(5, 16);
        cherry.addItem(20, 16);
        mine.addItem(20, 17);
        generateTime = 0;
        this.map = map;
        occ[5][12] = 1;
        occ[20][12] = 1;
        occ[5][16] = 1;
        occ[20][16] = 1;
        occ[20][17] = 1;
    }

    public void drawItem(Graphics2D g2d) {
        ice.drawIce(g2d);
        apple.drawApple(g2d);
        blood.drawBlood(g2d);
        cherry.drawCherry(g2d);
        mine.drawMine(g2d);
        mine.drawsetMine(g2d);
        generateTime = generateTime + 1;
        if (generateTime % 300 == 0 && generateTime != 0) {
            generateItem();
        }
    }

    public int getItem(int x, int y) {
        if (ice.eat(x, y)) {
            occ[x][y] = 0;
            return 1;
        }
        else if (apple.eat(x, y)) {
            occ[x][y] = 0;
            return 2;
        }
        else if (blood.eat(x, y)) {
            occ[x][y] = 0;
            return 3;
        }
        else if (cherry.eat(x, y)) {
            occ[x][y] = 0;
            return 4;
        }
        else if (mine.eat(x, y)) {
            occ[x][y] = 0;
            return 5;
        }
        else 
            return -1;
    }

    public boolean setMine(int x, int y) {
        if (occ[x][y] == 1) return false;
        occ[x][y] = 1;
        mine.addMine(x, y);
        return true;
    }

    public boolean bomb(int x, int y) {
        if (mine.eatMine(x, y) == true) {
            occ[x][y] = 0;
            return true;
        }
        return false;
    }

    public void drawBoob(Graphics2D g2d, int id, int x, int y) {
        mine.drawBoob(g2d, id, x, y);
    }

    private void generateItem() {
        Random r1 = new Random();
        int x, y;
        while (true) {
            x = r1.nextInt(26);
            y = r1.nextInt(26);
            if (map[y][x] == 0 && occ[x][y] == 0) {
                break;
            }
        }
        int newItem  = r1.nextInt(5);
        if (newItem == 0) {
            occ[x][y] = 1;
            ice.addItem(x, y);
        }
        else if (newItem == 1) {
            occ[x][y] = 1;
            apple.addItem(x, y);
        }        
        else if (newItem == 2) {
            occ[x][y] = 1;
            cherry.addItem(x, y);
        }
        else if (newItem == 3) {
            occ[x][y] = 1;
            mine.addItem(x, y);
        }
        else {
            occ[x][y] = 1;
            blood.addItem(x, y);
        }
    }        
}