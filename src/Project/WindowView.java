package Project;


import Modifiers.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public  class WindowView extends Stage{
    //private Stage window;
    private final int SCENE_WIDTH = 700, SCENE_HEIGHT = 700;
    private Scene mainScene;
    private ImageView theImageView;
    private Group mainRoot;
    private KeyCodeCombination undoKeyCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);
    private KeyCodeCombination undoRemovalCombination = new KeyCodeCombination(KeyCode.Y,KeyCombination.CONTROL_ANY);
    private WindowController windowController;
    private  VBox pane;
    private WindowModel theModel;


    public WindowView(Stage primaryStage){
       setTitle("Photo Modifier");
       setResizable(false);
        theModel = new WindowModel(this);
        theImageView = theModel.createCenterComponent();
        setOnCloseRequest(e ->{
           e.consume();
             close();
        });
        addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(undoKeyCombination.match(event)){
                    theModel.removeModifier();
                }
                if(undoRemovalCombination.match(event)){
                   theModel.undoRemoval();
                }
            }
        });
        // Main Scene
        mainRoot = new Group();
        mainScene = new Scene(mainRoot, SCENE_WIDTH, SCENE_HEIGHT);
         pane = new VBox();
        // MenuBar
         windowController = new WindowController(this);
        pane.getChildren().add(windowController);
        theImageView.setPreserveRatio(true);
        pane.getChildren().add(theImageView);

        mainRoot.getChildren().addAll(pane);
        setScene(mainScene);
        show();
    }

    /**
     *
     * @param newImageView The ImageView to scale the scene to
     * @precondition WindowView initialized
     * @postcondition Scene updated
     */
    public void updateScene(ImageView newImageView){
        Group newRoot = new Group();
        Scene newScene;
        if(newImageView.getImage().getWidth() > 1300){
            // Calculate the scale change to set correct height value in order to preserve the aspect ratio
            double imgWidth = newImageView.getImage().getWidth();
            double imgScale = 1300/imgWidth;
            double newImgHeight = newImageView.getImage().getHeight() * imgScale;
            newScene = new Scene( newRoot,1300, newImgHeight + windowController.getHeight());

        }else{
            newScene = new Scene( newRoot,newImageView.getImage().getWidth(), newImageView.getImage().getHeight() +windowController.getHeight());
        }

        newRoot.getChildren().add(pane);
        newImageView.setPreserveRatio(true);
        newImageView.fitWidthProperty().bind(newScene.widthProperty());
        newImageView.fitHeightProperty().bind(newScene.heightProperty());
        theImageView = newImageView;
        mainScene =newScene;
        mainRoot = newRoot;
        setScene(mainScene);
        sizeToScene();
    }
    public WindowModel getTheModel(){
        return theModel;
    }
    public WindowController getWindowController(){
        return windowController;
    }

}
