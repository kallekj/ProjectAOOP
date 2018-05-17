package Modifiers;

import Framework.ImageModifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Chess extends ImageModifier {
    Image originalImage = null;
    @Override
    /**
     * @param input , the ImageView containing the image to be added a back chess board
     * @return ImageView with black chessboard painted on image
     */
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(new java.awt.Color(0,0,0));
        for(int x = 0 ; x<8; x++){
            int offset = x%2;
            for(int y = 0; y<8;y++){
                graphics2D.fillRect ((int) (x* (input.getImage().getWidth()/8)), (int) (y* (input.getImage().getHeight()/4) + ( offset *(input.getImage().getHeight()/8) )),(int)(input.getImage().getWidth()/8),(int)(input.getImage().getHeight()/8));
            }
        }


        ImageView returnImageView = input;
        Image newImage = SwingFXUtils.toFXImage(bufferedImage,null);
        returnImageView.setImage(newImage);

        return returnImageView;
    }
    /**
     * @param input , the ImageView with chessboard drawn on the image
     * @return ImageView with black chessboard removed from image
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
        input.setImage(originalImage);
        return input;
    }


}
