package Modifiers;

import Framework.ImageModifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.awt.*;

public class Black_Circle extends ImageModifier {
    Image originalImage = null;
    @Override
    /**
     * @return ImageView with black circle painted on image, edge to edge
     * @param input  The ImageView containing the image to be added a black circle
     *
     */
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(new java.awt.Color(0,0,0));
        graphics2D.fillOval (0,0,(int)input.getImage().getWidth(),(int)input.getImage().getHeight());
        ImageView returnImageView = input;
        Image newImage = SwingFXUtils.toFXImage(bufferedImage,null);
        returnImageView.setImage(newImage);

        return returnImageView;
    }
    /**
     * @return ImageView without black circle
     * @param input , the ImageView containing the image with a black circle to be removed
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        input.setImage(originalImage);
        return input;
    }


}
