import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;


public class PacmanSingle extends JFrame {
    public PacmanSingle() {
        initUI();
    }
    private void initUI() {
        Board bd = new Board();
        add(bd);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        PacmanSingle pc = new PacmanSingle();
    }
}
