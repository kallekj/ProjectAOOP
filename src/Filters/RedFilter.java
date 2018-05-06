package Filters;

import Framework.ImageFilter;
import javafx.beans.binding.Bindings;
import javafx.scene.CacheHint;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RedFilter extends ImageFilter {
    @Override
    public ImageView manipulate(ImageView input, int intensity) {
        ImageView returnImageView = input;
        Blend noBlue = new Blend(BlendMode.BLUE,returnImageView.getEffect(), new ColorInput(0,0,returnImageView.getImage().getWidth(),  returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));
        returnImageView.setEffect(noBlue);
        Blend noGreen = new Blend(BlendMode.GREEN, returnImageView.getEffect(), new ColorInput(0,0,returnImageView.getImage().getWidth(),  returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));
        returnImageView.setEffect(noGreen);
        return returnImageView;
    }

    @Override
    public ImageView manipulate() {

        return null;
    }
}
