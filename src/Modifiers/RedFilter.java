package Modifiers;

import Framework.ImageModifier;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RedFilter extends ImageModifier {

    /**
     *
     * @param input The ImageView to be filtered
     * @return ImageView with its image only containing its red properties
     */
    @Override
    public ImageView activate(ImageView input) {
        ImageView returnImageView = input;
        Blend noBlue = new Blend(BlendMode.BLUE,returnImageView.getEffect(), new ColorInput(0,0,returnImageView.getImage().getWidth(),  returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));
        returnImageView.setEffect(noBlue);
        Blend noGreen = new Blend(BlendMode.GREEN, returnImageView.getEffect(), new ColorInput(0,0,returnImageView.getImage().getWidth(),  returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));
        returnImageView.setEffect(noGreen);
        return returnImageView;
    }
    /**
     *
     * @param input The filtered ImageView to be reset
     * @return ImageView with its image colors back to normal
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        return input;
    }
}
