package Modifiers;

import Project.ImageModifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;

public class InvertFilter extends ImageModifier {
    Image originalImage = null;
    /**
     *
     * @param input The ImageView to get its colors inverted
     * @return ImageView with inverted colors
     *  @precondition ImageView not null
     */
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView = input;
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(input.getImage(),null);
        BufferedImage invertedImage = new BufferedImage((int)input.getImage().getWidth(),(int)input.getImage().getHeight(),BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < bufferedImage.getWidth(); x++){
            for(int y = 0 ; y < bufferedImage.getHeight(); y++){
                int pixel = bufferedImage.getRGB(x,y);
                int originalRed = (pixel & 0x00ff0000) >> 16;
                int originalGreen = (pixel & 0x0000ff00) >>8;
                int originalBlue = (pixel & 0x000000ff) ;
                int newRed = (255 - originalRed) << 16;
                int newGreen  = (255 - originalGreen) << 8;
                int newBlue = (255- originalBlue);
                int newPixel = (newRed + newBlue + newGreen);
                invertedImage.setRGB(x,y,newPixel);
            }
        }
        Image newImage = SwingFXUtils.toFXImage(invertedImage,null);
        returnImageView.setImage(newImage);
        return returnImageView;
    }
    /**
     *
     * @param input The ImageView to be mirrored
     * @return ImageView with normal colors
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        ImageView returnView = input;
        returnView.setImage(originalImage);

        return returnView;
    }
}
