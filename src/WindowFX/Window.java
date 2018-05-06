package WindowFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;


public class Window extends Application {

    private Stage window;
    private int SCENE_WIDTH = 700, SCENE_HEIGHT = 700;
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
            File selectedItem = itemSelector.showOpenDialog( window);
            if(selectedItem != null){
                updateImage(selectedItem);
            }
        });

        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());
        pane.getChildren().add(theImageViewer);


        Menu filter = new Menu("Filter");
        MenuItem exFilter = new MenuItem("exFilter");
        Slider slider = new Slider(0, 10, 1);
        exFilter.setOnAction(event -> SliderBox.display(exFilter.getId(), slider));



        file.getItems().addAll(itemOpen, itemSave, itemExit);
        filter.getItems().add(exFilter);
        menuBar.getMenus().addAll(file, filter);
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();
    }
  
    public void updateImage(File inputFile){
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        theImageViewer.setImage(newImage);

        if(newImage.getWidth() > 1300){
            // Calculate the scale change to set correct height value in order to preserve the aspect ratio
            double imgWidth = newImage.getWidth();
            double imgScale = 1300/imgWidth;
            double newImgHeight = newImage.getHeight() * imgScale;

            window.setWidth(1300);
            window.setHeight(newImgHeight);
            theImageViewer.fitWidthProperty().bind(mainScene.widthProperty());

        }else{
            window.setWidth(newImage.getWidth());
            window.setHeight(newImage.getHeight());
            theImageViewer.fitWidthProperty().bind(mainScene.widthProperty());
        }

        theImageViewer.setPreserveRatio(true);

        // Auto resize, needs some tweeks
//        theImageViewer.setFitWidth(mainScene.getWidth());
//        theImageViewer.setFitHeight(mainScene.getHeight());
//        theImageViewer.setImage(newImage);
//        window.setWidth(newImage.getWidth());
//        window.setHeight(newImage.getHeight());
//        theImageViewer.fitWidthProperty().bind(mainScene.widthProperty());
//        theImageViewer.fitHeightProperty().bind(mainScene.heightProperty());
//        theImageViewer.setPreserveRatio(true);

    }

}
