import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileInputStream;



public class GameOver {
    private static ObjectInputStream input;
    //public Stage newstage = new Stage();
    private static String oppath = "";
    private int score;
    private static Rank[] r = new Rank[5];
    private boolean flag = false;
    private static Rank tmp;
    private Image pic;
    private int ranked = 5; 
    private String result;
    public GameOver(String s) {
        pic = new ImageIcon("images/gameover.jpeg").getImage();
        result = s;
        //System.exit(0);
    }

    public void showImage(Graphics g2d){
        g2d.drawImage(pic, 0, 300, null);
        Font scoreFont = new Font("Silom", Font.BOLD, 30);
        g2d.setFont(scoreFont);
        g2d.setColor(new Color(255, 177, 207)); 
        g2d.drawString(result, 150, 600);
        // System.exit(0);
    }

    
    // public void showinput() {
    //     try{
    //         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/InputController.fxml"));    
    //         Parent root = (Parent)fxmlLoader.load();
    //         InputController controller = fxmlLoader.<InputController>getController();
    //         Scene scene = new Scene(root);
    //         String css = this.getClass().getResource("css/input.css").toExternalForm(); 
    //         scene.getStylesheets().add(css);
    //         newstage.setTitle("input"); // displayed in window's title bar
    //         newstage.setScene(scene);
    //         newstage.show();
    //         //oldstage.close();
    //         controller.setStage(newstage); 
    //         String ooo = String.copyValueOf(oppath.toCharArray());
    //         controller.setinit(score ,ooo,ranked);
    //         //Rank mmm = new Rank(r[0].name,r[0].score);  
    //         controller.setrank(r[0].name,r[0].score,0);
    //         controller.setrank(r[1].name,r[1].score,1);
    //         controller.setrank(r[2].name,r[2].score,2);
    //         controller.setrank(r[3].name,r[3].score,3);
    //         controller.setrank(r[4].name,r[4].score,4);

    //     }
    //     catch(Exception e) {
    //         // do something, e.g. print e.getMessage()
    //     }

    // }

    

    
    
    
    
}