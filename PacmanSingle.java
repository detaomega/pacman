import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;


public class PacmanSingle extends JFrame {
    public int mode = 1;
    public int ghostnum = 1; 
    public PacmanSingle(int n, int m) {
        ghostnum = n;
        mode = m;
        initUI();
    }

    private void initUI() {
        Board bd = new Board(ghostnum, mode);
        bd.setJFrame(this);
        add(bd);
        setTitle("Pacman");
        setSize(700, 720);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
}
