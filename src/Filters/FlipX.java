package Filters;

import Framework.ImageFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlipX extends ImageFilter {
    Image originalImage = null;
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView = input;
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        BufferedImage mirroredImage = new BufferedImage((int)input.getImage().getWidth(),(int)input.getImage().getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int y = 0 ;y < input.getImage().getHeight()-1; y++){
            for(int leftX = 0, rightX = (int) input.getImage().getWidth()-1; leftX < input.getImage().getWidth()-1;leftX++,rightX--){
                int pixel = bufferedImage.getRGB(leftX,y);
                mirroredImage.setRGB(rightX,y,pixel);
            }
        }
        Image newImage = SwingFXUtils.toFXImage(mirroredImage,null);
        returnImageView.setImage(newImage);

        return returnImageView;
    }

    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        input.setImage(originalImage);
        return input;
    }

}
