import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class PacmanController {
    // public Stage first;
    public Stage newstage;
    public Stage oldstage;

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
    public void setStage(Stage s){
        oldstage = s;
    }

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
    void buttonSingleClicked(ActionEvent event) throws Exception{
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SingleMenu.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        SingleController controller = fxmlLoader.<SingleController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/singlestyle.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("Single player Menu"); // displayed in window's title bar
        newstage.setScene(scene);
        controller.setStage(newstage);
        
        newstage.show();
        oldstage.close();
    }

    @FXML
    void buttonDoubleClicked(ActionEvent event) throws Exception{
    	//PacmanDouble pmd = new PacmanDouble();
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoubleMenu.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        DoubleController controller = fxmlLoader.<DoubleController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/doublestyle.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("double player Menu"); // displayed in window's title bar
        newstage.setScene(scene);
        controller.setStage(newstage);
        newstage.show();
        oldstage.close();
    }

    @FXML
    void buttonExitClicked(ActionEvent event) {
        System.exit(0);
    }



}