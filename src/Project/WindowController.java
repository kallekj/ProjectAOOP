package Project;
import Modifiers.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowController extends MenuBar {
    /**
     *
     * @param primaryStage The Viewer for the Controller to control
     */
  
    public WindowController(WindowView primaryStage){

        WindowModel projectModel = primaryStage.getTheModel();
        prefWidthProperty().bind(primaryStage.widthProperty());

        // New image/canvas
        MenuItem itemCreator = new MenuItem("New Image");
        itemCreator.setOnAction(event -> {
            // Creates the "Create window" and opens it
            ImageCreatorBox userInput = new ImageCreatorBox();
            userInput.display();
            // If the users clicks on the "Create" button, this will run and create a new image with the selected color
            // When the image is created it will ask the user where to save it
            userInput.getCreateButton().setOnAction(event1 -> {
                int selectedWidth = userInput.getSelectedWidth();
                int selectedHeight = userInput.getSelectedHeight();
                Color selectedColor = userInput.getSelectedColor();
                if((selectedColor!=null) &&(selectedWidth != 0) && (selectedHeight != 0)){
                    userInput.closeWindow();
                    WritableImage createdImage = new WritableImage(selectedWidth,selectedHeight);
                    PixelWriter pixelWriter = createdImage.getPixelWriter();
                    for(int x = 0; x < createdImage.getWidth(); x++){
                        for (int y = 0; y < createdImage.getHeight(); y++){
                            pixelWriter.setColor(x,y,selectedColor);
                        }
                    }
                    RenderedImage saveImage = SwingFXUtils.fromFXImage(createdImage,null);
                    FileChooser fileSaver = new FileChooser();
                    fileSaver.setTitle("Save New Image");
                    File newFile = fileSaver.showSaveDialog(primaryStage);
                    if(newFile != null){
                        try{ ImageIO.write(saveImage,"png",new File( newFile.toString() + ".png"));
                            projectModel.updateImageView(newFile);

                        }
                        catch(IOException ex){
                            Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE,null,ex);

                        }
                    }

                }

            });

        });



        // ***************************************** FILE MENU *********************************************************


        // File, this is the file menu
        Menu file = new Menu("File");

        // This Section handles the open file operation
        MenuItem itemOpen = new MenuItem("Open");
        FileChooser itemSelector = new FileChooser();
        itemSelector.setTitle("Select Image");
        itemOpen.setOnAction(event -> {
            File selectedItem = itemSelector.showOpenDialog( primaryStage);
            if(selectedItem != null){
                projectModel.updateImageView(selectedItem);
            }
        });

        // This section handles the save file operation
        MenuItem itemSave = new MenuItem("Save");
        itemSave.setOnAction(event -> {
            Image FXImage = projectModel.getImageView().snapshot(new SnapshotParameters(), null);
            RenderedImage saveImage = SwingFXUtils.fromFXImage(FXImage,null);
            FileChooser fileSaver = new FileChooser();
            fileSaver.setTitle("Save Edited Image");

            File newFile = fileSaver.showSaveDialog(primaryStage);
            if(newFile != null){
                try{ ImageIO.write(saveImage,"png",new File( newFile.toString() + ".png"));

                }
                catch(IOException ex){
                    Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE,null,ex);

                }
            }

        });

        // This section handles the exit operation
        MenuItem itemExit = new MenuItem("Exit", null);
        itemExit.setOnAction(e -> Platform.exit());

        // Adds all the "file menu" items to the file menu
        file.getItems().addAll(itemOpen,itemCreator, itemSave, itemExit);



        // ************************************ FILTERS/MODIFIERS ******************************************************


        // This section handles filters
        Menu modifiers = new Menu ("Modify");

        // Swirl
        MenuItem swirl = new MenuItem("Swirl");
        swirl.setOnAction(event -> projectModel.setCurrentModifier(new Swirl()));

        // GrayScale
        MenuItem grayScale = new MenuItem("Grayscale");
        grayScale.setOnAction(event -> projectModel.setCurrentModifier(new GrayScale()) );

        // FlipX
        MenuItem flipX = new MenuItem("Flip X");
        flipX.setOnAction(event -> projectModel.setCurrentModifier(new FlipX()));

        // Red Filter
        MenuItem red_Filter = new MenuItem("Red Filter");
        red_Filter.setOnAction(event ->  projectModel.setCurrentModifier(new RedFilter()));

        //Inverted Filter
        MenuItem invertFilter = new MenuItem("Invert Colors");
        invertFilter.setOnAction(event -> projectModel.setCurrentModifier(new InvertFilter()));

        // Brightness filter
        MenuItem brightness = new MenuItem("Brightness");
        brightness.setOnAction(event -> projectModel.setCurrentModifier(new BrightnessFilter()));

        // Paint operation
        MenuItem paint = new MenuItem("Paint");
        paint.setOnAction(event -> projectModel.setCurrentModifier(new Paint()));

        // Sub Menu with patterns
        Menu patterns = new Menu("Patterns");

        // Vertical stripes in Sub Menu
        MenuItem vertical_Stripes = new MenuItem("Vertical Stripes");
        vertical_Stripes.setOnAction(event -> projectModel.setCurrentModifier(new VerticalStripes()));

        // Chess in Sub Menu
        MenuItem chess = new MenuItem("Chess");
        chess.setOnAction(event -> projectModel.setCurrentModifier(new Chess()));

        // Black Circle in Sub Menu
        MenuItem black_Circle = new MenuItem("Black Circle");
        black_Circle.setOnAction(event -> projectModel.setCurrentModifier(new BlackCircle()));

        // Restore image to original
        MenuItem noFilter = new MenuItem("No Modifier");
        noFilter.setOnAction(event -> projectModel.removeAllModifiers());

        // Add all Sub Menu patterns to the menu
        patterns.getItems().addAll(vertical_Stripes,chess,black_Circle);

        // Add all filters and patterns to the filter menu
        modifiers.getItems().addAll(swirl,grayScale,flipX,red_Filter, paint,brightness,invertFilter,patterns,noFilter);



        // ***************************************** HELP MENU *********************************************************


        // Help menu
        Menu helpMenu = new Menu("Help");

        // The "Help" section
        MenuItem help = new MenuItem("Help");

        // Reads the help file
        File helpFile = new File(new File("").getAbsolutePath() + "/src/Texts/help");
        String helpText ="";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(helpFile));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                helpText = helpText + line +"\n";
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // "Casts" the String from the bufferedReader to a Text object
        Text helpTextNode = new Text(helpText);

        // Open a new window and displays the text
        help.setOnAction(event -> NodeBox.display("Help",helpTextNode));

        // The "About" section, works exactly the same as the "Help"
        MenuItem about = new MenuItem("About");
        File aboutFile = new File(new File("").getAbsolutePath() + "/src/Texts/about");
        String aboutText ="";
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(aboutFile));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                aboutText = aboutText + line +"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Text aboutTextNode = new Text(aboutText);
        about.setOnAction(event -> NodeBox.display("About", aboutTextNode ));

        // Finds where the JavaDoc index.html file location and creates a WebView+WebEngine to read the html files.
        File javaDoc = new File( (new File("").getAbsolutePath() + "/JavaDoc/index.html"));
        WebView javaDocView = new WebView();
        WebEngine webEngine = javaDocView.getEngine();

        // Creates the JavaDoc menu
        MenuItem openJavaDoc = new MenuItem("JavaDoc");
        // Opens the JavaDoc
        openJavaDoc.setOnAction(event -> {
            webEngine.load(javaDoc.toURI().toString());
            NodeBox.display("Help",javaDocView);

        });

        // Adds all the "help menu" items to the help menu
        helpMenu.getItems().addAll(help, about,openJavaDoc);



        //**********************************ADD EVERYTHING TO THE MENU BAR**********************************************

        // Adds all the menus to the menu bar
        getMenus().addAll(file, modifiers, helpMenu);
    }
}
