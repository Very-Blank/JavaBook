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
import data.Loan;
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
        this.loanedBooks = loanedBooks;

        this.name = this.newTextField(this.user.getName(), this.textWidth);
        this.email = this.newTextField(this.user.getEmail(), this.textWidth);
        this.phoneNumber = new PhoneNumber(this.user.getCountryCode(), this.user.getPhoneNumber(), assets);
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

        ScrollableBookTab loanTab = new ScrollableBookTab(this.assets, "Available Books ", this.availableBooks);
        ScrollableBookTab returnLoanTab = new ScrollableBookTab(this.assets, "Loaned Books ", this.loanedBooks);

        loanTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<BookBox> bookBoxs = loanTab.getBookBoxs();
                for (int i = 0; i < bookBoxs.size(); i++) {
                    BookBox bookbox = bookBoxs.get(i);
                    int ID = bookbox.getBook().getID();
                    bookbox.setOnMouseClicked((_) -> {
                        for (int j = 0; j < availableBooks.size(); j++) {
                            if (ID == availableBooks.get(j).getID()) {
                                availableBooks.remove(j);
                            }
                        }

                        loanedBooks.add(bookbox.getBook());

                        loanTab.updateContents(availableBooks);
                        returnLoanTab.updateContents(loanedBooks);
                    });
                }
            }
        });

        returnLoanTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<BookBox> bookBoxs = returnLoanTab.getBookBoxs();
                for (int i = 0; i < bookBoxs.size(); i++) {
                    BookBox bookbox = bookBoxs.get(i);
                    int ID = bookbox.getBook().getID();

                    bookbox.setOnMouseClicked((_) -> {
                        for (int j = 0; j < loanedBooks.size(); j++) {
                            if (ID == loanedBooks.get(j).getID()) {
                                loanedBooks.remove(j);
                            }
                        }

                        bookbox.getBook().setLoan(-1);
                        availableBooks.add(bookbox.getBook());

                        loanTab.updateContents(availableBooks);
                        returnLoanTab.updateContents(loanedBooks);
                    });
                }
            }
        });

        tabBar.getTabs().addAll(loanTab, returnLoanTab);

        hbox.getChildren().addAll(detailHolder, tabBar);

        super.content.getChildren().addAll(hbox);
    }

    public ArrayList<Book> getLoanedBooks() {
        return this.loanedBooks;
    }

    public User getUser() {
        this.user.update(this.name.getText(), this.email.getText(), this.phoneNumber.getCountryCode(),
                this.phoneNumber.getPhoneNumber(), getDatePickerValue());
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
