import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileInputStream;

public class HighScoreController {
    private static ObjectInputStream input;
    public Stage newstage;
    public Stage oldstage;

    @FXML
    private Label modetitle;

    @FXML
    private Button backbutton;

    @FXML
    private Label name1;

    @FXML
    private Label name2;

    @FXML
    private Label name3;

    @FXML
    private Label name4;

    @FXML
    private Label name5;

     @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Label score3;

    @FXML
    private Label score4;

    @FXML
    private Label score5;

    private static String m;
    private static Rank tmp;

    private static String opfile;
    private static String sc;
    private static String name;
    private static Rank[] r= new Rank[5];

    public void setModeTitle(String s){
        //String newCopy = String.copyValueOf(first.toCharArray());
        m = String.copyValueOf(s.toCharArray());
        System.out.println(m);
        String mode = "Mode : " + s;
        modetitle.setText(mode);
        opfile = "score/" + s + ".bin";
    }
    
    public void setscore(){
        openFile();
        readRecord();
        closeFile();
    }


    public static void openFile(){
        try{
            System.out.println(opfile);
            input = new ObjectInputStream(Files.newInputStream(Paths.get(opfile)));
            
        }
        catch(IOException e){
            System.err.println("Error opening file.");
            System.exit(1);
        }
    }

    public  void setlabel(){
        for(int i = 0; i < 5 ; i++){
            Rank t = new Rank(r[i]);
            sc = Integer.toString(t.score);
            name = t.name;
            switch(i+1){
                case 1 :
                    score1.setText(sc);
                    name1.setText(name);
                    break;
                case 2:
                    score2.setText(sc);
                    name2.setText(name);
                    break;
                case 3:
                    score3.setText(sc);
                    name3.setText(name);
                    break;
                case 4:
                    score4.setText(sc);
                    name4.setText(name);
                    break;
                case 5:
                    score5.setText(sc);
                    name5.setText(name);
                    break;

            }
        } 
    }

    public static void readRecord(){
        int i = 0;
        try{
            System.out.println("hhhh");
            while(true){
                System.out.println(i);
                tmp = ((Rank)input.readObject());
                System.out.println(tmp.getName());
                r[i] = new Rank(tmp);
                //setlabel();
                i++;    
            }
            
        }
        catch(EOFException e){
            System.out.println("%nno more records%n");
            //System.exit(1);
        }
        catch(ClassNotFoundException c){
            System.err.println("Invalid object type");
            //System.exit(1);
        }
        catch(IOException io){

            System.out.println(io.getMessage());
            //System.exit(1);
        }
    }

    public static void closeFile(){
        try{
            if(input != null)
                input.close();
        }
        catch(IOException e){
            System.err.println("Error opening file.");
            System.exit(1);
        }

    }

    @FXML
    void backOnAction(ActionEvent event) throws Exception {
        newstage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/highscoremenu.fxml"));    
        Parent root = (Parent)fxmlLoader.load();
        HighScoreMenuController controller = fxmlLoader.<HighScoreMenuController>getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("css/highmenu.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        newstage.setTitle("highscoremenu"); // displayed in window's title bar
        newstage.setScene(scene);

        newstage.show();
        oldstage.close();
        controller.setStage(newstage);   
    }

    public void setStage(Stage s){
        oldstage = s;
    }

}
