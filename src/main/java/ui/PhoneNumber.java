package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Composite UI component combining country code selection and phone number input.
 * Provides validation to ensure numeric-only phone number entry.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class PhoneNumber extends HBox {
    private FuckFxMenuButton countrySelector;
    private String selectedCountryCode;
    private TextField textField;

    /**
     * Constructs phone number input component with specified initial values.
     * @param countryCode initial country code prefix (e.g. "+358")
     * @param phoneNumber initial phone number digits
     * @param assets styling resources and color definitions
     */
    public PhoneNumber(String countryCode, String phoneNumber, Assets assets) {
        super();
        String[] countryCodes = new String[] { "+358", "+1", "+46", "+81", "+82" };
        this.textField = new TextField();
        this.selectedCountryCode = countryCode;
        this.countrySelector = new FuckFxMenuButton(countryCode);

        this.setMaxWidth(250);

        this.countrySelector.setMinHeight(28);

        this.textField.setMinHeight(29);

        this.textField.setText(phoneNumber.replaceAll("[^\\d]", ""));
        this.textField.setBackground(assets.surface);
        this.textField.setStyle(String.format("-fx-text-fill: %s;", assets.white1));
        HBox.setHgrow(this.textField, Priority.ALWAYS);

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


    /** @return currently selected country code prefix */
    public String getCountryCode() {
        return this.selectedCountryCode;
    }

    /** @return sanitized phone number containing only digits */
    public String getPhoneNumber() {
        return this.textField.getText();
    }
}
