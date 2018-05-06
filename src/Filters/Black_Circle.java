package Filters;

import Framework.ImageFilter;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;

public class Black_Circle extends ImageFilter {

    @Override
    public ImageView manipulate(ImageView input, int intensity) {
        ImageView returnImageView = input;
        Circle blackCircle = new Circle(returnImageView.getFitWidth()/2,Color.BLACK);
        WritableImage image = (WritableImage) returnImageView.getImage();

       // Blend shadowBlend = new Blend(BlendMode.ADD,returnImageView.getEffect(), );
        //returnImageView.setEffect(shadowBlend);
        return returnImageView;
    }

    @Override
    public ImageView manipulate() {

        return null;
    }
}
