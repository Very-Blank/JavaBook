package ui.user;

import javafx.scene.layout.*;
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

    /**
     * Creates user tab with initial dataset
     * @param assets styling resources and color definitions
     * @param userDatas initial collection of users to display
     */
    public ScrollableUserTab(Assets assets, ArrayList<User> userDatas) {
        super(assets, "Users ");
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

    /**
     * @return current list of UserBox components in the tab
     */
    public ArrayList<UserBox> getUserBoxs() {
        return this.userBoxs;
    }
}
