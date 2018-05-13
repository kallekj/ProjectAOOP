package Filters;

import Framework.ImageFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.awt.*;

public class Black_Circle extends ImageFilter {
    Image originalImage = null;
    @Override
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

    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        input.setImage(originalImage);
        return input;
    }


}
