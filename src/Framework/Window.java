package Framework;


import Filters.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class  Window {
    private Stage window;
    private final int SCENE_WIDTH = 700, SCENE_HEIGHT = 700;
    private Scene mainScene;
    private MenuBar theMenuBar;
    private File TESTIMAGE;
    private ImageView theImageViewer;
    private Group mainRoot;
    private KeyCodeCombination undoKeyCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);
    private KeyCodeCombination undoRemovalCompination = new KeyCodeCombination(KeyCode.Y,KeyCombination.CONTROL_ANY);

    public abstract ImageView  createCenterComponent();
    public abstract ImageView updateImageView(File inputFile);
    public abstract void setCurrentModifier(ImageModifier filter);
    public abstract void removeModifier();
    public abstract void removeAllModifiers();
    public abstract void undoRemoval();



    public  Window(Stage primaryStage){
        window = primaryStage;
        window.setTitle("Main Window");
        window.setResizable(false);
        theImageViewer = createCenterComponent();
        window.setOnCloseRequest(e ->{
            e.consume();
            window.close();
        });
        window.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(undoKeyCombination.match(event)){
                    removeModifier();
                }
                if(undoRemovalCompination.match(event)){
                    undoRemoval();
                }
            }
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
        MenuItem itemCreator = new MenuItem("New");


        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");
        itemSave.setOnAction(event -> {
            Image FXImage = theImageViewer.snapshot(new SnapshotParameters(), null);
            RenderedImage saveImage = SwingFXUtils.fromFXImage(FXImage,null);
            FileChooser fileSaver = new FileChooser();
            fileSaver.setTitle("Save Edited Image");

            File newFile = fileSaver.showSaveDialog(primaryStage);
            if(newFile != null){
                try{ ImageIO.write(saveImage,"png",new File( newFile.toString() + ".png"));

                }
                catch(IOException ex){
                    Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE,null,ex);

                }
            }

        });

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());

        // This section handles filters
        Menu filter = new Menu ("Filter");

        // Swirl
        MenuItem swirl = new MenuItem("Swirl");
        swirl.setOnAction(event -> setCurrentModifier(new Swirl()));

        // GrayScale
        MenuItem grayScale = new MenuItem("Grayscale");
        grayScale.setOnAction(event -> setCurrentModifier(new GrayScale()) );

        // FlipX
        MenuItem flipX = new MenuItem("Flip X");
        flipX.setOnAction(event -> setCurrentModifier(new FlipX()));

        // Red Filter
        MenuItem red_Filter = new MenuItem("Red Filter");
        red_Filter.setOnAction(event ->  setCurrentModifier(new RedFilter()));

        //Inverted Filter
        MenuItem invertFilter = new MenuItem("Invert Colors");
        invertFilter.setOnAction(event -> setCurrentModifier(new InvertFilter()));

        // Brightness filter
        MenuItem brightness = new MenuItem("Brightness");
        brightness.setOnAction(event -> setCurrentModifier(new BrightnessFilter()));

        MenuItem paint = new MenuItem("Paint");
        paint.setOnAction(event -> setCurrentModifier(new Paint()));

        // Sub Menu with patterns
        Menu patterns = new Menu("Patterns");

        // Vertical stripes in Sub Menu
        MenuItem vertical_Stripes = new MenuItem("Vertical Stripes");
        vertical_Stripes.setOnAction(event -> setCurrentModifier(new Vertical_Stripes()));

        // Chess in Sub Menu
        MenuItem chess = new MenuItem("Chess");
        chess.setOnAction(event -> setCurrentModifier(new Chess()));

        // Black Circle in Sub Menu
        MenuItem black_Circle = new MenuItem("Black Circle");
        black_Circle.setOnAction(event -> setCurrentModifier(new Black_Circle()));

        // Restore image to original
        MenuItem noFilter = new MenuItem("No Filter");
        noFilter.setOnAction(event ->  removeAllModifiers());

        // Add all Sub Menu patterns to the menu
        patterns.getItems().addAll(vertical_Stripes,chess,black_Circle);

        // Add all filters and patterns to the filter menu
        filter.getItems().addAll(swirl,grayScale,flipX,red_Filter, paint,brightness,invertFilter,patterns,noFilter);

        // Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem help = new MenuItem("Help");
        help.setOnAction(event -> TextBox.display("Help",  "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tinci"));
        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> TextBox.display("About", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tinci"));

        helpMenu.getItems().addAll(help, about);

        theImageViewer.setPreserveRatio(true);
        pane.getChildren().add(theImageViewer);

        file.getItems().addAll(itemOpen,itemCreator, itemSave, itemExit);
        menuBar.getMenus().addAll(file, filter, helpMenu);
        mainRoot.getChildren().addAll(pane);

        window.setScene(mainScene);
        window.show();

    }
    public ImageView getImageView(){
        return  theImageViewer;
    }

}
