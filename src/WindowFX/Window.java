package WindowFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import java.awt.event.ActionListener;
import java.io.File;


public class Window extends Application {

    private Stage window;
    private Double imageY_Offset;
    private final int CANVAS_WIDTH = 500, CANVAS_HEIGHT = 500;
    ImageView imageView;
    private Scene mainScene;
    private int initialX;
    private int initialY;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setTitle("Main Window");
        imageView = new ImageView();
        window.setOnCloseRequest(e ->{
            e.consume();
            window.close();
        });

        // Main Scene
        Group mainRoot = new Group();
        mainScene = new Scene(mainRoot, SCENE_WIDTH, SCENE_HEIGHT);
        VBox pane = new VBox();


        // MenuBar
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.getChildren().add(menuBar);



        // File
        Menu file = new Menu("File");
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemSave = new MenuItem("Save");

        FileChooser itemSelector = new FileChooser();
        itemSelector.setTitle("Select Image");

        itemOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File selectedItem = itemSelector.showOpenDialog( primaryStage);
                if(selectedItem != null){
                    updateImage(selectedItem);
                }
            }
        });

        MenuItem itemExit = new MenuItem("Exit", null);

        itemExit.setOnAction(e -> Platform.exit());
        imageView.setPreserveRatio(false);

        file.getItems().addAll(itemOpen, itemSave, itemExit);
        menuBar.getMenus().add(file);

        // Show Image
        Image image = new Image("WindowFX\\snyggKille.jpg");
        ImageView theImageViewer = new ImageView();
        theImageViewer.setImage(image);
        theImageViewer.setFitWidth(mainScene.getWidth());
        theImageViewer.setFitHeight(mainScene.getHeight());
        pane.getChildren().add(theImageViewer);

       // mainScene
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();
        imageY_Offset = menuBar.getHeight();
    }
  
    public void updateImage(File inputFile){
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        imageView.setFitWidth(window.getScene().getWidth());
        imageView.setY(imageY_Offset);
        imageView.setFitHeight(window.getScene().getHeight() - imageY_Offset);
        imageView.setImage(newImage);
    }

}
