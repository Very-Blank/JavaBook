package ui.user;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import ui.*;
import data.Data;
import data.User;

/**
 * Custom UI component displaying user information in a card-like layout.
 * Shows name, email, phone number and birthday with consistent styling.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class UserBox extends HBox {
    private User user;
    private final double labelMargin = 10;

    /**
     * Constructs user information card with formatted data display
     * @param user User object containing data to display
     * @param assets styling resources for colors and fonts
     */
    public UserBox(User user, Assets assets) {
        super(10);
        this.user = user;

        this.setPrefHeight(50);
        this.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(fieldStart("Full Name", this.user.getName(), assets), field("Email", this.user.getEmail(), assets), field("PhoneNumber", this.user.getCountryCode() + " " + this.user.getPhoneNumber(), assets), field("Birthday", Data.dateToString(this.user.getBirthday()), assets));
        this.setBackground(assets.surface);
    }

    /**
     * Creates left-aligned data field with special margin handling
     * @param label field title text
     * @param data field content text
     * @param assets styling resources
     * @return formatted VBox container with label and data
     */

    public VBox fieldStart(String label, String data, Assets assets){
        Label lab = new Label(label);
        lab.setFont(assets.fonts.normal);
        lab.setTextFill(assets.textColor);
        VBox.setMargin(lab, new Insets(labelMargin, labelMargin, 0, labelMargin));

        Label dat = new Label(data);
        dat.setFont(assets.fonts.normal);
        dat.setTextFill(assets.textColor);
        VBox.setMargin(dat, new Insets(0, labelMargin, labelMargin, labelMargin));

        VBox vbox = new VBox(lab, dat);
        vbox.setBackground(assets.elevated);
        vbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(vbox, new Insets(labelMargin, 0, labelMargin, labelMargin));

        return vbox;
    }

    /**
     * Creates standard data field with centered alignment
     * @param label field title text
     * @param data field content text
     * @param assets styling resources
     * @return formatted VBox container with label and data
     */
    public VBox field(String label, String data, Assets assets){
        Label lab = new Label(label);
        lab.setFont(assets.fonts.normal);
        lab.setTextFill(assets.textColor);
        VBox.setMargin(lab, new Insets(labelMargin, labelMargin, 0, labelMargin));

        Label dat = new Label(data);
        dat.setFont(assets.fonts.normal);
        dat.setTextFill(assets.textColor);
        VBox.setMargin(dat, new Insets(0, labelMargin, labelMargin, labelMargin));

        VBox vbox = new VBox(lab, dat);
        vbox.setBackground(assets.elevated);
        vbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(vbox, new Insets(labelMargin, 0, labelMargin, 0));

        return vbox;
    }

    public User getUser(){
        return this.user;
    }

}

