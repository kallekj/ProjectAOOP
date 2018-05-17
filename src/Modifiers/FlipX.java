package Modifiers;

import Framework.ImageModifier;
import javafx.scene.image.ImageView;

public class FlipX extends ImageModifier {

    /**
     *
     * @param input The ImageView to be mirrored
     * @return Mirrored ImageView
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
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        input.setScaleX(1);
        return input;
    }

}
