package Modifiers;

import Project.ImageModifier;
import Project.SliderBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

public class Swirl extends ImageModifier {
    private Image originalImage;
    private Image returnImage;
    private Double intensity;
    private Slider theSlider;
    /**
     *
     * @param input The ImageView containing a image to be swirled
     * @return ImageView with its image swirled
     *  @precondition ImageView not null
     */
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        returnImage = input.getImage();
        ImageView resultView = input;

        initSlider();

        // The swirl filter works as following:
        // It rotates the image in a none linear fashion, the angle theta is a function of the distance from the center.
        // Sx is source pixel in position x, Sy is source pixel in position y, x0 is center in x-axis, y0 is center in y-axis
        // Intensity is how much it should swirl -> Value from the slider
        // fx = (sx - x0)*Cos((Pi/256) * radius * intensity)) - (sy - y0)*Sin((Pi/256) * radius * intensity))
        // fy = (sx - x0)*Sin((Pi/256) * radius * intensity)) + (sy - y0)*Cos((Pi/256) * radius * intensity))

        // This algorithm is based upon Swirl.java from the Book "Computer Science:   An Interdisciplinary Approach" By Robert Sedgewick and Kevin Wayne.

        // Calculates the center position, or "mid-way" position of the image
        double x0 = 0.5 * (input.getImage().getWidth() - 1) ;
        double y0 = 0.5 *(input.getImage().getHeight() - 1);

        // If the slider value changes, swirl the image
        theSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                intensity = newValue.doubleValue() ;
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(returnImage,null);
                BufferedImage tempBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_ARGB);
                for(int x = 0; x<bufferedImage.getWidth();x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        double deltaX = x - x0;
                        double deltaY = y - y0;
                        double radius = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        double angle = Math.PI / 256 * radius * intensity;
                        int tempX = (int) (deltaX * Math.cos(angle) - deltaY * Math.sin(angle) + x0);
                        int tempY = (int) (deltaX * Math.sin(angle) + deltaY * Math.cos(angle) + y0);
                        if (tempX >= 0 && tempX < bufferedImage.getWidth() && tempY >= 0 && tempY < bufferedImage.getHeight()) {
                            tempBufferedImage.setRGB(x, y, bufferedImage.getRGB(tempX, tempY));
                        }
                    }
                }
                Image newImage = SwingFXUtils.toFXImage(tempBufferedImage,null);
                resultView.setImage(newImage);


            }
        });

        return resultView;
}
    /**
     *
     * @param input The ImageView containing an image to be reset
     * @return ImageView with its image reset
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setImage(originalImage);
        return input;
    }

    private void initSlider(){
        theSlider = new Slider(0,10,0);
        theSlider.setShowTickLabels(true);
        theSlider.setShowTickMarks(true);
        theSlider.setMajorTickUnit(10);
        theSlider.setMinorTickCount(5);
        theSlider.setPrefWidth(300);
        intensity = 0.0;

        SliderBox.display("Intensity",theSlider);
    }

}
