package Filters;

import Framework.ImageModifier;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class GrayScale  extends ImageModifier {
    private String filterName = "Grayscale";

    @Override
    public ImageView activate (ImageView input) {
        ImageView returnImageView = input;
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        returnImageView.setEffect(grayScale);
        return returnImageView;
    }

    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        return input;
    }
}
