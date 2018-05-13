package Framework;
import javafx.scene.image.ImageView;

public abstract class ImageFilter {

    public abstract ImageView activate(ImageView input);
    public abstract ImageView deactivate(ImageView input);

}
