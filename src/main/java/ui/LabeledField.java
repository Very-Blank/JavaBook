package ui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


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

