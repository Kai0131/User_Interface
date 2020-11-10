import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    static final int BACK_DIS = 300;
    static final float dx1 = 3.0f;
    static final float dx2 = 4.0f;
    static final float dx3 = 6.0f;
    Boolean direction;
    float enemy_x, enemy_y = 350;
    float dx = dx1;
    float bdx = 6.0f;
    Image enemy = new Image("/resources/img/enemy.png");
    ImageView enemyv = new ImageView(enemy);
    Group enemy_group;
    MiniGame parent;
    Boolean back = false;
    int backdis = 0;
    Bounds bound;
    AnimationTimer timer;

    public Enemy (MiniGame p,Boolean d,int level) {
        if(level == 1) {
            dx = dx1;
        } else if (level == 2) {
            dx = dx2;
        } else {
            dx = dx3;
        }
        parent = p;
        direction = d;
        enemy_group = p.enemy_group;
        bound = parent.get_player_image_view().getBoundsInLocal();
    }

    void create_enemy() {
        enemyv.setPreserveRatio(true);
        if(direction == true) {
            enemy_x =1390;
        } else {
            enemyv.setScaleX(-1);
            enemy_x = -210;
        }
        enemyv.setX(enemy_x);
        enemyv.setY(enemy_y);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(parent.gameend == true) enemyv.setDisable(true);
                if(enemyv.isDisabled() == true) {
                    timer.stop();
                    return;
                }
                if((direction == true&& enemy_x <= 680)||(direction == false&& enemy_x >= 380))  {
                    parent.lifemius1();
                    backdis = BACK_DIS;
                    back = true;
                }
                if(back == false) {
                    handle_animation();
                } else  {
                    handle_animation_back();
                }
            }
        };
        timer.start();
        parent.enemy_group.getChildren().add(enemyv);
    }
    void handle_animation() {

        if(direction == true) {
            enemy_x -= dx;
        } else {
             enemy_x += dx;
        }
        enemyv.setX(enemy_x);
        enemyv.setY(enemy_y);
    }

    void handle_animation_back() {
        if(direction == true) {
            enemy_x += bdx;
        } else {
            enemy_x -= bdx;
        }
        backdis -= bdx;
        if(backdis <= 0) {
            back = false;
        }
        enemyv.setX(enemy_x);
        enemyv.setY(enemy_y);
    }
}
