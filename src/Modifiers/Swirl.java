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
    private String filterName = "Swirl";
    private Double intensity;
    private Slider theSlider;
    /**
     *
     * @param input The ImageView containing a image to be swirled
     * @return ImageView with its image swirled
     */
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        returnImage = input.getImage();
        ImageView resultView = input;

        initSlider();
        double x0 = 0.5 * ( input.getImage().getWidth() -1) ;
        double y0 = 0.5 *(input.getImage().getHeight() -1);
        theSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                intensity = newValue.doubleValue() ;
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(returnImage,null);
                BufferedImage tempBufferedImage = new BufferedImage((int)bufferedImage.getWidth(),(int)bufferedImage.getHeight(),BufferedImage.TYPE_INT_ARGB);
                for(int x = 0; x<bufferedImage.getWidth();x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        double deltaX = x0 - x;
                        double deltaY = y - y0;
                        double radius = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        double angle = Math.PI / 256 * radius * intensity;
                        int tempX = (int) (-deltaX * Math.cos(angle) + deltaY * Math.sin(angle) + x0);
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
