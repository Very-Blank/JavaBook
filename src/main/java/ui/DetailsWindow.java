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

import ui.Assets;

public class DetailsWindow<T extends Pane> extends Stage {
    private Button backButton;
    private Button deleteButton;
    private Button saveButton;
    protected HBox dialogue;
    protected T content;
    private final double edgeSpace = 5.0;

    public DetailsWindow(T content, Assets assets, String windowName, String details){
        super();
        super.setTitle(windowName);
        super.initModality(Modality.APPLICATION_MODAL);

        this.content = content;
        this.backButton = newButton("Back ", assets);
        this.deleteButton = newButton("Delete 󰆴", assets);
        this.saveButton = newButton("Save ", assets);
        this.dialogue = new HBox(5.0);

        VBox.setMargin(this.content, new Insets(0, this.edgeSpace, 0, this.edgeSpace));

        Label label = new Label(details);
        label.setFont(assets.fonts.big);
        label.setTextFill(assets.textColor);
        VBox.setMargin(label, new Insets(this.edgeSpace, this.edgeSpace, 0, this.edgeSpace));

        Pane hSpacer = new Pane();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        hSpacer.setMinSize(10, 10);

        this.dialogue.getChildren().addAll(this.backButton, hSpacer, this.deleteButton, this.saveButton);
        VBox.setMargin(this.dialogue, new Insets(0, this.edgeSpace, this.edgeSpace, this.edgeSpace));

        VBox vbox = new VBox();
        vbox.setBackground(assets.background);

        Pane vSpacer = new Pane();
        VBox.setVgrow(vSpacer, Priority.ALWAYS);
        vSpacer.setMinSize(10, 10);

        vbox.getChildren().addAll(label, this.content, vSpacer, this.dialogue);

        super.setScene(new Scene(vbox, 300, 150));
    }

    public Button newButton(String name, Assets assets){
        Button button = new Button(name);
        button.setFont(assets.fonts.normal);
        button.setTextFill(assets.textColor);
        button.setBackground(assets.surface);
        button.setMinHeight(30.0);
        button.setMinWidth(60.0);
        return button;
    }

    public void setBackAction(EventHandler<ActionEvent> action) {
        this.backButton.setOnAction(action);
    }

    public void setDeleteAction(EventHandler<ActionEvent> action) {
        this.deleteButton.setOnAction(action);
    }

    public void setSaveAction(EventHandler<ActionEvent> action) {
        this.saveButton.setOnAction(action);
    }
}
