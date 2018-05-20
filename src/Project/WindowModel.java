package Project;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Stack;


public class WindowModel  {
    private ImageView theImageViewer;
    private Stack<ModifiedImage> changeStack;
    private Stack<ModifiedImage> cacheStack;
    private WindowView theView;


    /**
     *
     * @return A new ImageView
     *
     */
    public WindowModel (WindowView stage){
        theView = stage;
    }

    public ImageView createCenterComponent() {
        return theImageViewer = new ImageView();
    }

    /**
     *
     * @param inputFile The directory of the new image
     * @return Updated ImageView
     * @precondition inputFile nor theImageView is null
     */


    public void updateImageView(File inputFile){
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
        theView.updateScene(theImageViewer);
    }

    public ImageView getImageView(){
        return  theImageViewer;
    }

    /**
     *
     * @param modifier The modifier to be set
     * @postcondition ImageView is modified
     */

    public void setCurrentModifier(ImageModifier modifier) {
        if(modifier != null) {
            ModifiedImage newMImage = new ModifiedImage(changeStack.peek().getImage(), modifier) ;
            theImageViewer= newMImage.getModifier().activate(theImageViewer);
            changeStack.push(newMImage);
        }
    }
    /**
     * @precondition ImageView is modified
     **/

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

    public void  undoRemoval(){
        if(cacheStack.size() >0 ) {
            changeStack.push(cacheStack.pop());
            theImageViewer.setImage(changeStack.peek().getImage());
        }
    }
}

