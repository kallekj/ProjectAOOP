package WindowFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import javax.swing.*;
import java.awt.event.ActionListener;


public class Window extends Application {

    private Stage window;
    private GraphicsContext gc;
    private final int CANVAS_WIDTH = 500, CANVAS_HEIGHT = 500;




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Main Window");
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

        itemExit.setMnemonicParsing(true);
        itemExit.setOnAction(e -> Platform.exit());

        file.getItems().addAll(itemOpen, itemSave, itemExit);

        menuBar.getMenus().add(file);

        mainRoot.getChildren().add(menuBar);
        window.setScene(mainScene);
        window.show();

    }
}
