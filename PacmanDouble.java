import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;


public class PacmanDouble extends JFrame {
    public PacmanDouble() {
        initUI();
    }
    private void initUI() {
        Board2 bd = new Board2();
        add(bd);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 710);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        PacmanDouble pc = new PacmanDouble();
    }
}
