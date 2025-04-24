package ui.book;

import javafx.scene.layout.*;

import java.util.ArrayList;

import data.*;
import ui.*;

public class ScrollableBookTab extends ScrollableTab {
    private ArrayList<BookBox> bookBoxs;

    public ScrollableBookTab(Assets assets, String name, ArrayList<Book> bookDatas) {
        super(assets, name);
        updateContents(bookDatas);
    }

    public void updateContents(ArrayList<Book> bookDatas) {
        this.bookBoxs = new ArrayList<BookBox>(bookDatas.size());

        if (bookDatas.size() != 0) {
            TilePane content = new TilePane();
            content.setHgap(7);
            content.setVgap(7);

            for (int i = 0; i < bookDatas.size(); i++) {
                BookBox bookBox = new BookBox(bookDatas.get(i), this.assets);
                bookBoxs.add(bookBox);

                content.getChildren().add(bookBox);
            }

            content.setBackground(this.assets.background);
            this.scrollPane.setContent(content);
        } else {
            TilePane content = new TilePane();
            content.setHgap(7);
            content.setVgap(7);

            content.setBackground(this.assets.background);
            this.scrollPane.setContent(content);
        }

        super.updateContents();
    }

    public ArrayList<BookBox> getBookBoxs() {
        return this.bookBoxs;
    }
}
