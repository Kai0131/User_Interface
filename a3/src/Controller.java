import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class Controller {
    static int Fitscale = 100;
    static int Fitscale2 = 100;
    static int hair_Y = -97;
    public Model model;
    public Stage stage;
    SVGLoader svgLoader = new SVGLoader();

    @FXML
    private GridPane Hair_GridPane;
    @FXML
    private GridPane Skin_GridPane;
    @FXML
    private GridPane Eyebrows_GridPane;
    @FXML
    private GridPane Eyes_Gridpane;
    @FXML
    private GridPane Mouth_GridPane;
    @FXML
    private Pane avator_spane;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private SplitPane root;
    @FXML
    private Text brow_text;
    @FXML
    private Spinner brow_spinner;
    @FXML
    private Text eye_text;
    @FXML
    private Spinner eye_spinner;
    @FXML
    private Button save_button;
    @FXML
    private FlowPane avatar_flowpane;

    private ImageView brows_view;
    private ImageView eyes_view;
    private ImageView mouth_view;
    private ImageView skin_view;
    private SVGPath hair_svg;
    private Group clothes;

    private SVGPath selected_svgpath = null;
    private ImageView selected_eyebrow = null;
    private ImageView selected_eyes = null;
    private DropShadow ds = new DropShadow(20,Color.BLUE);
    private DropShadow dss = new DropShadow(20,Color.GREEN);

    //preview

    public void initialize() {
        //hair
        Image hair_curly= new Image("/resources/hair/hair_curly.png");
        ImageView hair_curly_view = new ImageView(hair_curly);
        hair_curly_view.setFitWidth(Fitscale);
        hair_curly_view.setFitHeight(Fitscale);
        Hair_GridPane.add(hair_curly_view,0,0);
        avator_spane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        hair_curly_view.setPickOnBounds(true);
        hair_curly_view.setOnMouseClicked(event -> {
            model.set_hair("curly");
        });
        //preview
        hair_curly_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("curly")) {
                model.set_hair_preview("curly", (Color) hair_svg.getFill());
            }
        });
        hair_curly_view.setOnMouseExited(event -> {
            model.set_hair(null);
        });
        //preview end

        Image hair_long= new Image("/resources/hair/hair_long.png");
        ImageView hair_long_view = new ImageView(hair_long);
        hair_long_view.setFitWidth(Fitscale);
        hair_long_view.setFitHeight(Fitscale);
        Hair_GridPane.add(hair_long_view,1,0);
        hair_long_view.setPickOnBounds(true);
        hair_long_view.setOnMouseClicked(event -> {
            model.set_hair("long");
        });
        //preview
        hair_long_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("long")) {
                model.set_hair_preview("long", (Color) hair_svg.getFill());
            }
        });
        hair_long_view.setOnMouseExited(event -> {
            model.set_hair(null);
        });
        //preview end

        Image hair_short= new Image("/resources/hair/hair_short.png");
        ImageView hair_short_view = new ImageView(hair_short);
        hair_short_view.setFitWidth(Fitscale);
        hair_short_view.setFitHeight(Fitscale);
        Hair_GridPane.add(hair_short_view,0,1);
        hair_short_view.setPickOnBounds(true);
        hair_short_view.setOnMouseClicked(event -> {
            model.set_hair("short");
        });
        //preview
        hair_short_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("short")) {
                model.set_hair_preview("short", (Color) hair_svg.getFill());
            }
        });
        hair_short_view.setOnMouseExited(event -> {
            model.set_hair(null);
        });
        //preview end

        Image hair_wavy= new Image("/resources/hair/hair_wavy.png");
        ImageView hair_wavy_view = new ImageView(hair_wavy);
        hair_wavy_view.setFitWidth(Fitscale);
        hair_wavy_view.setFitHeight(Fitscale);
        Hair_GridPane.add(hair_wavy_view,1,1);
        hair_wavy_view.setPickOnBounds(true);
        hair_wavy_view.setOnMouseClicked(event -> {
            model.set_hair("wavy");
        });
        //preview
        hair_wavy_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("wavy")) {
                model.set_hair_preview("wavy", (Color) hair_svg.getFill());
            }
        });
        hair_wavy_view.setOnMouseExited(event -> {
            model.set_hair(null);
        });
        //preview end

        //Skin
        Image skin_brown= new Image("/resources/skin/skin_brown.png");
        ImageView skin_brown_view = new ImageView(skin_brown);
        skin_brown_view.setFitWidth(Fitscale);
        skin_brown_view.setFitHeight(Fitscale);
        Skin_GridPane.add(skin_brown_view,0,0);
        skin_brown_view.setPickOnBounds(true);
        skin_brown_view.setOnMouseClicked(event -> {
            model.set_skin("brown");
        });
        //preview
        skin_brown_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("brown")) {
                model.set_skin_preview("brown");
            }
        });
        skin_brown_view.setOnMouseExited(event -> {
            model.set_skin(null);
        });
        //preview end

        Image skin_light= new Image("/resources/skin/skin_light.png");
        ImageView skin_light_view = new ImageView(skin_light);
        skin_light_view.setFitWidth(Fitscale);
        skin_light_view.setFitHeight(Fitscale);
        Skin_GridPane.add(skin_light_view,1,0);
        skin_light_view.setPickOnBounds(true);
        skin_light_view.setOnMouseClicked(event -> {
            model.set_skin("light");
        });
        //preview
        skin_light_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_hair().equals("light")) {
                model.set_skin_preview("light");
            }
        });
        skin_light_view.setOnMouseExited(event -> {
            model.set_skin(null);
        });
        //preview end

        Image skin_lighter= new Image("/resources/skin/skin_lighter.png");
        ImageView skin_lighter_view = new ImageView(skin_lighter);
        skin_lighter_view.setFitWidth(Fitscale);
        skin_lighter_view.setFitHeight(Fitscale);
        Skin_GridPane.add(skin_lighter_view,0,1);
        skin_lighter_view.setPickOnBounds(true);
        skin_lighter_view.setOnMouseClicked(event -> {
            model.set_skin("lighter");
        });
        //preview
        skin_lighter_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_skin().equals("lighter")) {
                model.set_skin_preview("lighter");
            }
        });
        skin_lighter_view.setOnMouseExited(event -> {
            model.set_skin(null);
        });
        //preview end

        //brows
        Image brows_default = new Image("/resources/brows/brows_default.png");
        ImageView brows_default_view = new ImageView(brows_default);
        brows_default_view.setFitWidth(Fitscale2);
        brows_default_view.setFitHeight(Fitscale2);
        Eyebrows_GridPane.add(brows_default_view,0,0);
        brows_default_view.setPickOnBounds(true);
        brows_default_view.setOnMouseClicked(event -> {
            model.set_brows("default");
        });
        //preview
        brows_default_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_brows().equals("default")) {
                model.set_brows_preview("default");
            }
        });
        brows_default_view.setOnMouseExited(event -> {
            model.set_brows(null);
        });
        //preview end

        Image brows_angry = new Image("/resources/brows/brows_angry.png");
        ImageView brows_angry_view = new ImageView(brows_angry);
        brows_angry_view.setFitWidth(Fitscale2);
        brows_angry_view.setFitHeight(Fitscale2);
        Eyebrows_GridPane.add(brows_angry_view,1,0);
        brows_angry_view.setPickOnBounds(true);
        brows_angry_view.setOnMouseClicked(event -> {
            model.set_brows("angry");
        });
        //preview
        brows_angry_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_brows().equals("angry")) {
                model.set_brows_preview("angry");
            }
        });
        brows_angry_view.setOnMouseExited(event -> {
            model.set_brows(null);
        });
        //preview end

        Image brows_sad = new Image("/resources/brows/brows_sad.png");
        ImageView brows_sad_view = new ImageView(brows_sad);
        brows_sad_view.setFitWidth(Fitscale2);
        brows_sad_view.setFitHeight(Fitscale2);
        Eyebrows_GridPane.add(brows_sad_view,0,1);
        brows_sad_view.setPickOnBounds(true);
        brows_sad_view.setOnMouseClicked(event -> {
            model.set_brows("sad");
        });
        //preview
        brows_sad_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_brows().equals("sad")) {
                model.set_brows_preview("sad");
            }
        });
        brows_sad_view.setOnMouseExited(event -> {
            model.set_brows(null);
        });
        //preview end

        //eyes
        Image eyes_default = new Image("/resources/eyes/eyes_default.png");
        ImageView eyes_default_view = new ImageView(eyes_default);
        eyes_default_view.setFitWidth(Fitscale2);
        eyes_default_view.setFitHeight(Fitscale2);
        Eyes_Gridpane.add(eyes_default_view,0,0);
        eyes_default_view.setPickOnBounds(true);
        eyes_default_view.setOnMouseClicked(event -> {
            model.set_eyes("default");
        });
        //preview
        eyes_default_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_eyes().equals("default")) {
                model.set_eyes_preview("default");
            }
        });
        eyes_default_view.setOnMouseExited(event -> {
            model.set_eyes(null);
        });
        //preview end

        Image eyes_closed = new Image("/resources/eyes/eyes_closed.png");
        ImageView eyes_closed_view = new ImageView(eyes_closed);
        eyes_closed_view.setFitWidth(Fitscale2);
        eyes_closed_view.setFitHeight(Fitscale2);
        Eyes_Gridpane.add(eyes_closed_view,1,0);
        eyes_closed_view.setPickOnBounds(true);
        eyes_closed_view.setOnMouseClicked(event -> {
            model.set_eyes("closed");
        });
        //preview
        eyes_closed_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_eyes().equals("closed")) {
                model.set_eyes_preview("closed");
            }
        });
        eyes_closed_view.setOnMouseExited(event -> {
            model.set_eyes(null);
        });
        //preview end

        Image eyes_wide = new Image("/resources/eyes/eyes_wide.png");
        ImageView eyes_wide_view = new ImageView(eyes_wide);
        eyes_wide_view.setFitWidth(Fitscale2);
        eyes_wide_view.setFitHeight(Fitscale2);
        Eyes_Gridpane.add(eyes_wide_view,0,1);
        eyes_wide_view.setPickOnBounds(true);
        eyes_wide_view.setOnMouseClicked(event -> {
            model.set_eyes("wide");
        });
        //preview
        eyes_wide_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_eyes().equals("wide")) {
                model.set_eyes_preview("wide");
            }
        });
        eyes_wide_view.setOnMouseExited(event -> {
            model.set_eyes(null);
        });
        //preview end


        //Mouth
        Image mouth_default = new Image("/resources/mouth/mouth_default.png");
        ImageView mouth_default_view = new ImageView(mouth_default);
        mouth_default_view.setFitWidth(Fitscale2);
        mouth_default_view.setFitHeight(Fitscale2);
        Mouth_GridPane.add(mouth_default_view,0,0);
        mouth_default_view.setPickOnBounds(true);
        mouth_default_view.setOnMouseClicked(event -> {
            model.set_mouth("default");
        });
        //preview
        mouth_default_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_mouth().equals("default")) {
                model.set_mouth_preview("default");
            }
        });
        mouth_default_view.setOnMouseExited(event -> {
            model.set_mouth(null);
        });
        //preview end

        Image mouth_sad = new Image("/resources/mouth/mouth_sad.png");
        ImageView mouth_sad_view = new ImageView(mouth_sad);
        mouth_sad_view.setFitWidth(Fitscale2);
        mouth_sad_view.setFitHeight(Fitscale2);
        Mouth_GridPane.add(mouth_sad_view,1,0);
        mouth_sad_view.setPickOnBounds(true);
        mouth_sad_view.setOnMouseClicked(event -> {
            model.set_mouth("sad");
        });
        //preview
        mouth_sad_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_mouth().equals("sad")) {
                model.set_mouth_preview("sad");
            }
        });
        mouth_sad_view.setOnMouseExited(event -> {
            model.set_mouth(null);
        });
        //preview end

        Image mouth_serious = new Image("/resources/mouth/mouth_serious.png");
        ImageView mouth_serious_view = new ImageView(mouth_serious);
        mouth_serious_view.setFitWidth(Fitscale2);
        mouth_serious_view.setFitHeight(Fitscale2);
        Mouth_GridPane.add(mouth_serious_view,0,1);
        mouth_serious_view.setPickOnBounds(true);
        mouth_serious_view.setOnMouseClicked(event -> {
            model.set_mouth("serious");
        });
        //preview
        mouth_serious_view.setOnMouseEntered(event -> {
            deselect();
            if(!model.get_mouth().equals("serious")) {
                model.set_mouth_preview("serious");
            }
        });
        mouth_serious_view.setOnMouseExited(event -> {
            model.set_mouth(null);
        });
        //preview end


        //-----------------------------------------------------------------------
        //avator
        avator_spane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        avator_spane.setMaxWidth(200);
        avator_spane.setMaxHeight(200);

        //color_picker
        colorpicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                selected_svgpath.setFill(colorpicker.getValue());
            }
        });

        //root Vbox
        avatar_flowpane.setOnMouseClicked(event -> {
            deselect();
        });

        //  Spinner
        SpinnerValueFactory<Integer> vFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, 0);
        brow_spinner.setValueFactory(vFactory);

        SpinnerValueFactory<Double> eye_factor =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5,1.5,1,0.1);
        eye_spinner.setValueFactory(eye_factor);
        // save butto
        FileChooser fileChooser = new FileChooser();
        save_button.setOnAction(e -> {
            deselect();
            WritableImage image = avator_spane.snapshot(new SnapshotParameters(), null);
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( "Save as .png","*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showSaveDialog(stage);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", selectedFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void update_hair_view(String hair, Color hair_color) {
        SVGLoader s = new SVGLoader();
        Group svg;
        if(hair.equals("curly")){
            svg = svgLoader.loadSVG("resources/hair/hair_curly.svg");
        } else if (hair.equals("long")){
            svg = svgLoader.loadSVG("resources/hair/hair_long.svg");
        } else if (hair.equals("short")){
            svg = svgLoader.loadSVG("resources/hair/hair_short.svg");
        } else {
            svg = svgLoader.loadSVG("resources/hair/hair_wavy.svg");
        }
        SVGPath hair_svg = (SVGPath) svg.getChildren().get(0);
        this.hair_svg = hair_svg;
        if (hair_color != null) {
            hair_svg.setFill(hair_color);
        }
        model.set_hair_color((Color) hair_svg.getFill());
        hair_svg.setLayoutY(hair_Y);

        hair_svg.setOnMouseEntered(event -> {
            if (selected_svgpath != hair_svg) {
                hair_svg.setEffect(dss);
            }
        });

        hair_svg.setOnMouseExited(event -> {
            if (selected_svgpath != hair_svg) {
                hair_svg.setEffect(null);
            }
        });

        hair_svg.setOnMouseClicked(event -> {
            deselect();
            hair_svg.setEffect(ds);
            selected_svgpath = hair_svg;
            colorpicker.setValue((Color) hair_svg.getFill());
            colorpicker.setVisible(true);
            event.consume();
        });

    }

    public void update_clothes_view() {
        SVGLoader s = new SVGLoader();
        Group svg = svgLoader.loadSVG("resources/clothes.svg");
        clothes = svg;
        model.set_clothes(clothes);
        for (Iterator<Node> iterator = svg.getChildren().iterator(); iterator.hasNext(); ) {
            SVGPath svgit = (SVGPath) iterator.next();
            svgit.setOnMouseEntered(event -> {
                if (selected_svgpath != svgit) {
                    svgit.setEffect(dss);
                }
            });

            svgit.setOnMouseExited(event -> {
                if (selected_svgpath != svgit) {
                    svgit.setEffect(null);
                }
            });

            svgit.setOnMouseClicked(event -> {
                deselect();
                selected_svgpath = svgit;
                svgit.setEffect(ds);
                colorpicker.setValue((Color) svgit.getFill());
                colorpicker.setVisible(true);
                event.consume();
            });
        }
    }

    public void update_brows_view(String brows){
        Image brows_image;
        if(brows.equals("angry")) {
            brows_image = new Image("/resources/brows/brows_angry.png");
        }else if (brows.equals("default")) {
            brows_image = new Image("/resources/brows/brows_default.png");
        }else {
            brows_image = new Image("/resources/brows/brows_sad.png");
        }
        ImageView brows_imageview = new ImageView(brows_image);
        imageview_setup(brows_imageview);
        this.brows_view = brows_imageview;

        brow_spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            int offset = Integer.parseInt(brow_spinner.getValue().toString());
            model.set_brows_offset(offset);
            brows_view.setLayoutY(offset);
        });

        brows_view.setOnMouseEntered(event -> {
            if (selected_eyebrow != brows_view) {
                brows_view.setEffect(dss);
            }
        });

        brows_view.setOnMouseExited(event -> {
            if (selected_eyebrow != brows_view) {
                brows_view.setEffect(null);
            }
        });

        brows_view.setOnMouseClicked(event -> {
            deselect();
            selected_eyebrow = brows_view;
            brows_view.setEffect(ds);
            brow_text.setVisible(true);
            brow_spinner.setVisible(true);
            event.consume();
        });
    }

    public void update_eyes_view(String eyes) {
        Image eyes_image;
        if(eyes.equals("closed")){
            eyes_image = new Image("/resources/eyes/eyes_closed.png");
        }else if (eyes.equals("default")) {
            eyes_image = new Image("/resources/eyes/eyes_default.png");
        }else {
            eyes_image = new Image("/resources/eyes/eyes_wide.png");
        }
        ImageView eyes_imageview = new ImageView(eyes_image);
        imageview_setup(eyes_imageview);
        this.eyes_view = eyes_imageview;
        eye_spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            Double scale = Double.parseDouble(eye_spinner.getValue().toString());
            model.set_eye_scales(scale);
            this.eyes_view.setScaleX(scale);
            this.eyes_view.setScaleY(scale);
        });

        eyes_imageview.setOnMouseEntered(event -> {
            if(selected_eyes != eyes_imageview) {
                eyes_imageview.setEffect(dss);
            }
        });
        eyes_imageview.setOnMouseExited(event -> {
            if(selected_eyes != eyes_imageview) {
                eyes_imageview.setEffect(null);
            }
        });

        eyes_imageview.setOnMouseClicked(event -> {
            deselect();
            selected_eyes = eyes_imageview;
            eyes_imageview.setEffect(ds);
            eye_text.setVisible(true);
            eye_spinner.setVisible(true);
            event.consume();
        });
    }

    public void update_mouth_view(String mouth) {
        Image mouth_view;
        if(mouth.equals("default")) {
            mouth_view = new Image("/resources/mouth/mouth_default.png");
        } else if (mouth.equals("sad")) {
            mouth_view = new Image("/resources/mouth/mouth_sad.png");
        } else{
            mouth_view = new Image("/resources/mouth/mouth_serious.png");
        }
        ImageView mouth_imageview = new ImageView(mouth_view);
        imageview_setup(mouth_imageview);
        this.mouth_view = mouth_imageview;
    }

    public void update_skin_view(String skin){
        Image skin_view;
        if(skin.equals("brown")) {
            skin_view = new Image("/resources/skin/skin_brown.png");
        }else if (skin.equals("light")) {
            skin_view = new Image("/resources/skin/skin_light.png");
        }else {
            skin_view = new Image("/resources/skin/skin_lighter.png");
        }
        ImageView skin_imageview = new ImageView(skin_view);
        imageview_setup(skin_imageview);
        this.skin_view = skin_imageview;
    }

    public void set_nose(){
        Image nose_view = new Image("/resources/nose_default.png");
        ImageView nose_imageview  = new ImageView(nose_view);
        imageview_setup(nose_imageview);
        avator_spane.getChildren().add(nose_imageview);
    }

    public void update_view() {
        avator_spane.getChildren().clear();
        avator_spane.getChildren().add(skin_view);
        avator_spane.getChildren().add(hair_svg);
        avator_spane.getChildren().add(brows_view);
        avator_spane.getChildren().add(eyes_view);
        avator_spane.getChildren().add(mouth_view);
        avator_spane.getChildren().add(clothes);
        set_nose();
    }

    public void default_setup(){
        update_brows_view(model.get_brows());
        update_skin_view(model.get_skin());
        update_mouth_view(model.get_mouth());
        update_hair_view(model.get_hair(),null);
        update_eyes_view(model.get_eyes());
        update_clothes_view();
        update_view();
    }

    private void imageview_setup(ImageView iv) {
        iv.setFitHeight(200);
        iv.setFitWidth(200);
    }

    private void deselect(){
        colorpicker.setVisible(false);
        brow_text.setVisible(false);
        brow_spinner.setVisible(false);
        eye_text.setVisible(false);
        eye_spinner.setVisible(false);
        if(selected_svgpath != null) {
            selected_svgpath.setEffect(null);
        }
        if(selected_eyebrow != null) {
            selected_eyebrow.setEffect(null);
        }
        if(selected_eyes != null) {
            selected_eyes.setEffect(null);
        }
        selected_svgpath = null;
        selected_eyebrow = null;
        selected_eyes = null;
    }
}
