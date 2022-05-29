import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Pane background;

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
    private RadioButton easy;

    @FXML
    private RadioButton four;

    @FXML
    private ToggleGroup ghostToggleGroup;

    @FXML
    private RadioButton hard;

    @FXML
    private RadioButton medium;

    @FXML
    private ToggleGroup modeToggleGroup;

    @FXML
    private RadioButton one;

    @FXML
    private RadioButton three;

    @FXML
    private RadioButton two;

    
    public void initialize() {
        // user data on a control can be any Object
        one.setUserData(GhostNum.ONE);
        two.setUserData(GhostNum.TWO);
        three.setUserData(GhostNum.THREE);
        four.setUserData(GhostNum.FOUR);
        easy.setUserData(GameMode.EASY);
        medium.setUserData(GameMode.MEDIUM);
        hard.setUserData(GameMode.HARD);
    }


    @FXML
    void modeRadioButtonSelected(ActionEvent event) {
        // user data for each mode RadioButton is the corresponding Mode
        mode = (GameMode) modeToggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void numberRadioButtonSelected(ActionEvent event) {
        // user data for each number ghost RadioButton is the corresponding num
        num = (GhostNum) ghostToggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    private Button ok;

    @FXML
    private Pane modepane;

    @FXML
    private Pane numpane;

    @FXML
    void okButtonPressed(ActionEvent event) {
        if(num == GhostNum.ONE){
            System.out.println("num : 1");
        } else if(num == GhostNum.TWO){
            System.out.println("num : 2");
        } else if(num == GhostNum.THREE){
            System.out.println("num : 3");
        } else if(num == GhostNum.FOUR){
            System.out.println("num : 4");
        } 

        if(mode == GameMode.EASY){
            System.out.println("mode : easy");
        } else if(mode == GameMode.MEDIUM){
            System.out.println("mode : medium");
        } else if(mode == GameMode.HARD){
            System.out.println("mode : hard");
        }
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();


    }

}
