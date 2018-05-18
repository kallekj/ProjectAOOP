package Modifiers;

import Project.ImageModifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.image.BufferedImage;


import static javafx.scene.control.ColorPicker.STYLE_CLASS_SPLIT_BUTTON;


public class Paint extends ImageModifier {
    private Image originalImage = null;
    private Color currentColor;
    private Stage colorWindow;

    /**
     *
     * @param input The ImageView used as a Canvas
     * @return ImageView with the users paint on
     *  @precondition ImageView not null
     */
    @Override
    public ImageView activate(ImageView input) {
        originalImage = input.getImage();
        ImageView returnImageView =input;
       // initSlider();
        final double maxX = input.getImage().getWidth();
        final double maxY = input.getImage().getHeight();
        BufferedImage image = SwingFXUtils.fromFXImage(input.getImage(),null);
        colorWindow = new Stage();
        colorWindow.setHeight(200);
        colorWindow.setWidth(400);
        colorWindow.setResizable(false);
        colorWindow.setTitle("Color Selector");
        ColorPicker theColorPicker = new ColorPicker();
        theColorPicker.setValue(Color.WHITE);
        theColorPicker.setStyle(STYLE_CLASS_SPLIT_BUTTON);
        VBox colorBox = new VBox(30);
        theColorPicker.setOnAction((ActionEvent event )-> {currentColor = theColorPicker.getValue();
        colorBox.setBackground(new Background(new BackgroundFill(theColorPicker.getValue(),null,null)));});

        colorBox.setAlignment(Pos.CENTER);
        colorBox.getChildren().add(theColorPicker);


        returnImageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                if(x < maxX && y < maxY && colorWindow.isShowing()){
                    Graphics2D graphics = (Graphics2D) image.getGraphics();
                    java.awt.Color awtColor = new java.awt.Color( (int) (currentColor.getRed() *255),(int)(currentColor.getGreen() *255),(int)(currentColor.getBlue() *255));
                    graphics.setPaint(awtColor);
                    graphics.fillOval((int)x,(int)y,image.getWidth()/100,image.getHeight()/100);
                    returnImageView.setImage(SwingFXUtils.toFXImage(image,null));
                }

            }
        });
        Scene scene = new Scene(colorBox);
        colorWindow.initModality(Modality.WINDOW_MODAL);
        colorWindow.setScene(scene);
        colorWindow.show();
return returnImageView;
    }
    /**
     *
     * @param input The painted ImageView to be reset
     * @return ImageView with its image reset
     * @precondition  activate has been used
     * @postcondition Modifier no longer active
     */
    @Override
    public ImageView deactivate(ImageView input) {
        input.setOnMouseDragged(null);
        input.setImage(originalImage);
        return input;
    }

}
