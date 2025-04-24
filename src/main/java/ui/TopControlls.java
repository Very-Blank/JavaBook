package ui;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.event.*;

public class TopControlls extends HBox{
    private FuckFxMenuItem openItem;
    private FuckFxMenuItem newItem;
    private FuckFxMenuItem saveItem;
    private FuckFxMenuItem quitItem;

    public TopControlls(Assets assets){
        super();

        this.setBackground(assets.surface);

        this.openItem = new FuckFxMenuItem("Open ");
        this.newItem = new FuckFxMenuItem("New ");
        this.saveItem = new FuckFxMenuItem("Save ");
        this.quitItem = new FuckFxMenuItem("Quit ");

        FuckFxMenuButton menu = new FuckFxMenuButton("Options ", this.openItem, this.newItem, this.saveItem, this.quitItem);
        this.setMenuStyling(menu, assets);

        HBox.setMargin(menu, new Insets(5, 0, 5, 5));

        super.getChildren().addAll(menu);
    }

    private void setMenuStyling(FuckFxMenuButton menu, Assets assets){
        menu.setMenuBackground(new Background(new BackgroundFill(Color.valueOf(assets.gray2), null, null)));
        menu.setMenuItemBackground(assets.elevated);
        menu.setMenuItemFontColor(assets.textColor);
        menu.setMenuItemFont(assets.fonts.normal);

        menu.setBackground(assets.elevated);
        menu.setFont(assets.fonts.normal);
        menu.setTextFill(assets.textColor);
    }

    public void setOpenAction(EventHandler<ActionEvent> action) {
        this.openItem.setOnAction(action);
    }

    public void setNewAction(EventHandler<ActionEvent> action) {
        this.newItem.setOnAction(action);
    }

    public void setSaveAction(EventHandler<ActionEvent> action) {
        this.saveItem.setOnAction(action);
    }

    public void setQuitAction(EventHandler<ActionEvent> action) {
        this.quitItem.setOnAction(action);
    }
}
