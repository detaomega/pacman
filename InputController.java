import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class InputController {
    public Stage newstage;
    public Stage oldstage;
    private static ObjectOutputStream output;
    private int ranked = 5; // 名次
    private static String oppath = "";
    private int score;
    private static Rank[] rd;
    private String name = "";
    @FXML
    private TextField nameTextfiled;

    @FXML
    private Label scorelabel;

    public void setStage(Stage s){
        oldstage = s;
        rd = new Rank[5];
    }

    public void setrank(String name, int score, int i){
        rd[i] = new Rank(name, score); 
    }

    public void setinit(int s1, String path,int r){
        //rd = rk;
        oppath =  String.copyValueOf(path.toCharArray());
        score = s1;
        ranked = r;
        scorelabel.setText(Integer.toString(score));
    }

    public static void outputopenFile(){
        try{
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get(oppath)));
        }
        catch(IOException e){
            System.err.println("Error opening file.");
            System.exit(1);
        }
    }
    public static void addRecord(){
        // Scanner input = new Scanner(System.in);
        // System.out.println("Enter : ");
        int i = 0;
        while(i < 5){
            try{
                //Rank rd = new Rank(input.next(), input.nextInt());
                output.writeObject(rd[i]);
                i++;
            }
            catch(IOException io){
                System.out.println("Error write form file");
                //System.exit(1);
            }
            catch(NoSuchElementException e){
                System.out.println("Invalid input, Please try again");
                //input.nextLine();
                //System.exit(1);
            }
            
            
        }
        
        
        
    }

    public static void outputcloseFile(){
        try{
            if(output != null)
                output.close();
        }
        catch(IOException e){
            System.err.println("Error opening file.");
            System.exit(1);
        }

    }

    public void rewrite(){
        Rank add = new Rank(name, score);
        Rank move = new Rank(rd[ranked - 1]);
        rd[ranked - 1] = add;
        for(int i = ranked ; i < 5;i++){
            Rank tmp = new Rank(rd[ranked]);
            rd[ranked] = move;
            move = tmp;
        }
    }

    @FXML
    void backClicked(ActionEvent event) throws Exception{
        name = nameTextfiled.getText();
        outputopenFile();
        rewrite();
        addRecord();
        outputcloseFile();

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
        //controller.setting(n,m);
        newstage.show();
        oldstage.close();
    }

    @FXML
    void inputed(ActionEvent event) {
        // nameTextfiled
    }

}