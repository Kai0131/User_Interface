import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Player {
    Image image = new Image("/resources/img/fire.png");
    Image image_flip = new Image("/resources/img/fire_flip.png");

    public Player() {
    }

    public Image get_image() {
        return image;
    }

    public Image get_image_flip() {
        return image_flip;
    }
}
