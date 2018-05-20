package Modifiers;

import Project.ImageModifier;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RedFilter extends ImageModifier {
        private Image originalImage;
    /**
     *
     * @param input The ImageView to be filtered
     * @return ImageView with its image only containing its red properties
     *  @precondition ImageView not null
     */
    @Override
    public ImageView activate(ImageView input) {
        ImageView returnImageView = input;
        originalImage = returnImageView.getImage();
        Blend noBlue = new Blend(BlendMode.BLUE,returnImageView.getEffect(),
                new ColorInput(0,0,returnImageView.getImage().getWidth(),
                        returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));

        returnImageView.setEffect(noBlue);
        Blend noGreen = new Blend(BlendMode.GREEN, returnImageView.getEffect(),
                new ColorInput(0,0,returnImageView.getImage().getWidth(),
                        returnImageView.getImage().getHeight(), Color.rgb( 0 , 0,0 )));

        returnImageView.setEffect(noGreen);
        returnImageView.setImage(returnImageView.snapshot(new SnapshotParameters(),null));
        returnImageView.setEffect(null);
        return returnImageView;
    }
    /**
     *
     * @param input The filtered ImageView to be reset
     * @return ImageView with its image colors back to normal
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setImage(originalImage);
        return input;
    }
}
