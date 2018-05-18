package Project;

import javafx.scene.image.Image;

public class ModifiedImage {
    private final Image image;
    private  final ImageModifier modifier;

    /**
     *
     * @param inputImage The current image
     * @param inputModifier The modifier to be linked
     */
    public ModifiedImage(Image inputImage, ImageModifier inputModifier){
        image = inputImage;
        modifier = inputModifier;
    }

    /**
     *
     * @return the Image
     */
    public Image getImage(){
        return image;
    }

    /**
     *
     * @return The ImageModifier
     */
    public ImageModifier getModifier() {
        return modifier;
    }
}
