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

public class BookDetailsWindow extends DetailsWindow<VBox> {
    private TextField title;
    private TextField author;
    private TextArea summary;
    private DatePicker datePicker;
    private Label imagePath;
    private Book book;

    private final double textWidth = 450;

    public BookDetailsWindow(Book book, String loanedBy, Assets assets) {
        super(new VBox(), assets, "Book Details", "Edit book");
        this.book = book;
        this.imagePath = new Label(book.getImagePath());
        this.summary = new TextArea(this.book.getSummary());
        this.datePicker = new DatePicker();

        this.title = newTextField(this.book.getTitle(), this.textWidth);
        LabeledField<TextField> title = new LabeledField<TextField>("Title:", this.title, assets);

        this.author = newTextField(this.book.getAuthor(), this.textWidth);
        LabeledField<TextField> author = new LabeledField<TextField>("Author:", this.author, assets);

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

        this.datePicker.setValue(book.getPublication());
        LabeledField<DatePicker> datePicker = new LabeledField<DatePicker>("Publication Date:", this.datePicker,
                assets);

        LabeledField<HBox> path = new LabeledField<HBox>("ImagePath:", createLabelField(this.imagePath), assets);

        Stage window = this;
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
        if (loanedBy != null) {
            this.content.getChildren()
                    .add(new LabeledField<HBox>("Loaned by:", createLabelField(new Label(loanedBy)), assets));
        }
    }

    private HBox createLabelField(Label label) {
        label.setMinHeight(25);
        label.setTextFill(this.assets.textColor);
        label.setMinWidth(this.textWidth);
        label.setMaxWidth(this.textWidth);
        HBox.setMargin(label, new Insets(0, 0, 0, 8));
        HBox hbox = new HBox(label);
        hbox.setBackground(this.assets.surface);
        hbox.setMinWidth(this.textWidth);
        hbox.setMaxWidth(this.textWidth);

        return hbox;
    }

    public Book getBook() {
        this.book.update(this.title.getText(), this.author.getText(), this.summary.getText(), this.imagePath.getText(),
                getDatePickerValue());
        return this.book;
    }

    public void setDatePickerValue(LocalDate date) {
        this.datePicker.setValue(date);
    }

    public LocalDate getDatePickerValue() {
        return this.datePicker.getValue();
    }

    public void setDatePickerAction(EventHandler<ActionEvent> action) {
        this.datePicker.setOnAction(action);
    }

    public void setImagePathAction(EventHandler<MouseEvent> action) {
        this.imagePath.setOnMouseClicked(action);
    }
}
