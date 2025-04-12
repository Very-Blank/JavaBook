package ui.book;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileInputStream;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;

import ui.*;
import data.Book;

public class BookDetailsWindow extends DetailsWindow<VBox>{
    protected TextField title;
    protected TextField author;
    protected TextArea summary;
    protected DatePicker datePicker;
    protected Label imagePath;
    protected Book book;

    protected final double textWidth =  450;

    public BookDetailsWindow(Book book, Assets assets){
        super(new VBox(), assets, "Book Details", "Edit book");
        this.book = book;

        this.title = newTextField(this.book.title(), assets);
        LabeledField<TextField> title = new LabeledField<TextField>("Title:", this.title, assets);

        this.author = newTextField(this.book.author(), assets);
        LabeledField<TextField> author = new LabeledField<TextField>("Author:", this.author, assets);

        this.summary = new TextArea(this.book.summary());
        this.summary.setMinWidth(this.textWidth);
        this.summary.setMaxWidth(this.textWidth);
        this.summary.setStyle(String.format("""
            -fx-text-fill: %s;
            -fx-control-inner-background: %s;
            -fx-border-color: %s;
            -fx-border-width: 0;
            -fx-border-height: 0;
            -fx-focus-color: %s;
            -fx-faint-focus-color: %s;
        """, assets.white1, assets.gray2, assets.gray2, assets.gray2, assets.gray2, assets.gray2));


        LabeledField<TextArea> summary = new LabeledField<TextArea>("Summary:", this.summary, assets);

        this.datePicker = new DatePicker();

        this.datePicker.setValue(book.publication());
        LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Publication Date:", this.datePicker, assets);

        this.imagePath = new Label(book.imagePath());
        this.imagePath.setMinHeight(25);
        this.imagePath.setTextFill(assets.textColor);
        this.imagePath.setMinWidth(this.textWidth);
        this.imagePath.setMaxWidth(this.textWidth);
        HBox.setMargin(this.imagePath, new Insets(0, 0, 0, 8));
        HBox hbox = new HBox(this.imagePath);
        hbox.setBackground(assets.surface);
        hbox.setMinWidth(this.textWidth);
        hbox.setMaxWidth(this.textWidth);

        Stage window = this;

        LabeledField<HBox> path = new LabeledField<HBox>("ImagePath:", hbox, assets);

        setImagePathAction(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
                File selectedFile = fileChooser.showOpenDialog(window);
                if (selectedFile != null) {
                    imagePath.setText(selectedFile.getAbsolutePath());
                }
            }
        });


        this.content.getChildren().addAll(title, author, summary, datePicker, path);
    }

    public Book getBook(){
        this.book.update(this.title.getText(), this.author.getText(), this.summary.getText(), this.imagePath.getText(), getDatePickerValue());
        return this.book;
    }

    private TextField newTextField(String string, Assets assets){
        TextField textField = new TextField(string);
        textField.setBackground(assets.surface);
        textField.setStyle(String.format("-fx-text-fill: %s;", assets.white1));
        textField.setMinWidth(this.textWidth);
        textField.setMaxWidth(this.textWidth);
        return textField;
    }

    public void setDatePickerValue(LocalDate date){
        this.datePicker.setValue(date);
    }

    public LocalDate getDatePickerValue(){
        return this.datePicker.getValue();
    }

    public void setDatePickerAction(EventHandler<ActionEvent> action){
        this.datePicker.setOnAction(action);
    }

    public void setImagePathAction(EventHandler<MouseEvent> action){
        this.imagePath.setOnMouseClicked(action);
    }
}
