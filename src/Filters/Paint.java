package Filters;

import Framework.ImageFilter;
import Framework.SliderBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;



public class Paint extends ImageFilter {
    Image originalImage = null;
    double initX = 0;
    double initY = 0;
    int  red= 0;
    int blue = 0;
    int green = 0;
    java.awt.Color currentColor = Color.black;
    private Slider redSlider;
    private Slider blueSlider;
    private Slider greenSlider;
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView =input;
        initSlider();
        final double maxX = input.getImage().getWidth();
        final double maxY = input.getImage().getHeight();
        BufferedImage image = SwingFXUtils.fromFXImage(input.getImage(),null);
        redSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                red = newValue.intValue();
                currentColor = new java.awt.Color(red,blue,green);
            }
        });
        greenSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                green = newValue.intValue();
                currentColor = new java.awt.Color(red,blue,green);
            }
        });
        blueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                blue= newValue.intValue();
                currentColor = new java.awt.Color(red,blue,green);
            }
        });
        returnImageView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                initX = event.getX();
                initY = event.getY();
                event.consume();
            }
        });
        returnImageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getY();
                if(event.getSceneX() < maxX && event.getSceneY() < maxY){
                    Graphics2D graphics = (Graphics2D) image.getGraphics();
                    graphics.setPaint(currentColor);
                    graphics.fillOval((int)x,(int)y,image.getWidth()/100,image.getHeight()/100);
                    returnImageView.setImage(SwingFXUtils.toFXImage(image,null));
                }

            }
        });

return returnImageView;
    }

    @Override
    public ImageView deactivate(ImageView input) {
        return null;
    }
    private void initSlider(){
        redSlider= new Slider(0,255,0);
        redSlider.setShowTickLabels(true);
        redSlider.setShowTickMarks(true);
        redSlider.setMajorTickUnit(10);
        redSlider.setMinorTickCount(5);
        redSlider.setPrefWidth(300);
        redSlider.setAccessibleText("RED");

        greenSlider= new Slider(0,255,0);
        greenSlider.setShowTickLabels(true);
        greenSlider.setShowTickMarks(true);
        greenSlider.setMajorTickUnit(10);
        greenSlider.setMinorTickCount(5);
        greenSlider.setPrefWidth(300);
        greenSlider.setAccessibleText("GREEN");

        blueSlider= new Slider(0,255,0);
        blueSlider.setShowTickLabels(true);
        blueSlider.setShowTickMarks(true);
        blueSlider.setMajorTickUnit(10);
        blueSlider.setMinorTickCount(5);
        blueSlider.setPrefWidth(300);
        blueSlider.setAccessibleText("BLUE");


        SliderBox.display("Color",redSlider);
        SliderBox.addSlider("GREEN", greenSlider);
        SliderBox.addSlider("BLUE",blueSlider);
    }
}
