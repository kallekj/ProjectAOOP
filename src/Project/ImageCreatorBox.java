package Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ImageCreatorBox {
    private static int selectedHeight;
    private static int selectedWidth;
    private static Color selectedColor;
    private static Separator separator1 = new Separator(Orientation.HORIZONTAL);
    private static Separator separator2 = new Separator(Orientation.HORIZONTAL);
    private static Separator separator3 = new Separator(Orientation.HORIZONTAL);
    private static Stage imageSetEntry;
    private static Button CreateButton ;

    /**
     * @postcondition fields are not null
     */
    public static void display() {

        imageSetEntry = new Stage();
        imageSetEntry.initModality(Modality.APPLICATION_MODAL);
        imageSetEntry.setHeight(500);
        imageSetEntry.setWidth(600);
        imageSetEntry.setResizable(false);
        CreateButton = new Button("Create");
        CreateButton.setAlignment(Pos.BOTTOM_RIGHT);
        VBox EntryLayout = new VBox(30);
        Text newImgHeightLabel = new Text("New Image Height");
        TextField newImgHeight = new TextField();

        Text newImgWidthLabel = new Text("New Image Width");
        TextField newImgWidth = new TextField();
        newImgHeight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedHeight = Integer.parseInt(newValue);
            }
        });
        newImgWidth.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedWidth = Integer.parseInt(newValue);
            }
        });


        Text newImgColorLabel = new Text("Background color");
        ColorPicker backGroundColor = new ColorPicker();
        backGroundColor.setOnAction(event -> {
            selectedColor = backGroundColor.getValue();
        });
        imageSetEntry.setOnCloseRequest(event -> {
            event.consume();
            imageSetEntry.close();
        });
        EntryLayout.getChildren().addAll(newImgHeightLabel,newImgHeight,separator1,newImgWidthLabel,newImgWidth,separator2,newImgColorLabel,backGroundColor,separator3,CreateButton);
        Scene scene = new Scene(EntryLayout);
        imageSetEntry.setScene(scene);
        imageSetEntry.show();
    }

    /**
     * @precondition display has been used
     * @return CreateButton
     */

    public static Button getCreateButton() {
        return CreateButton;
    }
    /**
     * @precondition display has been used
     * @return Selected Height
     */
    public static int getSelectedHeight(){
        return selectedHeight;
    }
    /**
     * @precondition display has been used
     * @return Selected Width
     */
    public static int getSelectedWidth(){
        return selectedWidth;
    }
    /**
     * @precondition display has been used
     * @return Selected Color
     */
    public static Color getSelectedColor() {
        return selectedColor;
    }
    /**
     * @precondition display has been used
     * @postcondition Stage is closed
     */
    public static void closeWindow(){
        imageSetEntry.fireEvent(new WindowEvent(imageSetEntry,WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
