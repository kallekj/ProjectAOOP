package Framework;
import Filters.*;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ImageWindow extends Window {
    ImageView theImageViewer;
    private Image uneditedImage;
    private ArrayList<ImageFilter> filters;
    private ImageFilter currentFilter;

    public ImageWindow (Stage primaryStage){
        super(primaryStage);
    }
    @Override
    public ImageView createCenterComponent() {
        return theImageViewer = new ImageView();
    }

    @Override
    public void updateImageView(File inputFile, Scene theScene, Stage theStage){

        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        theImageViewer.setImage(newImage);
        uneditedImage = newImage;
        setCurrentFilter(null);

        if(newImage.getWidth() > 1300){
            // Calculate the scale change to set correct height value in order to preserve the aspect ratio
            double imgWidth = newImage.getWidth();
            double imgScale = 1300/imgWidth;
            double newImgHeight = newImage.getHeight() * imgScale;

            theStage.setWidth(1300);
            theStage.setHeight(newImgHeight);
            theImageViewer.fitWidthProperty().bind(theScene.widthProperty());

        }else{
            theStage.setWidth(newImage.getWidth());
            theStage.setHeight(newImage.getHeight());
            theImageViewer.fitWidthProperty().bind(theScene.widthProperty());
        }

        theImageViewer.setPreserveRatio(true);

    }

//    @Override
//    public void updateImageView(File inputFile, Double sceneWidth, Double sceneHeight) {
//        String fileURL = inputFile.getAbsolutePath();
//        Image newImage = new Image("file:"+fileURL);
//        theImageViewer.setFitWidth(sceneWidth);
//        theImageViewer.setFitHeight(sceneHeight);
//        theImageViewer.setImage(newImage);
//        if(currentFilter != null){
//            theImageViewer = currentFilter.manipulate(theImageViewer,10);
//        }
//
//        theImageViewer.setPreserveRatio(true);
//
//
//    }

    @Override
    public void setCurrentFilter(ImageFilter filter) {
        currentFilter =filter;
        if(currentFilter != null) {
            uneditedImage = theImageViewer.getImage();
            theImageViewer = currentFilter.manipulate(theImageViewer, 10);
        }
        else{
            theImageViewer.setEffect(null);}

    }


}
