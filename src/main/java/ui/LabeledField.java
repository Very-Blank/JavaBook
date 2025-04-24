package ui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Generic labeled container component pairing a text label with any JavaFX Node content.
 * Typically used for form fields or labeled UI elements in input layouts.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 * @param <T> type of contained UI component (must extend Node)
 */
public class LabeledField<T extends Node> extends VBox {
    /** Contained UI component displayed below the label */
    public T content;

    /**
     * Constructs labeled container with specified text and UI component.
     * @param name label text displayed above the content
     * @param content UI component to contain
     * @param assets styling resources for label formatting
     */
    public LabeledField(String name, T content, Assets assets) {
        super(5.0);
        Label label = new Label(name);
        label.setFont(assets.fonts.normal);
        label.setTextFill(assets.textColor);
        this.content = content;
        super.getChildren().addAll(label, this.content);
    }
}

