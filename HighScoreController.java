import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HighScoreController {
    public Stage newstage;
    public Stage oldstage;

    @FXML
    private Button backbutton;

    @FXML
    void backOnAction(ActionEvent event) throws Exception {
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SingleMenu.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        SingleController controller = fxmlLoader.<SingleController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/singlestyle.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("Single Menu"); // displayed in window's title bar
        newstage.setScene(scene);
        controller.setStage(newstage);
        
        newstage.show();
        oldstage.close();
    }

    public void setStage(Stage s){
        oldstage = s;
    }

}
