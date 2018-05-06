package Filters;

import Framework.ImageFilter;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FlipX extends ImageFilter {
    @Override
    public ImageView manipulate(ImageView input, int intensity) {
        ImageView returnImageView = input;

        returnImageView.setRotate(180);
        return returnImageView;
    }

    @Override
    public ImageView manipulate() {

        return null;
    }
}
