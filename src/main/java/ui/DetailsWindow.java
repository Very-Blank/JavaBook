package ui;

import javafx.application.Application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;

import java.io.FileInputStream;
import java.util.HashMap;

public class DetailsWindow<T extends Pane> extends Stage {
    private Button backButton;
    private Button saveButton;
    private HBox dialogue;
    protected T content;
    protected Assets assets;
    private final double edgeSpace = 5.0;

    private HashMap<String, Button> buttonMap;

    public DetailsWindow(T content, Assets assets, String windowName, String details){
        super();
        super.setTitle(windowName);
        super.initModality(Modality.APPLICATION_MODAL);

        this.content = content;
        this.assets = assets;
        this.buttonMap = new HashMap<String, Button>();
        this.backButton = newButton("Back ");
        this.saveButton = newButton("Save ");
        this.dialogue = new HBox(5.0);

        VBox.setMargin(this.content, new Insets(0, this.edgeSpace, 0, this.edgeSpace));


        this.dialogue.getChildren().addAll(this.backButton, new Spacer(true, false), this.saveButton);
        VBox.setMargin(this.dialogue, new Insets(0, this.edgeSpace, this.edgeSpace, this.edgeSpace));

        VBox vbox = new VBox();
        vbox.setBackground(assets.background);

        vbox.getChildren().addAll(newLabel(details), this.content, new Spacer(false, true), this.dialogue);

        super.setScene(new Scene(vbox, 300, 150));
    }


    protected TextField newTextField(String string, double width){
        TextField textField = new TextField(string);
        textField.setBackground(this.assets.surface);
        textField.setStyle(String.format("-fx-text-fill: %s;", this.assets.white1));
        textField.setMinWidth(width);
        textField.setMaxWidth(width);
        return textField;
    }

    private Label newLabel(String name){
        Label label = new Label(name);
        label.setFont(this.assets.fonts.big);
        label.setTextFill(this.assets.textColor);
        VBox.setMargin(label, new Insets(this.edgeSpace, this.edgeSpace, 0, this.edgeSpace));
        return label;
    }

    public void addButton(String label, String name) {
        Button button = newButton(label);
        dialogue.getChildren().add(2, button);
        buttonMap.put(name, button);
    }

    public Button getButton(String name) {
        return buttonMap.get(name);
    }

    public void setButtonAction(EventHandler<ActionEvent> action, String name) {
        buttonMap.get(name).setOnAction(action);
    }

    public Button newButton(String name){
        Button button = new Button(name);
        button.setFont(this.assets.fonts.normal);
        button.setTextFill(this.assets.textColor);
        button.setBackground(this.assets.surface);
        button.setMinHeight(30.0);
        button.setMinWidth(60.0);
        return button;
    }

    public void setBackAction(EventHandler<ActionEvent> action) {
        this.backButton.setOnAction(action);
    }

    public void setSaveAction(EventHandler<ActionEvent> action) {
        this.saveButton.setOnAction(action);
    }
}
