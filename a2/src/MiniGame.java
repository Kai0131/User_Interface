import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import static javafx.scene.paint.Color.ANTIQUEWHITE;
import static javafx.scene.paint.Color.RED;


public class MiniGame extends Application {
    static final int W_WIDTH = 1280;
    static final int W_HEIGHT = 620;
    static final int ENEMY_NUM = 20; //20
    static final long WAIT_TIME = 12000; //12000
    static final String heart = Character.toString(0x2764);
    Font font = Font.loadFont(getClass().getResourceAsStream("/resources/SHOWG.TTF"), 40);

    int i = 1;
    Scene scene;
    int level_num;
    int enemy = ENEMY_NUM;
    Text enemy_label;
    int life = 3;
    Text life_label;
    int score = 0;
    Text score_label;
    ImageView player_image_view;
    Group group;
    public Group fireball_group;
    public Group enemy_group;
    Button button;
    Boolean gameend = false;

    public MiniGame() {
        group = new Group();
        fireball_group = new Group();
        enemy_group = new Group();
        button = new Button();
        button.setVisible(false);
    }
    ImageView get_player_image_view (){
        return player_image_view;
    }

    Group getEnemy_group() {
        return enemy_group;
    }

    @Override
    public void start(Stage primaryStage) {
        //start_scene:
        //background
        Scene title_scene = create_title();
        //--------------------------------------------------------------------------------------------
        //scene1
        Scene fail_scene = create_fail();
        //--------------------------------------------------------------------------------------------
        primaryStage.setTitle("Monster vs Monsters");
        primaryStage.setScene(title_scene);
        primaryStage.setWidth(W_WIDTH);
        primaryStage.setHeight(W_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();

        title_scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Scene scene1 = create_scene(1);
                primaryStage.setScene(scene1);
                scene = scene1;
            } else if (event.getCode().equals(KeyCode.Q)) {
                primaryStage.close();
            } else if(event.getCode().equals(KeyCode.DIGIT1)) {
                Scene scene1 = create_scene(1);
                primaryStage.setScene(scene1);
                scene = scene1;
            } else if (event.getCode().equals(KeyCode.DIGIT2)) {
                Scene scene1 = create_scene(2);
                primaryStage.setScene(scene1);
                scene = scene1;
            } else if (event.getCode().equals(KeyCode.DIGIT3)) {
                Scene scene1 = create_scene(3);
                primaryStage.setScene(scene1);
                scene = scene1;
            }
        });

        button.addEventHandler(Failevent.fail, event -> {
            primaryStage.setScene(fail_scene);
            scene = fail_scene;
        });
        button.addEventHandler(Titleevent.title,event -> {
            primaryStage.setScene(title_scene);
            scene = title_scene;
        });
        button.addEventHandler(Level1.one,event -> {
            Scene scene1 = create_scene(1);
            primaryStage.setScene(scene1);
            scene = scene1;
        });
        button.addEventHandler(Level2.two,event -> {
            Scene scene1 = create_scene(2);
            primaryStage.setScene(scene1);
            scene = scene1;
        });
        button.addEventHandler(Level3.three,event -> {
            Scene scene1 = create_scene(3);
            primaryStage.setScene(scene1);
            scene = scene1;
        });
        button.addEventHandler(Quitevent.quit,event -> {
            primaryStage.close();
        });
    }

    Scene create_scene(int level) {
        //config
        level_num = level;
        enemy = ENEMY_NUM;
        group = new Group();
        gameend = false;
        fireball_group = new Group();
        enemy_group = new Group();
        life = 3;
        if(level == 1) {
            score = 0;
        }
        //----------------
        Image background = new Image("/resources/img/background2.png");
        ImageView background_view = new ImageView(background);
        background_view.setPreserveRatio(true);
        background_view.setFitWidth(1280);

        // enemy number
        enemy_label = new Text("Enemies: "+Integer.toString(enemy));
        enemy_label.setFont(font);
        enemy_label.setX(1000);
        enemy_label.setY(580);
        enemy_label.setFill(ANTIQUEWHITE);

        // Score
        score_label = new Text(Integer.toString(score));
        score_label.setFont(font);
        score_label.setX(20);
        score_label.setY(580);
        score_label.setFill(ANTIQUEWHITE);

        // life
        String livestr = "";
        for (int i=1; i <= life;i++) {
            livestr = heart + livestr;
        }
        life_label = new Text (livestr);
        life_label.setX(550);
        life_label.setY(580);
        life_label.setFill(RED);
        life_label.setFont(Font.font("Arial",50));

        //level label
        Text level_label = new Text("Level "+ level);
        level_label.setFont(font);
        level_label.setX(1100);
        level_label.setY(50);
        level_label.setFill(ANTIQUEWHITE);

        //player
        Player player = new Player();
        player_image_view = new ImageView(player.get_image());
        player_image_view.setX(520);
        player_image_view.setY(300);
        //enemy


        //----------------------------------------------------------------------------------
        group = new Group(background_view,level_label,score_label,life_label,enemy_label,
                player_image_view, fireball_group, enemy_group);

        Scene scene = new Scene(group);
        Fireball f = new Fireball(this, false,level_num);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                Fireball fball = new Fireball(this, false,level_num);
                fball.create_fireball();
                player_image_view.setImage(player.get_image_flip());
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                Fireball fball = new Fireball(this, true,level_num);
                fball.create_fireball();
                player_image_view.setImage(player.get_image());
            }
        });
        this.scene = scene;
        generate_enemy();
        return scene;
    }
    Scene create_fail() {
        Image background = new Image("/resources/img/background2.png");
        ImageView background_view = new ImageView(background);
        background_view.setPreserveRatio(true);
        background_view.setFitWidth(1280);

        Text mes = new Text("You failed!\n" +
                "\n Press ESC to go to menu\n Press Q to Quit\nPress Enter to restart");
        mes.setX(350);
        mes.setY(250);
        mes.setFont(font);
        mes.setTextAlignment(TextAlignment.CENTER);
        mes.setFill(RED);
        Group g = new Group(background_view,mes);
        Scene scene = new Scene(g);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                Event e = new Titleevent();
                button.fireEvent(e);
            } else if (event.getCode().equals(KeyCode.Q)) {
                Event e = new Quitevent();
                button.fireEvent(e);
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                Event e = new Level1();
                button.fireEvent(e);
            }
        });
        return scene;
    }

    Scene create_title (){
        Image background = new Image("/resources/img/background2.png");
        ImageView background_view = new ImageView(background);
        background_view.setPreserveRatio(true);
        background_view.setFitWidth(1280);

        //logo
        Image logo = new Image("/resources/img/logo.png");
        ImageView logo_view = new ImageView(logo);
        logo_view.setPreserveRatio(true);
        logo_view.setX(329);

        //my name
        Label myname = new Label("Created by Kai Wang(20669798)");

        //directions
        Text direction1 = new Text("CONTROLS\n PRESS        OR        TO FIRE \n\nSTART GAME-ENTER\nQUIT-Q");
        String larrow = Character.toString(0x2190);
        String rarrow = Character.toString(0x2192);
        Text direction2 = new Text(larrow + "        " + rarrow);
        direction2.setFill(ANTIQUEWHITE);
        direction2.setFont(Font.font("Arial",40));
        direction2.setX(555);
        direction2.setY(382);
        direction1.setFont(font);
        direction1.setX(425);
        direction1.setY(340);
        direction1.setFill(ANTIQUEWHITE);
        direction1.setTextAlignment(TextAlignment.CENTER);
        Group start_group = new Group(background_view, logo_view, myname, direction1,direction2);
        Scene title_scene = new Scene(start_group);

        return title_scene;

    }

    void winlevel3() {
        Text mes = new Text("Congratulations! You win!\n" + "Your score is:" +score+
                " \nPress ESC to go to menu\nPress Q to quit\nPress Enter to restart");
        mes.setX(300);
        mes.setY(150);
        mes.setFont(font);
        mes.setTextAlignment(TextAlignment.CENTER);
        mes.setFill(RED);
        group.getChildren().add(mes);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.Q)) {
                Event e = new Quitevent();
                button.fireEvent(e);
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                Event e = new Titleevent();
                button.fireEvent(e);
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                Event e = new Level1();
                button.fireEvent(e);
            }
        });
    }

    void winlevel12 () {
        Text mes = new Text("Congratulations! You win!\n" + "Your score is:" +score+
                " \n Press Enter to go to next level\n Press ESC to go to menu\n Press R to restart from level 1");
        mes.setX(300);
        mes.setY(150);
        mes.setFont(font);
        mes.setTextAlignment(TextAlignment.CENTER);
        mes.setFill(RED);
        group.getChildren().add(mes);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if(level_num == 1) {
                    Event e = new Level2();
                    button.fireEvent(e);
                } else if (level_num == 2) {
                    Event e = new Level3();
                    button.fireEvent(e);
                }
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                Event e = new Titleevent();
                button.fireEvent(e);
            } else if(event.getCode().equals(KeyCode.R)){
                Event e = new Level1();
                button.fireEvent(e);
            }
        });
    }

    void enemymius1() {
        enemy  = enemy - 1;
        enemy_label.setText("Enemies: "+Integer.toString(enemy));
        if(enemy == 0) {
            Media won = new Media(Paths.get("./src/resources/sound/won.mp3").toUri().toString());
            MediaPlayer wonplayer = new MediaPlayer(won);
            wonplayer.play();
            gameend = true;
            if(level_num == 1 || level_num == 2) {
                winlevel12();
            } else {
                winlevel3();
            }
        }
    }

    void lifemius1() {
        life = life - 1;
        String livestr = "";
        for (int i=1; i <= life;i++) {
            livestr = heart + livestr;
        }
        life_label.setText(livestr);
        if(life == 0) {
            Media fail = new Media(Paths.get("./src/resources/sound/fail.wav").toUri().toString());
            MediaPlayer failplayer = new MediaPlayer(fail);
            failplayer.play();
            gameend = true;
            if(enemy_group.getChildren()!= null) {
                for (Node n:enemy_group.getChildren()) {
                    n.setDisable(true);
                }
                enemy_group.getChildren().removeAll();
            }
            Event e = new Failevent();
            button.fireEvent(e);
        };
    }

    void scoreplus1 () {
        score = score + 1;
        score_label.setText(Integer.toString(score));
    }

    void scoreminus1 () {
        score = score - 1;
        score_label.setText(Integer.toString(score));
    }

    void generate_enemy () {
        MiniGame mg = this;
        Random rand = new Random();
        Scene current_scene = scene;
        for(int i=0;i<ENEMY_NUM;i++)  {
            long delay = ThreadLocalRandom.current().nextLong(WAIT_TIME);//ThreadLocalRandom.current().nextLong(WAIT_TIME);
            TimerTask task = new TimerTask() {
                public void run() {
                    if (current_scene != mg.scene) {
                        return;
                    }
                    Platform.runLater(() -> {
                        //System.out.println(mg.i);
                        mg.i = mg.i+1;
                        Enemy enemy = new Enemy(mg, rand.nextBoolean(),level_num);
                        enemy.create_enemy();
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, delay);
        }
    }
}

abstract class MiniEvent extends Event {
    public static final EventType<MiniEvent> Mini_Event = new EventType(ANY);
    public MiniEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}

class Failevent extends MiniEvent {
    public static final EventType<MiniEvent> fail = new EventType(Mini_Event,"Failevent");
    public Failevent() {
        super(fail);
    }
}

class Titleevent extends MiniEvent {
    public static final EventType<MiniEvent> title = new EventType(Mini_Event,"Titleevent");
    public Titleevent() {
        super(title);
    }
}

class Level1 extends MiniEvent {
    public static final EventType<MiniEvent> one = new EventType (Mini_Event,"Level1");
    public Level1() {
        super(one);
    }
}

class Level2 extends MiniEvent {
    public static final EventType<MiniEvent> two = new EventType (Mini_Event,"Level2");
    public Level2() {
        super(two);
    }
}

class Level3 extends MiniEvent {
    public static final EventType<MiniEvent> three = new EventType (Mini_Event,"Level3");
    public Level3() {
        super(three);
    }
}

class Quitevent extends MiniEvent {
    public static final EventType<Quitevent> quit = new EventType (Mini_Event,"Quitevent");
    public Quitevent() {
        super(quit);
    }
}