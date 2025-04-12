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

import ui.Assets;
import data.*;

public abstract class ScrollableTab extends Tab{
    protected ScrollPane scrollPane;
    protected Assets assets;
    protected EventHandler<ActionEvent> updateNotifier;

    private Button add;

    public ScrollableTab(Assets assets, String name){
        super();
        this.assets = assets;
        this.scrollPane = new ScrollPane();

        Label label = new Label(name);
        label.setFont(assets.fonts.normal);
        label.setMinWidth(150);
        label.setTextFill(assets.textColor);
        super.setGraphic(label);

        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        String css = String.format("""
            .scroll-pane > .viewport {
                -fx-background-color: %s;
            }
        """, assets.gray1);

        scrollPane.getStylesheets().add("data:text/css," + css);
        VBox vbox = new VBox(5);
        vbox.setBackground(assets.background);

        // Pane hSpacer = new Pane();
        // HBox.setHgrow(hSpacer, Priority.ALWAYS);
        // hSpacer.setMinSize(10, 1);

        // TextField search = new TextField();
        // HBox.setMargin(search, new Insets(5, 0, 5, 5));
        // search.setMinHeight(28);
        // search.setBackground(assets.elevated);
        // search.setStyle(String.format("-fx-text-fill: %s;", assets.white1));

        // FuckFxMenuItem test = new FuckFxMenuItem("Something");
        //
        // FuckFxMenuButton filter = new FuckFxMenuButton("Filter ", test);
        // filter.setMenuBackground(new Background(new BackgroundFill(Color.valueOf(assets.gray2), null, null)));
        // filter.setMenuItemBackground(assets.elevated);
        // filter.setMenuItemFontColor(assets.textColor);
        // filter.setMenuItemFont(assets.fonts.normal);
        //
        // filter.setBackground(assets.elevated);
        // filter.setFont(assets.fonts.normal);
        // filter.setTextFill(assets.textColor);
        //
        // filter.setMenuOffset(-5);
        // filter.setMenuSide(FuckFxSide.RIGHT);
        //
        // HBox.setMargin(filter, new Insets(5, 5, 5, 0));


        Label addLabel = new Label("Add ");
        addLabel.setFont(assets.fonts.normal);
        addLabel.setTextFill(assets.textColor);

        this.add = new Button();
        this.add.setGraphic(addLabel);
        HBox.setMargin(this.add, new Insets(5, 0, 5, 5));
        add.setMinHeight(28);
        add.setBackground(assets.elevated);


        HBox hbox = new HBox(add);
        // HBox hbox = new HBox(search, add, hSpacer, filter);
        hbox.setBackground(assets.surface);

        vbox.getChildren().addAll(hbox, this.scrollPane);
        super.setContent(vbox);
    }

    public void updateContents(){
        this.fire();
    }

    public void setUpdateNotifier(EventHandler<ActionEvent> handler) {
        this.updateNotifier = handler;
        this.fire();
    }

    public void fire() {
        if (this.updateNotifier != null) {
            this.updateNotifier.handle(new ActionEvent());
        }
    }

    public void setAddAction(EventHandler<ActionEvent> action){
        add.setOnAction(action);
    }

}
