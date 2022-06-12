import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HighScoreMenuController {
    public Stage newstage;
    public Stage oldstage;
    public String modetitle;

    @FXML
    private Button backbutton;

    @FXML
    private Label score1;

    @FXML
    private Label modelabel;

    public void setStage(Stage s){
        oldstage = s;
    }

    
    @FXML
    void backOnAction(ActionEvent event) throws Exception{
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

    @FXML
    void easyClicked(ActionEvent event)throws Exception {
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/highscore.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        HighScoreController controller = fxmlLoader.<HighScoreController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/highscore.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("highscore"); // displayed in window's title bar
        newstage.setScene(scene);
        newstage.show();
        oldstage.close();
        controller.setStage(newstage);
        controller.setModeTitle("Easy");
        controller.setscore();
        controller.setlabel();
    }

    @FXML
    void hardClicked(ActionEvent event)throws Exception {
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/highscore.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        HighScoreController controller = fxmlLoader.<HighScoreController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/highscore.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("highscore"); // displayed in window's title bar
        newstage.setScene(scene);

        newstage.show();
        oldstage.close();
        controller.setStage(newstage);
        controller.setModeTitle("Hard");
        controller.setscore();
        controller.setlabel();
    }

    @FXML
    void mediumClicked(ActionEvent event)throws Exception {
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/highscore.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        HighScoreController controller = fxmlLoader.<HighScoreController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/highscore.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("highscore"); // displayed in window's title bar
        newstage.setScene(scene);

        newstage.show();
        oldstage.close();
        controller.setStage(newstage);
        controller.setModeTitle("Medium");
        controller.setscore();
        controller.setlabel();
    }

}
