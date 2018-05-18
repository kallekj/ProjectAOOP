package Project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TextBox  {

    private  static HBox layout;
    private static Stage window;

    public static void display(String title, String inputText){

        window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setAlwaysOnTop(false);
        window.setResizable(false);

        layout = new HBox(50);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        Text theText = new Text();
        theText.setText(inputText);
        theText.setWrappingWidth(600);
        layout.getChildren().add(theText);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

}