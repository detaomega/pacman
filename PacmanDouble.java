import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;


public class PacmanDouble extends JFrame {
    public int mode = 1;
    public int ghostnum = 1; 
    public PacmanDouble(int n, int m){
        ghostnum = n;
        mode = m;
        initUI();
    }
    private void initUI() {
        Board2 bd = new Board2(ghostnum, mode);
        add(bd);
        setTitle("Pacman");
        setSize(650, 710);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
