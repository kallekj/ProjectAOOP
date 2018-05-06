package Framework;
import Filters.*;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ImageWindow extends  Window {
    ImageView theImageViewer;
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
    public void updateImageView(File inputFile, Double sceneWidth, Double sceneHeigth) {
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        theImageViewer.setFitWidth(sceneWidth);
        theImageViewer.setFitHeight(sceneHeigth);
        theImageViewer.setImage(newImage);
        if(currentFilter != null){
            theImageViewer = currentFilter.manipulate(theImageViewer,10);
        }

    }

    @Override
    public void setCurrentFilter(ImageFilter filter) {
        currentFilter =filter;
        if(currentFilter != null) {
            theImageViewer = currentFilter.manipulate(theImageViewer, 10);
        }
        else{ theImageViewer.setEffect(null);}
    }


}
