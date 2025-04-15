package ui.user;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.application.Platform;
import javafx.event.*;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Set;

import ui.*;
import ui.book.BookBox;
import ui.book.ScrollableBookTab;
import data.Book;
import data.User;

public class UserDetailsWindow extends DetailsWindow<VBox> {
    private DatePicker datePicker;
    private TextField name;
    private TextField email;
    private PhoneNumber phoneNumber;

    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;

    private final double textWidth = 250;
    private User user;

    public UserDetailsWindow(User user, ArrayList<Book> availableBooks, ArrayList<Book> loanedBooks, Assets assets) {
        super(new VBox(), assets, "User Details", "Edit User");
        this.user = user;

        this.availableBooks = availableBooks;
        this.loanedBooks = availableBooks;

        this.name = this.newTextField(this.user.name(), this.textWidth);
        this.email = this.newTextField(this.user.email(), this.textWidth);
        this.phoneNumber = new PhoneNumber(this.user.countryCode(), this.user.phoneNumber(), assets);
        this.datePicker = new DatePicker(LocalDate.now());

        LabeledField<TextField> name = new LabeledField<TextField>("Name:", this.name, this.assets);
        LabeledField<TextField> email = new LabeledField<TextField>("Email:", this.email, this.assets);
        LabeledField<PhoneNumber> phoneNumber = new LabeledField<PhoneNumber>("Phone Number:", this.phoneNumber,
                this.assets);
        LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Birthday:", this.datePicker, this.assets);

        HBox hbox = new HBox(20.0);
        VBox detailHolder = new VBox(name, email, phoneNumber, datePicker);

        TabPane tabBar = new TabPane();
        HBox.setHgrow(tabBar, Priority.ALWAYS);
        tabBar.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabBar.setBackground(this.assets.background);

        Platform.runLater(() -> {
            tabBar.lookup(".tab-header-background")
                    .setStyle(String.format("-fx-background-color: %s;", this.assets.gray1));

            Set<Node> tabs = tabBar.lookupAll(".tab");

            for (Node tab : tabs) {
                tab.setStyle(String.format("-fx-background-color: %s;", this.assets.gray2));
            }
        });

        ScrollableBookTab loanTab = new ScrollableBookTab(this.assets, "Available Books ", availableBooks);
        ScrollableBookTab returnLoanTab = new ScrollableBookTab(this.assets, "Loaned Books ", loanedBooks);
        tabBar.getTabs().addAll(loanTab, returnLoanTab);
        // tabBar.requestLayout();

        hbox.getChildren().addAll(detailHolder, tabBar);

        super.content.getChildren().addAll(hbox);
    }

    public User getUser() {
        this.user.update(this.name.getText(), this.email.getText(), this.phoneNumber.getCountryCode(),
                this.phoneNumber.getPhoneNumber(),
                this.user.loans(), getDatePickerValue());
        return this.user;
    }

    public void setDatePickerValue(LocalDate date) {
        datePicker.setValue(date);
    }

    public LocalDate getDatePickerValue() {
        return datePicker.getValue();
    }

    public void setDatePickerAction(EventHandler<ActionEvent> action) {
        datePicker.setOnAction(action);
    }

}
