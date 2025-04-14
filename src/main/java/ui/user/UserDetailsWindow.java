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
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;

import ui.*;
import ui.book.BookBox;
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

    public UserDetailsWindow(User user, Assets assets){
        super(new VBox(), assets, "User Details", "Edit User");
        this.user = user;
        this.name = new TextField(this.user.name());
        this.email = new TextField("EMAIL THIS IS PADDING TO");
        this.phoneNumber = new PhoneNumber(assets);
        this.datePicker = new DatePicker(LocalDate.now());

        LabeledField<TextField> name = new LabeledField<TextField>("Name:", this.name, assets);
        LabeledField<TextField> email = new LabeledField<TextField>("Email:", this.email, assets);
        LabeledField<PhoneNumber> phoneNumber = new LabeledField<PhoneNumber>("Phone Number:", this.phoneNumber, assets);
        LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Birthday:", this.datePicker, assets);

        HBox hbox = new HBox(80.0);
        VBox detailHolder = new VBox(name, email, phoneNumber, datePicker);
        detailHolder.setMaxWidth(250);
        detailHolder.setMinWidth(250);

        ScrollPane loanHolder = new ScrollPane();
        loanHolder.setFitToWidth(true);
        loanHolder.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        loanHolder.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        String css = String.format("""
            .scroll-pane > .viewport {
                -fx-background-color: %s;
            }
        """, assets.gray1);

        loanHolder.getStylesheets().add("data:text/css," + css);
        ArrayList<BookBox> bookBoxs = new ArrayList<BookBox>(30);
        HBox.setHgrow(loanHolder, Priority.ALWAYS);

        if(true){
            TilePane content = new TilePane();
            content.prefWidthProperty().bind(loanHolder.widthProperty());
            content.setHgap(7);
            content.setVgap(7);

            for(int i = 0; i < 30; i++){
                BookBox bookBox = new BookBox(new Book(), assets);
                bookBoxs.add(bookBox);

                content.getChildren().add(bookBox);
            }

            content.setBackground(assets.background);
            loanHolder.setContent(content);
        } else {
            // TilePane content = new TilePane();
            // content.prefWidthProperty().bind(this.scrollPane.widthProperty());
            // content.setHgap(7);
            // content.setVgap(7);
            //
            // content.setBackground(assets.background);
            // this.scrollPane.setContent(content);
        }


        hbox.getChildren().addAll(detailHolder, loanHolder);

        // this.loan = new Button("Add Loan");
        // this.returnBooks = new Button("Return Books");
        // this.save = new Button("Save");

        // HBox buttons = new HBox(this.loan, this.returnBooks);

        super.content.getChildren().addAll(hbox);
    }


    public User getUser(){
        this.user.update(this.name.getText(), this.email.getText(), this.phoneNumber.getPhoneNumber(), this.user.loans(), getDatePickerValue());
        return this.user;
    }

    public void setDatePickerValue(LocalDate date){
        datePicker.setValue(date);
    }

    public LocalDate getDatePickerValue(){
        return datePicker.getValue();
    }

    public void setDatePickerAction(EventHandler<ActionEvent> action){
        datePicker.setOnAction(action);
    }

}

