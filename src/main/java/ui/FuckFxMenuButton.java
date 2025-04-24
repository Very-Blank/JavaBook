package ui;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.Vector;
import javafx.application.Platform;

public class FuckFxMenuButton extends Button {
    private Popup popup;
    private VBox vbox;
    private VBox holder;
    private Pane upSpacing;
    private Pane downSpacing;
    private Vector<FuckFxFinalMenuItem> menuItems;

    private Font font = null;
    private Color fontColor = null;

    private Background menuItemBackground = null;

    private double menuOffset = 5;
    private FuckFxSide side = FuckFxSide.LEFT;
    private FuckFxHeight sideHeight = FuckFxHeight.BELOW;
    private Insets insets = new Insets(0, 5, 0, 5);
    private double height = 28;
    private double width = 120;
    private double textSpacing = 5;

    public FuckFxMenuButton(String name, FuckFxMenuItem... items) {
        super(name);
        this.vbox = new VBox(5.0);
        this.upSpacing = new Pane();
        this.downSpacing = new Pane();
        this.popup = new Popup();
        this.menuItems = new Vector<FuckFxFinalMenuItem>();

        this.setMinHeight(28);

        this.upSpacing.setMinHeight(5);
        this.downSpacing.setMinHeight(5);

        this.holder = new VBox(this.upSpacing, this.vbox, this.downSpacing);

        this.popup.getContent().add(this.holder);
        this.popup.setAutoHide(true);

        for(int i = 0; i < items.length; i++){
            this.addMenuItem(items[i]);
        }
    }

    private void openPopup(){
        if (this.menuItems.size() != 0) {
            Point2D coords = getPopupPos();

            if (popup.isShowing()) {
                return;
            }

            this.popup.show(this.getScene().getWindow(), coords.getX(), coords.getY());

            //This shit needs to be run later since for some reason it needs to be lmao
            Platform.runLater(() -> {
                Point2D newCoords = getPopupPos();

                this.popup.setX(newCoords.getX());
                this.popup.setY(newCoords.getY());
            });
        }
    }

    public void closePopup(){
        if (popup.isShowing()) {
            this.popup.hide();
        }
    }

    @Override
    public void fire() {
        super.fire();
        openPopup();
    }

    public Point2D getPopupPos(){
        Point2D screenCoords = this.localToScreen(new Point2D(0, 0));
        double x = screenCoords.getX() - menuOffset;
        double y = screenCoords.getY();

        switch (this.side) {
            case FuckFxSide.LEFT:
                break;
            case FuckFxSide.RIGHT:
                x -= this.holder.getWidth();
                x += this.getWidth();
                break;
        }

        switch (this.sideHeight) {
            case FuckFxHeight.BELOW:
                y += this.getHeight();
                break;
            case FuckFxHeight.ABOVE:
                y -= this.holder.getHeight();
                break;
        }

        return new Point2D(x, y);
    }

    public void addMenuItem(FuckFxMenuItem menuItem){
        FuckFxFinalMenuItem finalMenuItem = new FuckFxFinalMenuItem(menuItem, this.insets, this.height, this.width, this.textSpacing);

        if(this.menuItemBackground != null){
            finalMenuItem.setBackground(this.menuItemBackground);
        }

        if(this.font != null){
            finalMenuItem.setFont(this.font);
        }

        if(this.fontColor != null){
            finalMenuItem.setFontColor(this.fontColor);
        }

        this.menuItems.add(finalMenuItem);
        this.vbox.getChildren().add(finalMenuItem);
    }

    public void setMenuSide(FuckFxSide side){
        this.side = side;
    }

    public void setMenuSide(FuckFxHeight height){
        this.sideHeight = height;
    }

    public void setMenuOffset(double menuOffset){
        this.menuOffset = menuOffset;
    }

    public void setMenuBackground(Background background){
        this.holder.setBackground(background);
    }

    public void setMenuItemBackground(Background background){
        this.menuItemBackground = background;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setBackground(this.menuItemBackground);
        }
    }

    public void setMenuItemFontColor(Color color){
        this.fontColor = color;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setFontColor(this.fontColor);
        }
    }

    public void setMenuItemFont(Font font){
        this.font = font;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setFont(this.font);
        }
    }

    public void setMenuSpacing(double spacing){
        this.vbox.setSpacing(spacing);
    }

    public void setMenuDownSpacing(double spacing){
        this.upSpacing.setMinHeight(spacing);
    }

    public void setMenuUpSpacing(double spacing){
        this.downSpacing.setMinHeight(spacing);
    }

    public void setTextSpacing(double textSpacing){
        this.textSpacing = textSpacing;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setMinTextSpacing(this.textSpacing);
        }
    }

    public void setMenuInsets(Insets insets){
        this.insets = insets;
        for(int i = 0; i < this.menuItems.size(); i++){
            VBox.setMargin(this.menuItems.get(i), this.insets);
        }
    }

    public void setMenuHeight(double size){
        this.height = size;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setMinHeight(size);
        }
    }

    public void setMenuWidth(double size){
        this.width = size;
        for(int i = 0; i < this.menuItems.size(); i++){
            this.menuItems.get(i).setMinWidth(size);
        }
    }
}

