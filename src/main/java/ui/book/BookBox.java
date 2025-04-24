package ui.book;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;

import java.io.FileInputStream;
import data.Book;
import data.Data;
import ui.Assets;

/**
 * Visual component representing a book entry in a grid layout.
 * Displays cover image, metadata, and loan status in a standardized card format.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class BookBox extends VBox {
    private Node visual;
    private Label title;
    private Label author;
    private Label publicationDate;
    private Label status;
    private Book book;

    private final double width = 180;
    private final double height = 275;
    private final double contentSize = 150;
    private final double offset;

    /**
     * Constructs book display card with image and metadata
     * @param book Book object containing data to display
     * @param assets styling resources for colors and fonts
     */
    public BookBox(Book book, Assets assets) {
        super(5.0);
        this.setAlignment(Pos.CENTER);
        this.setMinSize(this.width, this.height);
        this.setMaxSize(this.width, this.height);

        this.offset = (this.width - this.contentSize) / 2.0;
        this.book = book;

        try {
            FileInputStream input = new FileInputStream(book.getImagePath());
            Image data = new Image(input);

            ImageView image = new ImageView(data);
            image.setFitHeight(this.contentSize);
            image.setFitWidth(this.contentSize);
            this.visual = image;

        } catch (Exception e) {
            Label missingText = new Label("Missing\nPicture");
            missingText.setFont(assets.fonts.medium);
            missingText.setTextFill(assets.textColor);
            StackPane pane = new StackPane(missingText);

            pane.setBackground(assets.elevated);

            pane.setMinSize(this.contentSize, this.contentSize);
            pane.setMaxSize(this.contentSize, this.contentSize);
            this.visual = pane;
        }

        this.title = createText(this.book.getTitle(), assets);
        VBox.setMargin(this.title, new Insets(this.offset / 3.0, 0, 0, this.offset / 3.0));

        this.author = createText(this.book.getAuthor(), assets);
        VBox.setMargin(this.author, new Insets(0, 0, 0, this.offset / 3.0));

        this.publicationDate = createText(Data.dateToString(this.book.getPublication()), assets);
        VBox.setMargin(this.publicationDate, new Insets(0, 0, 0, this.offset / 3.0));

        this.status = createText(this.book.loanToString(), assets);
        VBox.setMargin(this.status, new Insets(0, 0, this.offset / 3.0, this.offset / 3.0));

        VBox box = new VBox(title, author, publicationDate, status);
        box.setSpacing(5.0);
        box.setBackground(assets.elevated);
        box.setMaxWidth(this.contentSize);

        super.getChildren().addAll(visual, box);

        super.setBackground(assets.surface);
    }

    public Label createText(String string, Assets assets) {
        Label label = new Label(string);
        label.setFont(assets.fonts.normal);
        label.setTextFill(assets.textColor);
        label.setMaxWidth(this.contentSize - this.offset / 3.0 * 2);
        return label;
    }

    public Book getBook() {
        return this.book;
    }
}
