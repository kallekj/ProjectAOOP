package Modifiers;

import Project.ImageModifier;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;

public class FlipX extends ImageModifier {
    private Image originalImage;
    /**
     *
     * @param input The ImageView to be mirrored
     * @return Mirrored ImageView
     * @precondition ImageView not null
     *
     */
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView = input;
        returnImageView.setScaleX(-1);
        WritableImage flippedImage = returnImageView.snapshot(new SnapshotParameters(),null);
        returnImageView.setScaleX(1);
        returnImageView.setImage(flippedImage);
        return returnImageView;
    }
    /**
     *
     * @param input The ImageView to be reset to normal
     * @return Reset ImageView
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setImage(originalImage);
        return input;
    }

}
