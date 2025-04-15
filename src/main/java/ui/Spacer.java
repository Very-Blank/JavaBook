package ui;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Spacer extends Pane {
    public Spacer(boolean hGrow, boolean vGrow){
        super();

        if(hGrow){
            HBox.setHgrow(this, Priority.ALWAYS);
            this.setMinSize(10, 10);
        }

        if (vGrow) {
            VBox.setVgrow(this, Priority.ALWAYS);
            this.setMinSize(10, 10);
        }
    }
}
