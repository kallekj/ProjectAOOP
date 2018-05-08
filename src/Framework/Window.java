package Framework;


import Filters.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public abstract class  Window {
    private Stage window;
    private final int SCENE_WIDTH = 700, SCENE_HEIGHT = 700;
    private Scene mainScene;
    private MenuBar theMenuBar;
    private ImageFilter currentFilter;
    private File TESTIMAGE;


    public abstract ImageView  createCenterComponent();
    public abstract void updateImageView(File inputFile, Scene theScene, Stage theStage);
    public abstract void setCurrentFilter(ImageFilter filter);
    public abstract void removeFilter();



    public  Window(Stage primaryStage){
        window = primaryStage;
        window.setTitle("Main Window");
        window.setResizable(false);
        ImageView theImageViewer = createCenterComponent();
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
                TESTIMAGE = selectedItem;
                updateImageView(selectedItem, mainScene, window);
            }
        });

        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());

        Menu filter = new Menu ("Filter");
        MenuItem swirl = new MenuItem("Swirl");
        swirl.setOnAction(event -> setCurrentFilter(new Swirl()));
        MenuItem grayScale = new MenuItem("Grayscale");
        grayScale.setOnAction(event -> setCurrentFilter(new GrayScale()) );
        MenuItem flipX = new MenuItem("Flip X");
        flipX.setOnAction(event -> setCurrentFilter(new FlipX()));
        MenuItem red_Filter = new MenuItem("Red Filter");
        red_Filter.setOnAction(event ->  setCurrentFilter(new RedFilter()));
        Menu patterns = new Menu("Patterns");
        MenuItem vertical_Stripes = new MenuItem("Vertical Stripes");
        vertical_Stripes.setOnAction(event -> setCurrentFilter(new Vertical_Stripes()));
        MenuItem chess = new MenuItem("Chess");
        chess.setOnAction(event -> setCurrentFilter(new Chess()));
        MenuItem black_Circle = new MenuItem("Black Circle");
        black_Circle.setOnAction(event -> setCurrentFilter(new Black_Circle()));
        MenuItem noFilter = new MenuItem("No Filter");
        noFilter.setOnAction(event ->  removeFilter());

        patterns.getItems().addAll(vertical_Stripes,chess,black_Circle);
        filter.getItems().addAll(swirl,grayScale,flipX,red_Filter,patterns,noFilter);

        theImageViewer.setPreserveRatio(true);
        pane.getChildren().add(theImageViewer);


        file.getItems().addAll(itemOpen, itemSave, itemExit);
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(filter);
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();

    }
}
