
import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.*;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import ui.user.*;
import ui.book.*;
import ui.*;
import data.*;

/**
 * Main application class for library management system GUI.
 * Handles primary stage setup, database integration, and user interface components.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */

public class Main extends Application {
    private TopControlls topControlls;
    private TabPane tabBar;
    private ScrollableBookTab bookTab;
    private ScrollableUserTab userTab;
    private Database database;
    private VBox root;
    private Assets assets;
    private String searchString;

    /**
     * Main entry point for JavaFX application. Initializes database connection,
     * UI components, and event handlers for user interactions.
     * 
     * @param primaryStage primary window container for the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.database = new Database();

        try {
            String content = Files.readString(java.nio.file.Path.of("src/main/resources/settings.json"));
            JSONObject jsonObject = new JSONObject(content);
            this.database.readDataFromFile(jsonObject.getString("lastFile"));
        } catch (Exception e) {
            // No need to do anything, doesn't matter if this fails.
            System.err.println(e.getMessage());
        }

        this.assets = new Assets();
        this.topControlls = new TopControlls(this.assets);
        this.searchString = "";
        this.userTab = new ScrollableUserTab(this.assets, getUsers());
        this.bookTab = new ScrollableBookTab(this.assets, "Books ", database.getBooks());
        this.tabBar = new TabPane();

        this.topControlls.setOpenAction((_) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Json Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    database.readDataFromFile(selectedFile.getAbsolutePath());
                    bookTab.updateContents(database.getBooks());
                    userTab.updateContents(getUsers());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("lastFile", selectedFile.getAbsolutePath());
                    FileWriter settings = new FileWriter("src/main/resources/settings.json");
                    settings.write(jsonObject.toString());
                    settings.flush();
                    settings.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        this.topControlls.setNewAction((_) -> {
            database.dumb();
            bookTab.updateContents(database.getBooks());
            userTab.updateContents(getUsers());
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                try (FileWriter file = new FileWriter(selectedDirectory.getAbsolutePath() + "/data.json")) {
                    file.write("{}");
                    file.flush();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("lastFile", selectedDirectory.getAbsolutePath() + "/data.json");
                    FileWriter settings = new FileWriter("src/main/resources/settings.json");
                    settings.write(jsonObject.toString());
                    settings.flush();
                    settings.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }

            System.out.println();
        });

        this.topControlls.setSaveAction((_) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Json Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    database.writeDataToFile(selectedFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        this.topControlls.setQuitAction((_) -> {
            primaryStage.close();
        });

        this.tabBar.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.tabBar.setBackground(assets.background);

        Platform.runLater(() -> {
            tabBar.lookup(".tab-header-background").setStyle(String.format("-fx-background-color: %s;", assets.gray1));

            Set<Node> tabs = tabBar.lookupAll(".tab");

            for (Node tab : tabs) {
                tab.setStyle(String.format("-fx-background-color: %s;", assets.gray2));
            }
        });

        this.bookTab.addButton("Add ", "add");

        this.bookTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<BookBox> bookBoxs = bookTab.getBookBoxs();
                for (int i = 0; i < bookBoxs.size(); i++) {
                    BookBox bookbox = bookBoxs.get(i);
                    bookbox.setOnMouseClicked((_) -> {
                        try {
                            Book book = bookbox.getBook();
                            String loanedBy = database.getLoanerName(book);

                            BookDetailsWindow newWindow = new BookDetailsWindow(book, loanedBy, assets);
                            newWindow.addButton("Delete 󰆴", "delete");

                            newWindow.setBackAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    newWindow.close();
                                }
                            });

                            newWindow.setButtonAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    try {
                                        database.deleteBook(newWindow.getBook().getID());
                                    } catch (Exception e) {
                                        System.out.println("failed to delete book");
                                        System.out.println(e.getMessage());
                                    }

                                    bookTab.updateContents(database.getBooks());
                                    userTab.updateContents(getUsers());
                                    newWindow.close();
                                }
                            }, "delete");

                            newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    database.updateBook(newWindow.getBook());
                                    bookTab.updateContents(database.getBooks());
                                    newWindow.close();
                                }
                            });

                            newWindow.showAndWait();
                        } catch (Exception e) {
                            System.out.println("failed to open window");
                            System.err.println(e.getMessage());
                        }
                    });
                }
            }
        });

        this.bookTab.setButtonAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    Book book = new Book();

                    BookDetailsWindow newWindow = new BookDetailsWindow(book, null, assets);

                    newWindow.setBackAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            newWindow.close();
                        }
                    });

                    newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            database.addBook(newWindow.getBook());

                            bookTab.updateContents(database.getBooks());
                            tabBar.requestLayout();
                            newWindow.close();
                        }
                    });

                    newWindow.showAndWait();

                } catch (Exception e) {
                    System.out.println("failed to open window");
                    System.err.println(e.getMessage());
                }
            }
        }, "add");

        this.userTab.setUpdateNotifier(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ArrayList<UserBox> userBoxs = userTab.getUserBoxs();
                for (int i = 0; i < userBoxs.size(); i++) {
                    UserBox userBox = userBoxs.get(i);
                    userBox.setOnMouseClicked((_) -> {
                        try {
                            User user = userBox.getUser();
                            UserDetailsWindow newWindow = new UserDetailsWindow(user, database.getAvailableBooks(), database.getUserLoanedBooks(user), assets);
                            newWindow.addButton("Delete 󰆴", "delete");

                            newWindow.setBackAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    newWindow.close();
                                }
                            });

                            newWindow.setButtonAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    try {
                                        database.deleteUser(newWindow.getUser().getID());
                                    } catch (Exception e) {
                                        System.out.println("failed to delete user");
                                        System.err.println(e.getMessage());
                                    }
                                    userTab.updateContents(getUsers());
                                    newWindow.close();
                                }
                            }, "delete");

                            newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent s) {
                                    int userID = database.updateUser(newWindow.getUser());
                                    try {
                                        database.updateLoansForUser(userID, newWindow.getLoanedBooks());
                                    } catch (DataException e) {
                                        System.out.println("failed to update user loaned books");
                                        System.err.println(e.getMessage());
                                    }

                                    bookTab.updateContents(database.getBooks());
                                    userTab.updateContents(getUsers());
                                    newWindow.close();
                                }
                            });

                            newWindow.showAndWait();
                        } catch (Exception e) {
                            System.out.println("failed to open window");
                            System.err.println(e.getMessage());
                        }
                    });
                }
            }
        });


        this.userTab.addButton("Add ", "add");
        this.userTab.setButtonAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    User user = new User();

                    UserDetailsWindow newWindow = new UserDetailsWindow(user, database.getAvailableBooks(), new ArrayList<Book>(), assets);

                    newWindow.setBackAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            newWindow.close();
                        }
                    });

                    newWindow.setSaveAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent s) {
                            int userID = database.updateUser(newWindow.getUser());

                            try {
                                database.updateLoansForUser(userID, newWindow.getLoanedBooks());
                            } catch (DataException e) {
                                System.out.println("failed to update user loaned books");
                                System.out.println(e.getMessage());
                            }

                            bookTab.updateContents(database.getBooks());
                            userTab.updateContents(getUsers());
                            tabBar.requestLayout();
                            newWindow.close();
                        }
                    });

                    newWindow.showAndWait();

                } catch (Exception e) {
                    System.out.println("failed to open window");
                    System.err.println(e.getMessage());
                }
            }
        }, "add");

        this.userTab.addSearchListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchString = newValue;
                userTab.updateContents(getUsers());
                tabBar.requestLayout();
            }
        });


        this.tabBar.getTabs().addAll(this.bookTab, this.userTab);

        this.tabBar.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            if (newTab == bookTab) {
                bookTab.updateContents(database.getBooks());
            } else if (newTab == userTab) {
                userTab.updateContents(getUsers());
            }
        });

        this.root = new VBox();
        this.root.setBackground(assets.background);
        this.root.getChildren().addAll(this.topControlls, this.tabBar);

        Scene scene = new Scene(this.root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ArrayList<User> getUsers(){
        ArrayList<User> users =  UserUtils.findUsersByName(database.getUsers(), searchString);
        UserUtils.sortUsersByName(users);
        return users;
    }

    /**
     * Program entry point that delegates to JavaFX application launch.
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
