package Modifiers;

import Project.ImageModifier;
import javafx.scene.image.ImageView;

public class FlipX extends ImageModifier {

    /**
     *
     * @param input The ImageView to be mirrored
     * @return Mirrored ImageView
     * @precondition ImageView not null
     *
     */
    @Override
    public ImageView activate(ImageView input) {

        ImageView returnImageView = input;
        if(input.getScaleX() == -1){
           returnImageView.setScaleX(1);
        }
        else{
            returnImageView.setScaleX(-1);
        }
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
        input.setScaleX(1);
        return input;
    }

}
