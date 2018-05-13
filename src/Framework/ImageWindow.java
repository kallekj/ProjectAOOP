package Framework;
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
    public ImageView updateImageView(File inputFile){
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        if(currentFilter != null){
            removeFilter();
        }
        theImageViewer.setImage(newImage);
        theImageViewer.setEffect(null);

     return theImageViewer;
    }


    @Override
    public void setCurrentFilter(ImageFilter filter) {
        theImageViewer = super.getImageView();
        if(filter != null) {
            if(currentFilter!= null){
               removeFilter();
            }
            currentFilter = filter;
           theImageViewer= currentFilter.activate(theImageViewer);
        }
    }
    public void removeFilter(){
        theImageViewer = currentFilter.deactivate(theImageViewer);
        currentFilter = null;
    }


}
