import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Item { 

    private Ice ice;
    private Apple apple;
    public Item() {
        ice = new Ice(5, 12);
        apple = new Apple(0, 5); 
        // blood = new Blood(1, 2);
    }

    public void drawItem(Graphics2D g2d) {
        ice.drawIce(g2d);
        apple.drawApple(g2d);
        // blood.drawBlood(g2d);
    }

    public int getItem(int x, int y) {
        if (ice.eat(x, y)) {
            return 1;
        }
        else if (apple.eat(x, y)) {
            return 2;
        }
        // else if (Blood.eat(x, y)) {
        //     return 2;
        // }
        else 
            return -1;
    }
}