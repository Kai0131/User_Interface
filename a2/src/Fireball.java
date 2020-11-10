import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Fireball {
    static final int leftlimit1 = 275;
    static final int leftlimit2 = 375;
    static final int leftlimit3 = 475;

    static final int rightlimit1 = 940;
    static final int rightlimit2 = 840;
    static final int rightlimit3 = 740;

    int leftlitmit = leftlimit1;
    int rightlimit = rightlimit1;
    Boolean direction;
    float fireball_x, fireball_y = 450;
    float dx = 3.0f, dy = 1.5f;
    Image fireball = new Image("/resources/img/fireball2.gif");
    ImageView fireballv = new ImageView(fireball);
    Group fireball_group;
    MiniGame parent;
    AnimationTimer timer;

    Media media = new Media(Paths.get("./src/resources/sound/firecast.mp3").toUri().toString());
    MediaPlayer firecast = new MediaPlayer(media);
    Media media1 = new Media(Paths.get("./src/resources/sound/kill_sound.mp3").toUri().toString());
    MediaPlayer kill_sound = new MediaPlayer(media1);

    public Fireball (MiniGame p,Boolean d,int level) {
        if(level == 1) {
             leftlitmit = leftlimit1;
             rightlimit = rightlimit1;
        } else if(level == 2) {
            leftlitmit = leftlimit2;
            rightlimit = rightlimit2;
        } else {
            leftlitmit = leftlimit3;
            rightlimit = rightlimit3;
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handle_animation();
            }
        };
        parent = p;
        direction = d;
        fireball_group = p.fireball_group;
        fireballv.setX(fireball_x);
        fireballv.setY(fireball_y);

    }

    void create_fireball() {
        fireballv.setPreserveRatio(true);
        if(direction == true) {
            fireballv.setScaleX(-1);
            fireball_x = 575;
        } else {
            fireball_x = 640;
        }
        timer.start();
        firecast.play();
        parent.fireball_group.getChildren().add(fireballv);
    }
    void handle_animation() {
        if(direction == true) {
            if (fireball_x < leftlitmit) {
                fireball_group.getChildren().remove(fireballv);
                parent.scoreminus1();
                timer.stop();
                return;
            } else {
                fireball_x -= dx;
            }
        } else {
            if (fireball_x > rightlimit) {
                fireball_group.getChildren().remove(fireballv);
                parent.scoreminus1();
                timer.stop();
                return;
            } else {
                fireball_x += dx;
            }
        }
        fireballv.setX(fireball_x);
        fireballv.setY(fireball_y);
        //check hit:
        for (Node enemy:parent.getEnemy_group().getChildren()) {
            Bounds bound = enemy.getBoundsInLocal();
            if (fireballv.intersects(bound)) {
                timer.stop();
                fireball_group.getChildren().remove(fireballv);
                parent.getEnemy_group().getChildren().remove(enemy);
                enemy.setDisable(true);
                parent.scoreplus1();
                parent.enemymius1();
                kill_sound.play();
                break;
            }
        }
    }
}
