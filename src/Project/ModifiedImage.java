package Project;

import javafx.scene.image.Image;

public class ModifiedImage {
    private final Image image;
    private  final ImageModifier modifier;
    public ModifiedImage(Image inputImage, ImageModifier inputModifier){
        image = inputImage;
        modifier = inputModifier;
    }
    public Image getImage(){
        return image;
    }

    public ImageModifier getModifier() {
        return modifier;
    }
}
