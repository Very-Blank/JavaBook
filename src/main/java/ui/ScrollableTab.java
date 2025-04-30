package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.event.*;
import java.util.HashMap;

/**
 * Abstract base class for scrollable tab components with dynamic content and action buttons.
 * Provides common functionality for content updates and button management.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public abstract class ScrollableTab extends Tab {
    protected ScrollPane scrollPane;
    protected Assets assets;
    protected EventHandler<ActionEvent> updateNotifier;
    protected HBox customHolder;

    private HBox buttonHolder;
    private HashMap<String, Button> buttonMap;

    /**
     * Constructs scrollable tab with basic layout and styling.
     * @param assets styling resources and color definitions
     * @param name display name for the tab header
     */
    public ScrollableTab(Assets assets, String name) {
        super();
        this.assets = assets;
        this.scrollPane = new ScrollPane();
        this.customHolder = new HBox();
        this.buttonHolder = new HBox();
        this.buttonMap = new HashMap<String, Button>();

        this.customHolder.setAlignment(Pos.CENTER_LEFT);

        super.setGraphic(newLabel(name));
        setScrollPaneSyling(this.scrollPane, this.assets);

        HBox holder = new HBox(this.customHolder, this.buttonHolder);
        holder.setBackground(this.assets.surface);
        holder.setMinHeight(30);

        VBox vbox = new VBox(5);
        vbox.setBackground(this.assets.background);
        vbox.getChildren().addAll(holder, this.scrollPane);
        super.setContent(vbox);
    }

    /**
     * Creates styled label for tab header
     * @param name text content for label
     * @return configured Label component
     */
    private Label newLabel(String name) {
        Label label = new Label(name);
        label.setFont(this.assets.fonts.normal);
        label.setMinWidth(150);
        label.setTextFill(this.assets.textColor);
        return label;
    }

    /**
     * Applies consistent styling to scroll pane component
     * @param scrollPane pane to style
     * @param assets styling resources
     */
    private void setScrollPaneSyling(ScrollPane scrollPane, Assets assets) {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        String css = String.format("""
            .scroll-pane > .viewport {
                -fx-background-color: %s;
            }
        """, assets.gray1);
        scrollPane.getStylesheets().add("data:text/css," + css);
    }

    /**
     * Adds action button to the tab's control bar
     * @param label displayed text with icon
     * @param name unique identifier for button retrieval
     */
    public void addButton(String label, String name) {
        Label addLabel = new Label(label);
        addLabel.setFont(this.assets.fonts.normal);
        addLabel.setTextFill(this.assets.textColor);

        Button newButton = new Button();
        newButton.setGraphic(addLabel);
        HBox.setMargin(newButton, new Insets(5, 0, 5, 5));
        newButton.setMinHeight(28);
        newButton.setBackground(this.assets.elevated);
        this.buttonHolder.getChildren().add(newButton);
        this.buttonMap.put(name, newButton);
    }

    /**
     * Retrieves button by its identifier
     * @param name button identifier
     * @return Button component or null if not found
     */
    public Button getButton(String name) {
        return this.buttonMap.get(name);
    }

    /**
     * Assigns action handler to specified button
     * @param action event handler to assign
     * @param name button identifier
     */
    public void setButtonAction(EventHandler<ActionEvent> action, String name) {
        this.buttonMap.get(name).setOnAction(action);
    }

    /**
     * Triggers content update notification
     */
    protected void updateContents() {
        this.fire();
    }

    /**
     * Sets callback for content update events
     * @param handler event handler for update notifications
     */
    public void setUpdateNotifier(EventHandler<ActionEvent> handler) {
        this.updateNotifier = handler;
        this.fire();
    }

    /**
     * Internal method to propagate update events
     */
    private void fire() {
        if (this.updateNotifier != null) {
            this.updateNotifier.handle(new ActionEvent());
        }
    }
}
