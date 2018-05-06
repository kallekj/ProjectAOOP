package WindowFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SliderBox {

    private static double returnValue;

    public static double display(String title, Slider slider){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        returnValue = slider.getValue();

        HBox layout = new HBox(30);
        layout.getChildren().add(slider);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return returnValue;
    }

}
