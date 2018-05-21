package Modifiers;

import Project.ImageModifier;
import Project.SliderBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;


import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class BrightnessFilter extends ImageModifier {

    private Image originalImage;
    private Image returnImage;
    private float intensity;
    private Slider theSlider;
    private SliderBox theBox;

    /**
     * @return ImageView with image colors brightened by the intensity selected in the slider
     * @param input  The ImageView containing the image to be added brightened
     * @precondition  ImageView not null
     */
    @Override
    public ImageView activate(ImageView input) {

        originalImage = input.getImage();
        returnImage = input.getImage();
        ImageView resultView = input;
        initSlider();
        theSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    intensity = newValue.floatValue() *(float) 0.1;
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(returnImage, null);
                    RescaleOp operation = new RescaleOp(intensity + 1 , 0, null);
                    bufferedImage = operation.filter(bufferedImage, bufferedImage);

                    Image newImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    resultView.setImage(newImage);

            }
        });
        return resultView;
    }

    /**
     * @return ImageView with image colors brightened by the intensity selected in the slider
     * @param input , the ImageView containing the image to be reset
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        theBox.close();
        input.setImage(originalImage);
        return input;
    }

    private void initSlider(){
        theSlider = new Slider(1,10,0);
        theSlider.setShowTickLabels(true);
        theSlider.setShowTickMarks(true);
        theSlider.setMajorTickUnit(10);
        theSlider.setMinorTickCount(5);
        theSlider.setPrefWidth(300);
        intensity = 0;
        theBox = new SliderBox();

        theBox.display("Intensity",theSlider);
    }
}


