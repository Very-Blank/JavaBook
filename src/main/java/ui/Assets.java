package ui;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.*;

/**
 * Centralized styling resource container defining color scheme, backgrounds and fonts.
 * Provides pre-configured UI elements for consistent application styling.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Assets {
    /** Font resource loader with different font weights/sizes */
    public final Fonts fonts;
    /** Primary background style for root containers */
    public final Background background;
    /** Dark gray color code for base surfaces */
    public final String gray1 = "#252525";
    /** Secondary background style for elevated components */
    public final Background surface;
    /** Medium gray color code for intermediate surfaces */
    public final String gray2 = "#393939";
    /** Tertiary background style for highlighted components */
    public final Background elevated;
    /** Light gray color code for accent elements */
    public final String gray3 = "#4f4f4f";
    /** Quaternary background style for interactive elements */
    public final Background highlight;
    /** Bright gray color code for high-contrast details */
    public final String gray4 = "#666666";
    /** Primary text color value */
    public final String white1 = "#dee2e6";
    /** Text color constant using white1 value */
    public final Color textColor = Color.valueOf(white1);

    /**
     * Initializes all styling resources with predefined color scheme.
     * Creates font instances and configures background styles with:
     * - Rounded corners (4px radius)
     * - Layered gray color palette
     * - Transparent insets
     */
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
