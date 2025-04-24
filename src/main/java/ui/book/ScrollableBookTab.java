package ui.book;

import javafx.scene.layout.*;
import java.util.ArrayList;
import data.*;
import ui.*;

/**
 * Scrollable tab component displaying books in a tile grid layout using BookBox components.
 * Handles dynamic updates of book displays and maintains visual consistency.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class ScrollableBookTab extends ScrollableTab {
    private ArrayList<BookBox> bookBoxs;

    /**
     * Creates book tab with initial dataset
     * @param assets styling resources and color definitions
     * @param name tab header text
     * @param bookDatas initial collection of books to display
     */
    public ScrollableBookTab(Assets assets, String name, ArrayList<Book> bookDatas) {
        super(assets, name);
        updateContents(bookDatas);
    }

    /**
     * Refreshes tab content with updated book collection. Creates new BookBox
     * components arranged in a grid, or clears content if empty.
     * @param bookDatas current collection of books to display
     */
    public void updateContents(ArrayList<Book> bookDatas) {
        this.bookBoxs = new ArrayList<BookBox>(bookDatas.size());

        TilePane content = new TilePane();
        content.setHgap(7);
        content.setVgap(7);

        if (!bookDatas.isEmpty()) {
            for (int i = 0; i < bookDatas.size(); i++) {
                BookBox bookBox = new BookBox(bookDatas.get(i), this.assets);
                bookBoxs.add(bookBox);
                content.getChildren().add(bookBox);
            }
        }

        content.setBackground(this.assets.background);
        this.scrollPane.setContent(content);
        super.updateContents();
    }

    /**
     * @return current list of BookBox components in the tab
     */
    public ArrayList<BookBox> getBookBoxs() {
        return this.bookBoxs;
    }
}
