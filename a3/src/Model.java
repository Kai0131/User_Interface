import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Model {
    public Controller controller;

    //variables to store the current state
    private String brows;
    private int brows_offset;
    private String eyes;
    private Double eyes_scale;
    private String hair;
    private Color hair_color = null;
    private Group clothes;
    private String mouth;
    private String skin;

    //set up default values
    public Model(Controller c) {
        controller = c;
        brows = "default";
        eyes = "default";
        hair = "curly";
        mouth = "default";
        skin = "light";
    }

    public String get_brows(){
        return brows;
    }
    // when brow state is changed, controller calls set_brows to change the state in Model
    // then Model will notify controller to update the avatar.
    // if null is given, then update the avatar used stored value (used for preview feature)
    public void set_brows(String brows){
        if(brows != null) {
            this.brows = brows;
        }
        controller.update_brows_view(this.brows);
        controller.update_view();
    }

    // temporarily set the avatar used brows given, does not change the state (used for preview feature)
    public void set_brows_preview(String brows) {
        controller.update_brows_view(brows);
        controller.update_view();
    }

    public String get_eyes() {
        return eyes;
    }

    public void set_eyes(String eyes){
        if(eyes != null) {
            this.eyes = eyes;
        }
        controller.update_eyes_view(this.eyes);
        controller.update_view();
    }
    public void set_eyes_preview(String eyes) {
        controller.update_eyes_view(eyes);
        controller.update_view();
    }

    public String get_hair() {
        return hair;
    }

    public void set_hair(String hair){ // null means using stored hair type,
        if(hair != null) {
            this.hair = hair;
        }
        controller.update_hair_view(this.hair,hair_color);
        controller.update_view();
    }
    public void set_hair_color(Color hair_color){
        this.hair_color = hair_color;
    }
    public void set_hair_preview(String hair, Color pre_hair_color) {
        controller.update_hair_view(hair, pre_hair_color);
        controller.update_view();
    }

    public String get_mouth() {
        return mouth;
    }

    public void set_mouth(String mouth){
        if (mouth != null) {
            this.mouth = mouth;
        }
        controller.update_mouth_view(this.mouth);
        controller.update_view();
    }
    public void set_mouth_preview(String mouth) {
        controller.update_mouth_view(mouth);
        controller.update_view();
    }

    public String get_skin() {
        return skin;
    }

    public void set_skin(String skin){
        if(skin != null) {
            this.skin = skin;
        }
        controller.update_skin_view(this.skin);
        controller.update_view();
    }

    public void set_skin_preview(String skin) {
        controller.update_skin_view(skin);
        controller.update_view();
    }

    public void set_brows_offset (int offset) {
        this.brows_offset = offset;
    }

    public void set_clothes(Group clothes) {
        this.clothes = clothes;
    }

    public void set_eye_scales(Double eyes_scale) {
        this.eyes_scale = eyes_scale;
    }

}

