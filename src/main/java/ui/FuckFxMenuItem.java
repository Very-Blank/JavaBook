package ui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.event.EventHandler;


public class FuckFxMenuItem{
    private String text;
    private EventHandler<ActionEvent> actionHandler;

    public FuckFxMenuItem(String text) {
        this.text = text;
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.actionHandler = handler;
    }

    public void fire() {
        if (actionHandler != null) {
            actionHandler.handle(new ActionEvent());
        }
    }

    public String getText() {
        return text;
    }
}
