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

public class UserDetailsWindow extends DetailsWindow<VBox> {
    private DatePicker datePicker;
    private TextField name;
    private TextField email;
    private PhoneNumber phoneNumber;

    private VBox fields;
    private VBox browser;

    private Button loan;
    private Button returnBooks;
    private Button save;

    public UserDetailsWindow(Assets assets){
        super(new VBox(), assets, "User Details", "Edit User");
        //
        //LabeledField<TextField> name = new LabeledField<TextField>("Name:", new TextField("USER'S NAME"));
        //this.name = name.child;
        //LabeledField<TextField> email = new LabeledField<TextField>("Email:", new TextField("EMAIL THIS IS PADDING TO"));
        //this.email = email.child;
        //LabeledField<PhoneNumber> phoneNumber = new LabeledField<PhoneNumber>("Phone Number:", new PhoneNumber());
        //this.phoneNumber = phoneNumber.child;
        //LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Birthday:", new DatePicker());
        //this.datePicker = datePicker.child;
        //datePicker.child.setValue(LocalDate.now());
        //
        //this.loan = new Button("Add Loan");
        //this.returnBooks = new Button("Return Books");
        //this.save = new Button("Save");
        //
        //HBox buttons = new HBox(this.loan, this.returnBooks);
        //
        //super.content.getChildren().addAll(this.name, this.email, this.phoneNumber, this.datePicker);
        //super.dialogue.getChildren().addAll(this.save);
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

