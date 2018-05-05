package WindowFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;


public class Window extends Application {

    private Stage window;
    private Double imageY_Offset;
    private GraphicsContext gc;
    private final int CANVAS_WIDTH = 500, CANVAS_HEIGHT = 500;
    ImageView imageView;



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
        Scene mainScene = new Scene(mainRoot, CANVAS_WIDTH, CANVAS_HEIGHT);


        // MenuBar
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());



        // File
        Menu file = new Menu("File");
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemSave = new MenuItem("Save");
        MenuItem itemExit = new MenuItem("Exit", null);

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

        itemExit.setMnemonicParsing(true);
        itemExit.setOnAction(e -> Platform.exit());
        imageView.setPreserveRatio(false);

        file.getItems().addAll(itemOpen, itemSave, itemExit);

        menuBar.getMenus().add(file);

        mainRoot.getChildren().add(menuBar);
        mainRoot.getChildren().add(imageView);
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
