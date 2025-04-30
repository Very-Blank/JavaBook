package ui.user;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.beans.value.ChangeListener;
import java.util.ArrayList;
import data.*;
import ui.*;

/**
 * Scrollable tab component specialized for displaying user entries in a vertical list.
 * Dynamically updates content based on provided user data and maintains UserBox components.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class ScrollableUserTab extends ScrollableTab {
    private ArrayList<UserBox> userBoxs;
    private TextField search;

    /**
     * Creates user tab with initial dataset
     * @param assets styling resources and color definitions
     * @param userDatas initial collection of users to display
     */
    public ScrollableUserTab(Assets assets, ArrayList<User> userDatas) {
        super(assets, "Users ");
        this.search = new TextField("");
        HBox.setMargin(this.search, new Insets(0, 0, 0, 5));
        this.search.setBackground(this.assets.elevated);
        this.search.setStyle(String.format("-fx-text-fill: %s;", this.assets.white1));
        this.search.setMinHeight(28);
        this.search.setMinWidth(250);
        this.search.setMaxWidth(250);
        this.customHolder.getChildren().add(this.search);
        updateContents(userDatas);
    }

    /**
     * Refreshes tab content with updated user collection. Creates new UserBox
     * components for each entry or clears content if empty.
     * @param userDatas current collection of users to display
     */
    public void updateContents(ArrayList<User> userDatas) {
        this.userBoxs = new ArrayList<UserBox>(userDatas.size());

        VBox content = new VBox(7.0);
        if(!userDatas.isEmpty()) {
            for(int i = 0; i < userDatas.size(); i++) {
                UserBox userBox = new UserBox(userDatas.get(i), this.assets);
                userBoxs.add(userBox);
                content.getChildren().add(userBox);
            }
        }

        content.setBackground(assets.background);
        this.scrollPane.setContent(content);
        super.updateContents();
    }

    public void addSearchListener(ChangeListener<String> listener) {
        this.search.textProperty().addListener(listener);
    }

    /**
     * @return current list of UserBox components in the tab
     */
    public ArrayList<UserBox> getUserBoxs() {
        return this.userBoxs;
    }
}
