package ui;
import javafx.scene.text.Font;
import java.io.FileInputStream;

public class Fonts {
    public final Font normal;
    public final Font bold;
    public final Font medium;
    public final Font big;

    public Fonts(){
        Font normalFont;
        try{
            FileInputStream fontStream = new FileInputStream("assets/fonts/ArimoNerdFont-Regular.ttf");
            normalFont = Font.loadFont(fontStream, 14);
        } catch (Exception e) {
            normalFont = new Font(14);
        }

        this.normal = normalFont;

        Font boldFont;
        try{
            FileInputStream fontStreamBold = new FileInputStream("assets/fonts/ArimoNerdFont-Bold.ttf");
            boldFont = Font.loadFont(fontStreamBold, 14);

        } catch (Exception e) {
            boldFont = new Font(14);
        }
        this.bold = boldFont;

        Font mediumFont;
        try{
            FileInputStream fontStreamBold = new FileInputStream("assets/fonts/ArimoNerdFont-Bold.ttf");
            mediumFont = Font.loadFont(fontStreamBold, 20);

        } catch (Exception e) {
            mediumFont = new Font(20);
        }

        this.medium = mediumFont;

        Font bigFont;
        try{
            FileInputStream fontStreamBold = new FileInputStream("assets/fonts/ArimoNerdFont-Bold.ttf");
            bigFont = Font.loadFont(fontStreamBold, 30);
        } catch (Exception e) {
            bigFont = new Font(30);
        }
        this.big = bigFont;

    }
}
