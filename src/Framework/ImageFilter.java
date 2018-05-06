package Framework;
import Filters.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ImageFilter {
    public abstract ImageView manipulate(ImageView input, int intensity);
    public abstract ImageView manipulate();
}
