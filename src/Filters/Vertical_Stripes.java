package Filters;

import Framework.ImageFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Vertical_Stripes extends ImageFilter {
    Image originalImage = null;
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(new java.awt.Color(0,0,0));
        for(int x = 0 ; x<10; x++){

            graphics2D.fillRect ((int) (x* (input.getFitWidth()/10)), (int) 0,(int)(input.getFitWidth()/20),(int)(input.getFitHeight()));

        }


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
