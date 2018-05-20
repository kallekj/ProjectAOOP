package Project;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NodeBox {

    private  static ScrollPane layout;
    private static Stage window;

    // This section will create a new window where the content of "Node" will be shown
    // In this case we use it to show the about, help and JavaDoc.

    /**
     *
     * @param title The title to display
     * @param content The Node to be added to the box
     */
    public static void display(String title, Node content){

        window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setAlwaysOnTop(false);
        window.setResizable(false);
        layout = new ScrollPane(content);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

}