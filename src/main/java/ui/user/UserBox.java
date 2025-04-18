package ui.user;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;

import ui.*;
import data.Data;
import data.User;

public class UserBox extends HBox {
    private User user;
    private final double labelMargin = 10;

    public UserBox(User user, Assets assets) {
        super(10);
        this.user = user;

        this.setPrefHeight(50);
        this.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(fieldStart("Full Name", this.user.name(), assets), field("Email", this.user.email(), assets), field("PhoneNumber", this.user.countryCode() + " " + this.user.phoneNumber(), assets), field("Birthday", Data.dateToString(this.user.birthday()), assets));
        this.setBackground(assets.surface);
    }


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

