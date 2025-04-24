package ui;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Flexible layout spacer component that expands to fill available space.
 * Can be configured to grow horizontally and/or vertically in container layouts.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Spacer extends Pane {
    /**
     * Creates spacer with specified expansion properties.
     * @param hGrow true to expand horizontally and fill available width
     * @param vGrow true to expand vertically and fill available height
     */
    public Spacer(boolean hGrow, boolean vGrow) {
        super();

        if(hGrow) {
            HBox.setHgrow(this, Priority.ALWAYS);
            this.setMinSize(10, 10);
        }

        if (vGrow) {
            VBox.setVgrow(this, Priority.ALWAYS);
            this.setMinSize(10, 10);
        }
    }
}
