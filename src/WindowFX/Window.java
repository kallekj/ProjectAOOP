package WindowFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import java.awt.event.ActionListener;
import java.io.File;


public class Window extends Application {

    private Stage window;
    private Double imageY_Offset;
    private final int SCENE_WIDTH = 500, SCENE_HEIGHT = 500;
    ImageView theImageViewer;
    private Scene mainScene;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setTitle("Main Window");
        theImageViewer = new ImageView();
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

        itemOpen.setOnAction(event -> {
            File selectedItem = itemSelector.showOpenDialog( primaryStage);
            if(selectedItem != null){
                updateImage(selectedItem);
            }
        });

        MenuItem itemExit = new MenuItem("Exit", null);

        itemExit.setOnAction(e -> Platform.exit());
        theImageViewer.setPreserveRatio(false);
        pane.getChildren().add(theImageViewer);

        file.getItems().addAll(itemOpen, itemSave, itemExit);
        menuBar.getMenus().add(file);


        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();
        imageY_Offset = menuBar.getHeight();
    }
  
    public void updateImage(File inputFile){
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        theImageViewer.setFitWidth(window.getScene().getWidth());
        theImageViewer.setY(imageY_Offset);
        theImageViewer.setFitHeight(window.getScene().getHeight() - imageY_Offset);
        theImageViewer.setImage(newImage);
    }

}
