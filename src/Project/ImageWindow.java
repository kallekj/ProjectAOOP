package Project;
import javafx.scene.SnapshotParameters;
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
     *
     */
    public ImageWindow (Stage primaryStage){
        super(primaryStage);
    }

    /**
     *
     * @return A new ImageView
     *
     */
    @Override
    public ImageView createCenterComponent() {
        return theImageViewer = new ImageView();
    }

    /**
     *
     * @param inputFile The directory of the new image
     * @return Updated ImageView
     * @precondition inputFile nor theImageView is null
     */

    @Override
    public ImageView updateImageView(File inputFile){
        changeStack = new Stack<ModifiedImage>();
        cacheStack = new Stack<ModifiedImage>();
        cacheStack.clear();
        changeStack.clear();
        String fileURL = inputFile.getAbsolutePath();
        Image newImage = new Image("file:"+fileURL);
        ModifiedImage rootMImage = new ModifiedImage(newImage, null  );
        changeStack.push(rootMImage);
        theImageViewer.setImage(newImage);
        theImageViewer.setEffect(null);
        return theImageViewer;
    }

    /**
     *
     * @param modifier The modifier to be set
     * @postcondition ImageView is modified
     */
    @Override
    public void setCurrentModifier(ImageModifier modifier) {
        if(modifier != null) {
            ModifiedImage newMImage = new ModifiedImage(changeStack.peek().getImage(), modifier) ;
            theImageViewer= newMImage.getModifier().activate(theImageViewer);
           // newMImage.setImage(theImageViewer.getImage());
            changeStack.push(newMImage);
        }
    }
    /**
     * @precondition ImageView is modified
     **/
    @Override
    public void removeModifier() {
        if (changeStack.peek().getModifier() != null) {
            changeStack.peek().setImage(theImageViewer.getImage());
            theImageViewer = changeStack.peek().getModifier().deactivate(theImageViewer);
            cacheStack.push(changeStack.pop());
        }
    }
    /**
     * @precondition ImageView is modified
     * @postcondition The ImageView is no longer modified
     **/
    @Override
    public void removeAllModifiers() {
        while(changeStack.peek().getModifier() != null){
            changeStack.pop();
        }
        theImageViewer.setImage(changeStack.peek().getImage());
        theImageViewer.setEffect(null);
    }
    /**
     * @precondition  removeModifier() has been used
     * @postcondition ImageView is modified
     **/
    @Override
    public void  undoRemoval(){
        if(cacheStack.size() >0 ) {
            changeStack.push(cacheStack.pop());
            theImageViewer.setImage(changeStack.peek().getImage());
            //setCurrentModifier(changeStack.peek().getModifier());
        }
    }
}

