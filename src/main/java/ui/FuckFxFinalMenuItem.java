package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;

/**
 * A custom JavaFX HBox component representing a final menu item in a menu structure.
 * Contains a label and spacing elements, and links to a FuckFxMenuItem for functionality.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class FuckFxFinalMenuItem extends HBox{
    private FuckFxMenuItem menuItem;
    private Label label;
    private Pane offset;

    /**
     * Constructs a final menu item with specified layout parameters.
     * 
     * @param item The associated FuckFxMenuItem containing action logic
     * @param insets Margin insets for the component
     * @param height Minimum height of the menu item
     * @param width Minimum width of the menu item
     * @param textSpacing Spacing between the icon offset and text label
     */
    public FuckFxFinalMenuItem(FuckFxMenuItem item, Insets insets, double height, double width, double textSpacing){
        super();
        this.label = new Label(item.getText());
        this.offset = new Pane();
        this.menuItem = item;

        this.setAlignment(Pos.CENTER_LEFT);

        this.setOnMouseClicked((_) -> {
            this.menuItem.fire();
        });

        this.setMinHeight(height);
        this.setMinWidth(width);

        this.offset.setMinHeight(height);
        this.offset.setMinWidth(textSpacing);

        VBox.setMargin(this, insets);
        this.getChildren().addAll(this.offset, this.label);
    }

    /**
     * Sets the text color of the menu item label.
     * @param color The Color to apply to the text
     */
    public void setFontColor(Color color){
        this.label.setTextFill(color);
    }

    /**
     * Sets the font for the menu item label.
     * @param font The Font to apply to the text
     */
    public void setFont(Font font){
        this.label.setFont(font);
    }

    /**
     * Adjusts the spacing between the icon offset and text label.
     * @param textSpacing The new spacing width in pixels
     */
    public void setMinTextSpacing(double textSpacing){
        this.offset.setMinWidth(textSpacing);
    }

    /**
     * Checks if this component is linked to the given FuckFxMenuItem.
     * @param item The menu item to compare against
     * @return true if the provided item is the same instance as the stored menu item
     */
    public boolean equalsTo(FuckFxMenuItem item){
        return this.menuItem == item;
    }
}
