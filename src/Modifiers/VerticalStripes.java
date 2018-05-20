package Modifiers;

import Project.ImageModifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.image.Image;


import java.awt.*;

public class VerticalStripes extends ImageModifier {
    private Image originalImage ;
    /**
     *
     * @param input The ImageView containing an image to get black vertical stripes added ontop
     * @return ImageView with black vertical stripes on top of its image
     *  @precondition ImageView not null
     */

    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(new java.awt.Color(0,0,0));
        for(int x = 0 ; x<10; x++){

            graphics2D.fillRect ((int) (x* (input.getImage().getWidth()/10)),0,(int)(input.getImage().getWidth()/20),(int)(input.getImage().getHeight()));

        }
        ImageView returnImageView = input;
        Image newImage = SwingFXUtils.toFXImage(bufferedImage,null);
        returnImageView.setImage(newImage);

        return returnImageView;
    }


    /**
     *
     * @param input The ImageView with black vertical stripes on its image
     * @return ImageView with its image back to normal
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */

    @Override
    public ImageView deactivate(ImageView input) {
        input.setImage(originalImage);
        return input;
    }

}
