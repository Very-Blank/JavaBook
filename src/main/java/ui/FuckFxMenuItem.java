package ui;

import javafx.event.*;
import javafx.event.EventHandler;

/**
 * Custom menu item implementation providing basic action handling functionality.
 * Serves as a simplified alternative to JavaFX's MenuItem with direct firing capability.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class FuckFxMenuItem {
    private String text;
    private EventHandler<ActionEvent> actionHandler;

    /**
     * Creates menu item with display text
     * @param text visible label for the menu item
     */
    public FuckFxMenuItem(String text) {
        this.text = text;
    }

    /**
     * Registers action handler for menu item selection
     * @param handler event handler to execute on activation
     */
    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.actionHandler = handler;
    }

    /**
     * Triggers the associated action handler programmatically
     */
    public void fire() {
        if (actionHandler != null) {
            actionHandler.handle(new ActionEvent());
        }
    }

    /** @return current display text of the menu item */
    public String getText() {
        return text;
    }
}
