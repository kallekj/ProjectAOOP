package Framework;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;


public class ImageWindow extends Window {
    private ImageView theImageViewer;
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
        theImageViewer.setEffect(null);
        if(currentFilter != null){
            removeFilter();
        }
        if(newImage.getWidth() > 1300){
            // Calculate the scale change to set correct height value in order to preserve the aspect ratio
            double imgWidth = newImage.getWidth();
            double imgScale = 1300/imgWidth;
            double newImgHeight = newImage.getHeight() * imgScale;

            theStage.setWidth(1300);
            theStage.setHeight(newImgHeight);


        }else{
            theStage.setWidth(newImage.getWidth());
            theStage.setHeight(newImage.getHeight());

        }
        theImageViewer.fitWidthProperty().bind(theStage.widthProperty());
        theImageViewer.fitHeightProperty().bind(theStage.heightProperty());


        theImageViewer.setPreserveRatio(true);


    }


    @Override
    public void setCurrentFilter(ImageFilter filter) {
        if(filter != null) {
            if(currentFilter!= null){
               removeFilter();
            }
            currentFilter = filter;
            theImageViewer = currentFilter.activate(theImageViewer);
        }
    }
    public void removeFilter(){
        theImageViewer = currentFilter.deactivate(theImageViewer);
        currentFilter = null;
    }


}
