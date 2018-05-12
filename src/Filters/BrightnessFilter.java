package Filters;

import Framework.ImageFilter;
import Framework.SliderBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Slider;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class BrightnessFilter extends ImageFilter {

    private Image originalImage;
    private Image returnImage;
    private String filterName = "Brightness";
    private float intensity;
    private Slider theSlider;

    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        returnImage = input.getImage();
        ImageView resultView = input;
        initSlider();
        theSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    intensity = newValue.floatValue();
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(returnImage, null);
                    RescaleOp operation = new RescaleOp(intensity, 0, null);
                    bufferedImage = operation.filter(bufferedImage, bufferedImage);

                    Image newImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    resultView.setImage(newImage);

            }
        });
        return resultView;
    }


    @Override
    public ImageView deactivate(ImageView input) {
        input.setEffect(null);
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

        SliderBox.display("Intensity",theSlider);
    }
}


