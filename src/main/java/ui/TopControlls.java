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

public class TopControlls extends HBox{
    private FuckFxMenuItem saveItem;
    private FuckFxMenuItem openItem;
    private FuckFxMenuItem newItem;
    public FuckFxMenuItem quitItem;
    private MenuButton menuButton;

    public TopControlls(Assets assets){
        super();

        this.setBackground(assets.surface);

        this.openItem = new FuckFxMenuItem("Open ");
        this.newItem = new FuckFxMenuItem("New ");
        this.quitItem = new FuckFxMenuItem("Quit ");

        FuckFxMenuButton menu = new FuckFxMenuButton("Options ", this.openItem, this.newItem, this.quitItem);
        menu.setMenuBackground(new Background(new BackgroundFill(Color.valueOf(assets.gray2), null, null)));
        menu.setMenuItemBackground(assets.elevated);
        menu.setMenuItemFontColor(assets.textColor);
        menu.setMenuItemFont(assets.fonts.normal);

        menu.setBackground(assets.elevated);
        menu.setFont(assets.fonts.normal);
        menu.setTextFill(assets.textColor);

        HBox.setMargin(menu, new Insets(5, 0, 5, 5));

        super.getChildren().addAll(menu);
    }

    public Label createLabelWidth(String name, Assets assets){
        Label label = new Label(name);
        label.setFont(assets.fonts.normal);
        label.setTextFill(assets.textColor);
        label.setMinWidth(150);
        return label;
    }

    public Label createLabel(String name, Assets assets){
        Label label = new Label(name);
        label.setFont(assets.fonts.normal);
        label.setTextFill(assets.textColor);
        return label;
    }
}
