package ui;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;

public class Assets{
    public final Fonts fonts;
    public final Background background;
    public final String gray1 = "#252525";
    public final Background surface;
    public final String gray2 = "#393939";
    public final Background elevated;
    public final String gray3 = "#4f4f4f";
    public final Background highlight;
    public final String gray4 = "#666666";

    public final String white1 = "#dee2e6";
    public final Color textColor = Color.valueOf(white1);

    public Assets(){
        this.fonts = new Fonts();
        BackgroundFill fill1 = new BackgroundFill(Color.valueOf(this.gray1), new CornerRadii(0), new Insets(0));
        this.background = new Background(fill1);

        BackgroundFill fill2 = new BackgroundFill(Color.valueOf(this.gray2), new CornerRadii(4), new Insets(0));
        this.surface = new Background(fill2);

        BackgroundFill fill3 = new BackgroundFill(Color.valueOf(this.gray3), new CornerRadii(4), new Insets(0));
        this.elevated = new Background(fill3);

        BackgroundFill fill4 = new BackgroundFill(Color.valueOf(this.gray4), new CornerRadii(4), new Insets(0));
        this.highlight = new Background(fill4);
    }
}
