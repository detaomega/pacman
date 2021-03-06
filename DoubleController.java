import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DoubleController {
    public Stage newstage;
    public Stage oldstage; 
    public int mode = 2;
    public int ghostnum = 4; 

    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonback;

    @FXML
    private Button buttonmenu;

    @FXML
    private GridPane pane;
    public void setStage(Stage s){
        oldstage = s;
    }
    public void setting(int n, int m){
        ghostnum = n;
        mode = m;
    }

    @FXML
    void buttonBackClicked(ActionEvent event) throws Exception{
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Pacman.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        PacmanController controller = fxmlLoader.<PacmanController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/background.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("Pacman");
        newstage.setScene(scene);
        oldstage.close();
        newstage.show();
        controller.setStage(newstage);
    }

    @FXML
    void buttonPlayClicked(ActionEvent event) {
        PacmanDouble pms = new PacmanDouble(ghostnum, mode);
    }

    @FXML
    void buttonSettingClicked(ActionEvent event) throws Exception{
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Menu.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        MenuController controller = fxmlLoader.<MenuController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/menu.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("Setting"); // displayed in window's title bar
        newstage.setScene(scene);
        newstage.show();
        oldstage.close();
        controller.setStage(newstage);
        controller.setPlayer(2);
        controller.setting(ghostnum, mode);
    }

}
