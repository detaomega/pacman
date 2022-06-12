import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class RuleController {
    public Stage newstage;
    public Stage oldstage;

    @FXML
    void backClicked(ActionEvent event) throws Exception{
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

    public void setStage(Stage s){
        oldstage = s;
    }

}
