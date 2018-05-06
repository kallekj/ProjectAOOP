package Filters;

import Framework.ImageFilter;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GrayScale  extends ImageFilter {
    private String filterName = "Grayscale";

    @Override
    public ImageView manipulate(ImageView input, int intensity) {
        ImageView returnImageView = input;
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        returnImageView.setEffect(grayScale);
        return returnImageView;


    }

    @Override
    public ImageView manipulate() {

        return null;
    }
}
