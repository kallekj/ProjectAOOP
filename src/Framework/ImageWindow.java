package Framework;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.util.Stack;


public class ImageWindow extends Window {
    private ImageView theImageViewer;
    private Stack<ModifiedImage> changeStack;
    private Stack<ModifiedImage> cacheStack;
    /**
     *
     * @param primaryStage to be initialized
     * @return A new ImageWindow
     */
    public ImageWindow (Stage primaryStage){
        super(primaryStage);
    }
    @Override
    public ImageView createCenterComponent() {
        return theImageViewer = new ImageView();
    }

    @Override
    public ImageView updateImageView(File inputFile){
        changeStack = new Stack<ModifiedImage>();
        cacheStack = new Stack<ModifiedImage>();
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        ModifiedImage rootMImage = new ModifiedImage(newImage, null  );
        changeStack.push(rootMImage);
        theImageViewer.setImage(newImage);
        theImageViewer.setEffect(null);
        return theImageViewer;
    }


    @Override
    public void setCurrentModifier(ImageModifier modifier) {
        if(modifier != null) {
        ModifiedImage newMImage = new ModifiedImage(changeStack.peek().getImage(), modifier) ;
        theImageViewer= newMImage.getModifier().activate(theImageViewer);
        changeStack.push(newMImage);
        }
    }
    @Override
    public void removeModifier() {
        if (changeStack.peek().getModifier() != null) {
            theImageViewer = changeStack.peek().getModifier().deactivate(theImageViewer);
            cacheStack.push(changeStack.pop());
        }
    }

    @Override
    public void removeAllModifiers() {
        while(changeStack.peek().getModifier() != null){
            changeStack.pop();
        }
        theImageViewer.setImage(changeStack.peek().getImage());
        theImageViewer.setEffect(null);
    }
    @Override
    public void  undoRemoval(){
        if(cacheStack.size() >0 ) {
            changeStack.push(cacheStack.pop());
            setCurrentModifier(changeStack.peek().getModifier());
        }
    }
}
