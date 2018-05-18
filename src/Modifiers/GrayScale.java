package Modifiers;

import Project.ImageModifier;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class GrayScale  extends ImageModifier {
    private String filterName = "Grayscale";
    /**
     *
     * @param input The ImageView to be grayscaled
     * @return ImageView only containing it's gray colors
     */
    @Override
    public ImageView activate (ImageView input) {
        ImageView returnImageView = input;
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        returnImageView.setEffect(grayScale);
        return returnImageView;
    }
    /**
     *
     * @param input The ImageView to be reset to colored
     * @return original ImageView
     */

    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        return input;
    }
}
