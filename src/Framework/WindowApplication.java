package Framework;


import javafx.application.Application;
import javafx.stage.Stage;


public class WindowApplication extends Application {
    public static void main(String args[]){
    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageWindow theImageWindow = new ImageWindow(primaryStage);

    }
}
