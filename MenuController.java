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
    public Stage newstage;
    public Stage oldstage;
    public int player;

    public void setStage(Stage s){
        oldstage = s;
    }

    public void setPlayer(int i){
        player = i;
    }

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

    public void setting(int n, int m){
        if(n == 1){
            num = GhostNum.ONE;
            one.setSelected(true);
        } else if(n == 2){
            num = GhostNum.TWO;
            two.setSelected(true);
        } else if(n == 3){
            num = GhostNum.THREE;
            three.setSelected(true);
        } else if(n == 4){
            num = GhostNum.FOUR;
            four.setSelected(true);
        } 

        if(m == 1){
            mode = GameMode.EASY;
            easy.setSelected(true);
        } else if(m == 2){
            mode = GameMode.MEDIUM;
            medium.setSelected(true);
        } else if(m == 3){
            mode = GameMode.HARD;
            hard.setSelected(true);
        }
    }
    
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
    void okButtonPressed(ActionEvent event) throws Exception{
        int n = 1,m = 1;
        if(num == GhostNum.ONE){
            n = 1;
        } else if(num == GhostNum.TWO){
            n = 2;
        } else if(num == GhostNum.THREE){
            n = 3;
        } else if(num == GhostNum.FOUR){
            n = 4;
        } 

        if(mode == GameMode.EASY){
            m = 1;
        } else if(mode == GameMode.MEDIUM){
            m = 2;
        } else if(mode == GameMode.HARD){
            m = 3;
        }
        if(player == 1){
            newstage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SingleMenu.fxml"));    
            Parent root = (Parent)fxmlLoader.load();
            SingleController controller = fxmlLoader.<SingleController>getController();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("css/singlestyle.css").toExternalForm(); 
            scene.getStylesheets().add(css);
            newstage.setTitle("Single playerMenu"); // displayed in window's title bar
            newstage.setScene(scene);
            controller.setStage(newstage);
            controller.setting(n,m);
            newstage.show();
            oldstage.close();
        }
        else if(player == 2){
            newstage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/DoubleMenu.fxml"));    
            Parent root = (Parent)fxmlLoader.load();
            DoubleController controller = fxmlLoader.<DoubleController>getController();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("css/doublestyle.css").toExternalForm(); 
            scene.getStylesheets().add(css);
            newstage.setTitle("Double player Menu"); // displayed in window's title bar
            newstage.setScene(scene);
            controller.setStage(newstage);
            controller.setting(n,m);
            newstage.show();
            oldstage.close();
        }
        else{
            System.out.println("error player");
        }
        

    }



}
