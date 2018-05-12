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
import javafx.scene.image.WritableImage;
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
    private File TESTIMAGE;
    private ImageView theImageViewer;
    private Group mainRoot;


    public abstract ImageView  createCenterComponent();
    public abstract ImageView updateImageView(File inputFile);
    public abstract void setCurrentFilter(ImageFilter filter);
    public abstract void removeFilter();



    public  Window(Stage primaryStage){
        window = primaryStage;
        window.setTitle("Main Window");
        window.setResizable(false);
        theImageViewer = createCenterComponent();
        window.setOnCloseRequest(e ->{
            e.consume();
            window.close();
        });
        // Main Scene
        mainRoot = new Group();
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

        // EDIT THIS TO ONLY SELECT VALID IMAGES
        itemSelector.setTitle("Select Image");
        itemOpen.setOnAction(event -> {
            File selectedItem = itemSelector.showOpenDialog( primaryStage);
            if(selectedItem != null){
                TESTIMAGE = selectedItem;
                ImageView newImageView =  updateImageView(selectedItem);
                Group newRoot = new Group();
                Scene newScene;
                if(newImageView.getImage().getWidth() > 1300){
                    // Calculate the scale change to set correct height value in order to preserve the aspect ratio
                    double imgWidth = newImageView.getImage().getWidth();
                    double imgScale = 1300/imgWidth;
                    double newImgHeight = newImageView.getImage().getHeight() * imgScale;
                     newScene = new Scene( newRoot,1300, newImgHeight + menuBar.getHeight());

                }else{
                     newScene = new Scene( newRoot,newImageView.getImage().getWidth(), newImageView.getImage().getHeight() + menuBar.getHeight());
                }

                newRoot.getChildren().add(pane);
                newImageView.setPreserveRatio(true);
                newImageView.fitWidthProperty().bind(newScene.widthProperty());
                newImageView.fitHeightProperty().bind(newScene.heightProperty());
                theImageViewer = newImageView;
                mainScene =newScene;
                mainRoot = newRoot;
                window.setScene(mainScene);
                window.sizeToScene();

            }
        });

        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());

        // This section handles filters
        Menu filter = new Menu ("Filter");

        // Swirl
        MenuItem swirl = new MenuItem("Swirl");
        swirl.setOnAction(event -> setCurrentFilter(new Swirl()));

        // GrayScale
        MenuItem grayScale = new MenuItem("Grayscale");
        grayScale.setOnAction(event -> setCurrentFilter(new GrayScale()) );

        // FlipX
        MenuItem flipX = new MenuItem("Flip X");
        flipX.setOnAction(event -> setCurrentFilter(new FlipX()));

        // Red Filter
        MenuItem red_Filter = new MenuItem("Red Filter");
        red_Filter.setOnAction(event ->  setCurrentFilter(new RedFilter()));

        // Brightness filter
        MenuItem brightness = new MenuItem("Brightness");
        brightness.setOnAction(event -> setCurrentFilter(new BrightnessFilter()));

        // Sub Menu with patterns
        Menu patterns = new Menu("Patterns");

        // Vertical stripes in Sub Menu
        MenuItem vertical_Stripes = new MenuItem("Vertical Stripes");
        vertical_Stripes.setOnAction(event -> setCurrentFilter(new Vertical_Stripes()));

        // Chess in Sub Menu
        MenuItem chess = new MenuItem("Chess");
        chess.setOnAction(event -> setCurrentFilter(new Chess()));

        // Black Circle in Sub Menu
        MenuItem black_Circle = new MenuItem("Black Circle");
        black_Circle.setOnAction(event -> setCurrentFilter(new Black_Circle()));

        // Restore image to original
        MenuItem noFilter = new MenuItem("No Filter");
        noFilter.setOnAction(event ->  removeFilter());

        // Add all Sub Menu patterns to the menu
        patterns.getItems().addAll(vertical_Stripes,chess,black_Circle);

        // Add all filters and patterns to the filter menu
        filter.getItems().addAll(swirl,grayScale,flipX,red_Filter, brightness,patterns,noFilter);

        theImageViewer.setPreserveRatio(true);
        pane.getChildren().add(theImageViewer);

        file.getItems().addAll(itemOpen, itemSave, itemExit);
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(filter);
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();

    }
    public ImageView getImageView(){
        return  theImageViewer;
    }

}
