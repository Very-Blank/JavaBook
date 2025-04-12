package ui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;


public class LabeledField<T extends Node> extends VBox{
    public T content;
    public LabeledField(String name, T content, Assets assets) {
        super(5.0);
        Label label = new Label(name);
        label.setFont(assets.fonts.normal);
        label.setTextFill(assets.textColor);
        this.content = content;
        super.getChildren().addAll(label, this.content);
    }
}

