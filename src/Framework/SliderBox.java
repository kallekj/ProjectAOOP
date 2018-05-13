package Framework;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SliderBox  {

    private static double returnValue;
    private  static VBox layout;
    private static Stage window;

    public static void display(String title, Slider slider){

        window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setWidth(600);
        window.setHeight(200);
        window.setAlwaysOnTop(false);
        window.setResizable(false);
        returnValue = slider.getValue();

        layout = new VBox(50);
        addSlider(title,slider);

        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public static void addSlider(String title, Slider slider){
        Separator newSeparator = new Separator();
        TextField newTextField = new TextField(title);
        if(slider.getAccessibleText() != null){
            newTextField.setText(slider.getAccessibleText());
        }
        window.setHeight(window.getHeight()+100);
        newTextField.setMaxWidth(80);
        newTextField.setEditable(false);
        newTextField.setMouseTransparent(true);
        newTextField.setFocusTraversable(false );
        newSeparator.setHalignment(HPos.LEFT);
        layout.getChildren().addAll(newTextField, slider);

    }


}