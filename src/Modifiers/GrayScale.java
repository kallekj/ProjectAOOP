package Modifiers;

import Project.ImageModifier;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.image.Image;


public class GrayScale  extends ImageModifier {
    private Image originalImage;
    /**
     *
     * @param input The ImageView to be grayscaled
     * @return ImageView only containing it's gray colors
     *  @precondition ImageView not null
     */
    @Override
    public ImageView activate (ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView = input;
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        returnImageView.setEffect(grayScale);
        returnImageView.setImage(returnImageView.snapshot(new SnapshotParameters(),null));
        returnImageView.setEffect(null);
        return returnImageView;
    }
    /**
     *
     * @param input The ImageView to be reset to colored
     * @return original ImageView
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */

    @Override
    public ImageView deactivate(ImageView input) {
        input.setImage(originalImage);
        return input;
    }
}
