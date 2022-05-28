import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PacmanController {
    // enum representing ghost num
    private enum GhostNum {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);
        private final int num;
        GhostNum(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }
    };

    // enum representing game mode
    private enum GameMode {
        EASY(1),
        MEDIUM(2),
        HARD(3);
        private final int mode;
        GameMode(int mode) {
            this.mode = mode;
        }
        public int getMode() {
            return mode;
        }
    };

    private GhostNum num = GhostNum.ONE; 
    private GameMode mode = GameMode.EASY;
     
	@FXML
    private Button buttonDouble;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonSingle;

    @FXML
    private Button buttonMenu;

    @FXML
    private GridPane pane;

    public void initialize() {
    	buttonDouble = new Button();
    	buttonExit = new Button();
    	buttonSingle = new Button();
    }

    @FXML
    void buttonMenuClicked(ActionEvent event) throws Exception {
        Stage sec = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("menu.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        sec.setTitle("Menu"); // displayed in window's title bar
        sec.setScene(scene);
        sec.show();
    }


    @FXML
    void buttonSingleClicked(ActionEvent event) {
    	PacmanSingle pms = new PacmanSingle();
    }

    @FXML
    void buttonDoubleClicked(ActionEvent event) {
    	PacmanDouble pmd = new PacmanDouble();
    }

    @FXML
    void buttonExitClicked(ActionEvent event) {
        System.exit(0);

    }



}