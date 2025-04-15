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

public class PhoneNumber extends HBox {
    private FuckFxMenuButton countrySelector;
    private String selectedCountryCode;
    private TextField textField;

    public PhoneNumber(String phoneNumber, String countryCode, Assets assets) {
        super();
        String[] countryCodes = new String[] { "+358", "+1", "+46", "+81", "+82" };
        this.textField = new TextField();
        this.selectedCountryCode = countryCode;
        this.countrySelector = new FuckFxMenuButton(countryCode);

        this.textField.setText(phoneNumber);

        this.countrySelector
                .setMenuBackground(new Background(new BackgroundFill(Color.valueOf(assets.gray2), null, null)));
        this.countrySelector.setMenuItemBackground(assets.elevated);
        this.countrySelector.setMenuItemFontColor(assets.textColor);
        this.countrySelector.setMenuItemFont(assets.fonts.normal);

        this.countrySelector.setBackground(assets.elevated);
        this.countrySelector.setFont(assets.fonts.normal);
        this.countrySelector.setTextFill(assets.textColor);

        for (int i = 0; i < countryCodes.length; i++) {
            final String code = countryCodes[i];
            FuckFxMenuItem menuItem = new FuckFxMenuItem(code);
            this.countrySelector.addMenuItem(menuItem);
            menuItem.setOnAction((_) -> {
                countrySelector.setText(code);
                countrySelector.closePopup();
                selectedCountryCode = code;
            });
        }

        this.textField.textProperty().addListener(new ChangeListener<String>() {
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

    public String getPhoneNumber() {
        return this.selectedCountryCode + " " + textField.getText();
    }
}
