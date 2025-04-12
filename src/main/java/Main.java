
import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.event.EventHandler;

import java.io.FileInputStream;
import java.time.LocalDate;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Set;

import ui.user.*;
import ui.book.*;
import ui.*;
import data.*;

public class Main extends Application {
    private TopControlls topControlls;
    private TabPane tabBar;
    private ScrollableBookTab bookTab;
    private ScrollableUserTab userTab;
    private Database database;
    private VBox root;
    private Assets assets;

    @Override
    public void start(Stage primaryStage) {
        this.database = new Database();
        this.assets = new Assets();
        this.topControlls = new TopControlls(this.assets);

        this.topControlls.quitItem.setOnAction((event) -> {
            primaryStage.close();
        });

        this.tabBar = new TabPane();
        this.tabBar.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.tabBar.setBackground(assets.background);

        Platform.runLater(() -> {
            tabBar.lookup(".tab-header-background").setStyle(String.format("-fx-background-color: %s;", assets.gray1));

            Set<Node> tabs = tabBar.lookupAll(".tab");

            for (Node tab : tabs) {
                tab.setStyle(String.format("-fx-background-color: %s;", assets.gray2));
            }
        });

        this.bookTab = new ScrollableBookTab(this.assets, database.getBooks());

        this.bookTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<BookBox> bookBoxs = bookTab.getBookBoxs();
                for (int i = 0; i < bookBoxs.size(); i++) {
                    BookBox bookbox = bookBoxs.get(i);
                    bookbox.setOnMouseClicked((event) -> {
                        try {
                            Book book = bookbox.getBook();
                            BookDetailsWindow newWindow = new BookDetailsWindow(book, assets);

                            newWindow.setBackAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    newWindow.close();
                                }
                            });

                            newWindow.setDeleteAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    try {
                                        database.deleteBook(newWindow.getBook());
                                    } catch(Exception e){
                                        System.out.println("failed to delete book");
                                        System.out.println(e.getMessage());
                                    }

                                    bookTab.updateContents(database.getBooks());
                                    newWindow.close();
                                }
                            });

                            newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    database.updateBook(newWindow.getBook());
                                    newWindow.close();

                                    bookTab.updateContents(database.getBooks());
                                }
                            });

                            newWindow.showAndWait();
                        } catch (Exception e) {
                            System.out.println("failed to open window");
                        }
                    });
                }
            }
        });

        this.bookTab.setAddAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    Book book = new Book();

                    BookDetailsWindow newWindow = new BookDetailsWindow(book, assets);

                    newWindow.setBackAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            newWindow.close();
                        }
                    });

                    newWindow.setDeleteAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            newWindow.close();
                        }
                    });

                    newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            database.addBook(newWindow.getBook());
                            newWindow.close();

                            bookTab.updateContents(database.getBooks());
                        }
                    });

                    newWindow.showAndWait();

                } catch (Exception e) {
                    System.out.println("failed to open window");
                }
            }
        });

        this.userTab = new ScrollableUserTab(this.assets, database.getUsers());

        this.userTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<UserBox> userBoxs = userTab.getUserBoxs();
                for (int i = 0; i < userBoxs.size(); i++) {
                    UserBox userBox = userBoxs.get(i);
                    userBox.setOnMouseClicked((_) -> {
                        try {
                            User user = userBox.getUser();
                            UserDetailsWindow newWindow = new UserDetailsWindow(user, assets);

                            newWindow.setBackAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    newWindow.close();
                                }
                            });

                            newWindow.setDeleteAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    try {
                                        database.deleteUser(newWindow.getUser());
                                    } catch(Exception e){
                                        System.out.println("failed to delete user");
                                        System.out.println(e.getMessage());
                                    }

                                    userTab.updateContents(database.getUsers());
                                    newWindow.close();
                                }
                            });

                            newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    database.updateUser(newWindow.getUser());

                                    userTab.updateContents(database.getUsers());
                                    newWindow.close();
                                }
                            });

                            newWindow.showAndWait();
                        } catch (Exception e) {
                            System.out.println("failed to open window");
                            System.out.println(e.getMessage());
                        }
                    });
                }
            }
        });


        // this.userTab.setAddAction(new EventHandler<ActionEvent>() {
        //     public void handle(ActionEvent t) {
        //         try {
        //             Stage newWindow = new UserDetailsWindow(assets);
        //             newWindow.showAndWait();
        //         } catch (Exception e) {
        //             System.out.println("failed to open window");
        //         }
        //     }
        // });

        this.tabBar.getTabs().addAll(this.bookTab, this.userTab);

        this.tabBar.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            if (newTab == bookTab) {
                bookTab.updateContents(database.getBooks());
            } else if (newTab == userTab) {
                userTab.updateContents(database.getUsers());
            }
        });

        this.root = new VBox();
        this.root.setBackground(assets.background);
        this.root.getChildren().addAll(this.topControlls, this.tabBar);

        Scene scene = new Scene(this.root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
