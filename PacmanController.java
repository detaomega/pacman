import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PacmanController {
	@FXML
    private Button buttonDouble;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonSingle;

    @FXML
    private GridPane pane;

    public void initialize() {
    	//System.out.println("jaaa");
    	buttonDouble = new Button();
    	buttonExit = new Button();
    	buttonSingle = new Button();
    }

    @FXML
    void buttonSingleClicked(ActionEvent event) {
    	PacmanSingle pms = new PacmanSingle();
    }

    @FXML
    void buttonDoubleClicked(ActionEvent event) {
    	PacmanDouble pmd = new PacmanDouble();
    }

    @FXML
    void buttonExitClicked(ActionEvent event) {
        System.exit(0);

    }



}