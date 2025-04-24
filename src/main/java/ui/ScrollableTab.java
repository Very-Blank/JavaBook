package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.event.*;

import java.util.HashMap;

public abstract class ScrollableTab extends Tab {
    protected ScrollPane scrollPane;
    protected Assets assets;
    protected EventHandler<ActionEvent> updateNotifier;

    private HBox buttonHolder;
    private HashMap<String, Button> buttonMap;

    public ScrollableTab(Assets assets, String name) {
        super();
        this.assets = assets;
        this.scrollPane = new ScrollPane();
        this.buttonHolder = new HBox();
        this.buttonMap = new HashMap<String, Button>();

        super.setGraphic(newLabel(name));
        setScrollPaneSyling(this.scrollPane, this.assets);

        this.buttonHolder.setBackground(this.assets.surface);
        this.buttonHolder.setMinHeight(30);

        VBox vbox = new VBox(5);
        vbox.setBackground(this.assets.background);

        vbox.getChildren().addAll(this.buttonHolder, this.scrollPane);

        super.setContent(vbox);
    }

    private Label newLabel(String name) {
        Label label = new Label(name);
        label.setFont(this.assets.fonts.normal);
        label.setMinWidth(150);
        label.setTextFill(this.assets.textColor);
        return label;
    }

    private void setScrollPaneSyling(ScrollPane scrollPane, Assets assets){
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        String css = String.format("""
                    .scroll-pane > .viewport {
                        -fx-background-color: %s;
                    }
                """, assets.gray1);

        scrollPane.getStylesheets().add("data:text/css," + css);
    }

    public void addButton(String label, String name) {
        Label addLabel = new Label(label);
        addLabel.setFont(this.assets.fonts.normal);
        addLabel.setTextFill(this.assets.textColor);

        Button newButton = new Button();
        newButton.setGraphic(addLabel);
        HBox.setMargin(newButton, new Insets(5, 0, 5, 5));
        newButton.setMinHeight(28);
        newButton.setBackground(this.assets.elevated);
        this.buttonHolder.getChildren().add(newButton);
        this.buttonMap.put(name, newButton);
    }

    public Button getButton(String name) {
        return this.buttonMap.get(name);
    }

    public void setButtonAction(EventHandler<ActionEvent> action, String name) {
        this.buttonMap.get(name).setOnAction(action);
    }

    protected void updateContents() {
        this.fire();
    }

    public void setUpdateNotifier(EventHandler<ActionEvent> handler) {
        this.updateNotifier = handler;
        this.fire();
    }

    private void fire() {
        if (this.updateNotifier != null) {
            this.updateNotifier.handle(new ActionEvent());
        }
    }
}
