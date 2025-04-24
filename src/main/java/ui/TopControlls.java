package ui;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.event.*;

/**
 * Custom toolbar component containing menu controls for application actions.
 * Provides buttons for file operations and application exit.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class TopControlls extends HBox {
    private FuckFxMenuItem openItem;
    private FuckFxMenuItem newItem;
    private FuckFxMenuItem saveItem;
    private FuckFxMenuItem quitItem;

    /**
     * Constructs toolbar with styled menu buttons and initializes actions.
     * @param assets styling resources and color definitions
     */
    public TopControlls(Assets assets) {
        super();
        this.setBackground(assets.surface);

        this.openItem = new FuckFxMenuItem("Open ");
        this.newItem = new FuckFxMenuItem("New ");
        this.saveItem = new FuckFxMenuItem("Save ");
        this.quitItem = new FuckFxMenuItem("Quit ");

        FuckFxMenuButton menu = new FuckFxMenuButton("Options ", this.openItem, this.newItem, this.saveItem, this.quitItem);
        this.setMenuStyling(menu, assets);

        HBox.setMargin(menu, new Insets(5, 0, 5, 5));
        super.getChildren().addAll(menu);
    }

    /**
     * Applies consistent styling to menu components.
     * @param menu menu button to style
     * @param assets styling resources and color definitions
     */
    private void setMenuStyling(FuckFxMenuButton menu, Assets assets) {
        menu.setMenuBackground(new Background(new BackgroundFill(Color.valueOf(assets.gray2), null, null)));
        menu.setMenuItemBackground(assets.elevated);
        menu.setMenuItemFontColor(assets.textColor);
        menu.setMenuItemFont(assets.fonts.normal);

        menu.setBackground(assets.elevated);
        menu.setFont(assets.fonts.normal);
        menu.setTextFill(assets.textColor);
    }

    /** @param action event handler for Open menu item */
    public void setOpenAction(EventHandler<ActionEvent> action) {
        this.openItem.setOnAction(action);
    }

    /** @param action event handler for New menu item */
    public void setNewAction(EventHandler<ActionEvent> action) {
        this.newItem.setOnAction(action);
    }

    /** @param action event handler for Save menu item */
    public void setSaveAction(EventHandler<ActionEvent> action) {
        this.saveItem.setOnAction(action);
    }

    /** @param action event handler for Quit menu item */
    public void setQuitAction(EventHandler<ActionEvent> action) {
        this.quitItem.setOnAction(action);
    }
}
