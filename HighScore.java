import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class HighScore extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/highscore.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("highscore.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        stage.setTitle("HighScore");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
