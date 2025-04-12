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

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;

import javafx.beans.property.*;

import data.*;
import ui.*;


public class ScrollableBookTab extends ScrollableTab{
    private ArrayList<BookBox> bookBoxs;

    public ScrollableBookTab(Assets assets, ArrayList<Book> bookDatas){
        super(assets, "Books ");
        updateContents(bookDatas);
    }

    public void updateContents(ArrayList<Book> bookDatas){
        this.bookBoxs = new ArrayList<BookBox>(bookDatas.size());

        if(bookDatas.size() != 0){
            TilePane content = new TilePane();
            content.prefWidthProperty().bind(this.scrollPane.widthProperty());
            content.setHgap(7);
            content.setVgap(7);

            for(int i = 0; i < bookDatas.size(); i++){
                BookBox bookBox = new BookBox(bookDatas.get(i), this.assets);
                bookBoxs.add(bookBox);

                content.getChildren().add(bookBox);
            }

            content.setBackground(assets.background);
            this.scrollPane.setContent(content);
        } else {
            TilePane content = new TilePane();
            content.prefWidthProperty().bind(this.scrollPane.widthProperty());
            content.setHgap(7);
            content.setVgap(7);

            content.setBackground(assets.background);
            this.scrollPane.setContent(content);
        }

        super.updateContents();
    }

    public ArrayList<BookBox> getBookBoxs(){
        return this.bookBoxs;
    }
}


