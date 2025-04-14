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

    // private VBox fields;
    // private VBox browser;

    private Button loan;
    private Button returnBooks;
    private Button save;
    private User user;

    public UserDetailsWindow(User user, Assets assets) {
        super(new VBox(), assets, "User Details", "Edit User");
        this.user = user;
        this.name = new TextField(this.user.name());
        this.email = new TextField("EMAIL THIS IS PADDING TO");
        this.phoneNumber = new PhoneNumber(assets);
        this.datePicker = new DatePicker(LocalDate.now());

        LabeledField<TextField> name = new LabeledField<TextField>("Name:", this.name, assets);
        LabeledField<TextField> email = new LabeledField<TextField>("Email:", this.email, assets);
        LabeledField<PhoneNumber> phoneNumber = new LabeledField<PhoneNumber>("Phone Number:", this.phoneNumber,
                assets);
        LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Birthday:", this.datePicker, assets);

        HBox hbox = new HBox(20.0);
        VBox detailHolder = new VBox(name, email, phoneNumber, datePicker);
        detailHolder.setMaxWidth(250);
        detailHolder.setMinWidth(250);

        ArrayList<Book> books = new ArrayList<Book>(30);
        for (int i = 0; i < 30; i++) {
            books.add(new Book());
        }

        TabPane tabBar = new TabPane();
        HBox.setHgrow(tabBar, Priority.ALWAYS);
        tabBar.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabBar.setBackground(assets.background);

        Platform.runLater(() -> {
            tabBar.lookup(".tab-header-background").setStyle(String.format("-fx-background-color: %s;", assets.gray1));

            Set<Node> tabs = tabBar.lookupAll(".tab");

            for (Node tab : tabs) {
                tab.setStyle(String.format("-fx-background-color: %s;", assets.gray2));
            }
        });

        ScrollableBookTab loanTab = new ScrollableBookTab(assets, "Available Books ", books);
        tabBar.getTabs().addAll(loanTab);

        hbox.getChildren().addAll(detailHolder, tabBar);

        // this.loan = new Button("Add Loan");
        // this.returnBooks = new Button("Return Books");
        // this.save = new Button("Save");

        // HBox buttons = new HBox(this.loan, this.returnBooks);

        super.content.getChildren().addAll(hbox);
    }

    public User getUser() {
        this.user.update(this.name.getText(), this.email.getText(), this.phoneNumber.getPhoneNumber(),
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
