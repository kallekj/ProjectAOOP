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
    private final int SCENE_WIDTH = 700, SCENE_HEIGHT = 700;
    ImageView theImageViewer;
    private Scene mainScene;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setTitle("Main Window");
        window.setResizable(false);
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


        // File, this section handles the file menu
        Menu file = new Menu("File");

        // This Section handles the open file operation
        MenuItem itemOpen = new MenuItem("Open");
        FileChooser itemSelector = new FileChooser();
        itemSelector.setTitle("Select Image");
        itemOpen.setOnAction(event -> {
            File selectedItem = itemSelector.showOpenDialog( primaryStage);
            if(selectedItem != null){
                updateImage(selectedItem);
            }
        });

        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());
        theImageViewer.setPreserveRatio(true);
        pane.getChildren().add(theImageViewer);


        file.getItems().addAll(itemOpen, itemSave, itemExit);
        menuBar.getMenus().add(file);
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();
    }
  
    public void updateImage(File inputFile){
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        theImageViewer.setFitWidth(mainScene.getWidth());
        theImageViewer.setFitHeight(mainScene.getHeight());
        theImageViewer.setImage(newImage);
        window.getScene().getRoot().getChildrenUnmodifiable().get(1);
    }

}
