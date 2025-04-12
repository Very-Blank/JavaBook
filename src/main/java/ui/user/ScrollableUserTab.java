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

import javafx.beans.property.*;

import data.*;
import ui.*;


public class ScrollableUserTab extends ScrollableTab{
    public ScrollableUserTab(Assets assets, ArrayList<User> userDatas){
        super(assets, "Users ");
        updateContents(userDatas);
    }

    public void updateContents(ArrayList<User> userDatas){

        if(userDatas.size() != 0){
            VBox list = new VBox(7.0);
            list.prefWidthProperty().bind(this.scrollPane.widthProperty());

            try {
                for(int i = 0; i < userDatas.size(); i++){
                    String padding = "  ";
                    final double fontSize = 20.0;

                    UserBox userPane = new UserBox(userDatas.get(i), this.assets);

                    userPane.setOnMouseClicked((event) -> {
                        try {
                            Stage newWindow = new UserDetailsWindow(assets);
                            newWindow.showAndWait();
                        } catch (Exception e) {
                            System.out.println("failed to open window");
                        }
                    });

                    list.getChildren().add(userPane);
                }
            } catch (Exception e) {
                System.out.println("something wrong");
            }

            scrollPane.setContent(list);
        } else{

        }
    }
}


