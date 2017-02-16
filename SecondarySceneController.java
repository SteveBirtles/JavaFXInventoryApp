import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;

public class SecondarySceneController
{

    private Stage stage;
    private PrimarySceneController parent;

    @FXML   private TextField nameTextField;
    @FXML   private ChoiceBox categoryChoiceBox;
    @FXML   private Button saveButton;
    @FXML   private Button cancelButton;

    private Thing thing;

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

        System.out.println("Populating scene with items from the database...");        
        @SuppressWarnings("unchecked")
        List<Category> targetList = categoryChoiceBox.getItems();  // Grab a reference to the listView's current item list.
        Category.readAll(targetList);       
        categoryChoiceBox.getSelectionModel().selectFirst();

    }

    public void setParent(PrimarySceneController parent)
    {
        this.parent = parent;
    }

    public void loadItem(int id)
    {        
        thing = Thing.getById(id);
        nameTextField.setText(thing.name);

        List<Category> targetList = categoryChoiceBox.getItems();

        for(Category c : targetList)
        {
            if (c.id == thing.categoryId)
            {
                categoryChoiceBox.getSelectionModel().select(c);
            }                
        }

    }

    @FXML   void saveButtonClicked()
    {
        System.out.println("Save button clicked!");        

        if (thing == null)
        {   
            thing = new Thing(0, "", 0);
        }

        thing.name = nameTextField.getText();

        Category selectedCategory = (Category) categoryChoiceBox.getSelectionModel().getSelectedItem();        
        thing.categoryId = selectedCategory.id;

        thing.save();

        parent.initialize();

        stage.close();
    }

    @FXML   void cancelButtonClicked()
    {
        System.out.println("Cancel button clicked!");        
        stage.close();
    }

}
