package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;

public class FuckFxFinalMenuItem extends HBox{
    private FuckFxMenuItem menuItem;
    private Label label;
    private Pane offset;

    public FuckFxFinalMenuItem(FuckFxMenuItem item, Insets insets, double height, double width, double textSpacing){
        super();
        this.label = new Label(item.getText());
        this.offset = new Pane();
        this.menuItem = item;

        this.setAlignment(Pos.CENTER_LEFT);

        this.setOnMouseClicked((_) -> {
            this.menuItem.fire();
        });

        this.setMinHeight(height);
        this.setMinWidth(width);

        this.offset.setMinHeight(height);
        this.offset.setMinWidth(textSpacing);

        VBox.setMargin(this, insets);
        this.getChildren().addAll(this.offset, this.label);
    }

    public void setFontColor(Color color){
        this.label.setTextFill(color);
    }

    public void setFont(Font font){
        this.label.setFont(font);
    }

    public void setMinTextSpacing(double textSpacing){
        this.offset.setMinWidth(textSpacing);
    }

    public boolean equalsTo(FuckFxMenuItem item){
        return this.menuItem == item;
    }
}
