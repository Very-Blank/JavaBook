package ui.user;

import javafx.scene.layout.*;

import java.util.ArrayList;

import data.*;
import ui.*;


public class ScrollableUserTab extends ScrollableTab{
    private ArrayList<UserBox> userBoxs;

    public ScrollableUserTab(Assets assets, ArrayList<User> userDatas){
        super(assets, "Users ");
        updateContents(userDatas);
    }

    public void updateContents(ArrayList<User> userDatas){
        this.userBoxs = new ArrayList<UserBox>(userDatas.size());

        if(userDatas.size() != 0){
            VBox content = new VBox(7.0);

            for(int i = 0; i < userDatas.size(); i++){
                UserBox userBox = new UserBox(userDatas.get(i), this.assets);
                userBoxs.add(userBox);

                content.getChildren().add(userBox);
            }

            content.setBackground(assets.background);
            this.scrollPane.setContent(content);
        } else{
            VBox content = new VBox(7.0);

            content.setBackground(assets.background);
            this.scrollPane.setContent(content);
        }

        super.updateContents();
    }

    public ArrayList<UserBox> getUserBoxs(){
        return this.userBoxs;
    }
}


