import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class SecondarySceneController
{

    private Stage stage;

    @FXML   private TextField nameTextField;
    @FXML   private ChoiceBox categoryChoiceBox;
    @FXML   private Button saveButton;
    @FXML   private Button cancelButton;

    public SecondarySceneController()
    {
        System.out.println("Initialising controllers...");
    } 

    public void prepareStageEvents(Stage stage)
    {
        System.out.println("Preparing stage events...");

        this.stage = stage;

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("Close button was clicked!");
                    stage.close();
                }
            });

    }         

    @FXML   void initialize() 
    {            
        System.out.println("Asserting controls...");
        try
        {
            assert nameTextField != null : "Can't find nameTextField";
            assert categoryChoiceBox != null : "Can't find categoryChoiceBox";
            assert saveButton != null : "Can't find saveButton";
            assert cancelButton != null : "Can't find cancelButton";
        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }
    }

    @FXML   void saveButtonClicked()
    {
        System.out.println("Save button clicked - feature not yet implemented!");        
    }

    @FXML   void cancelButtonClicked()
    {
        System.out.println("Cancel button clicked!");        
        stage.close();
    }

}
