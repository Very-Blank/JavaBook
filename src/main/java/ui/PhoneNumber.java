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

//import ui.Fonts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PhoneNumber extends HBox{
    MenuButton countrySelector;
    TextField textField;
    public PhoneNumber(){
        super();
        String[] countryCodes = new String[]{"+358", "+1", "+46", "+81", "+82"};
        countrySelector = new MenuButton(countryCodes[0], null);
        countrySelector.setMinWidth(70);


        for(String code : countryCodes){
            MenuItem item = new MenuItem(code);
            item.setOnAction(event->{
                countrySelector.setText(item.getText());
            });

            countrySelector.getItems().add(item);
        }


        textField = new TextField();
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        super.getChildren().addAll(countrySelector, textField);
    }
}

