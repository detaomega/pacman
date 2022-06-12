import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class InputTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/input.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        InputController controller = fxmlLoader.<InputController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/input.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        stage.setTitle("Pacman");
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
