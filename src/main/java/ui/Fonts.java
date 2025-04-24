package ui;
import javafx.scene.text.Font;
import java.io.FileInputStream;

/**
 * Font resource loader handling custom font initialization with fallback defaults.
 * Attempts to load Nerd Font variants and uses JavaFX system fonts as fallback.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Fonts {
    /** Regular weight font (14pt) from ArimoNerdFont-Regular.ttf or system default */
    public final Font normal;
    /** Bold weight font (14pt) from ArimoNerdFont-Bold.ttf or system default */
    public final Font bold;
    /** Medium weight font (20pt) from ArimoNerdFont-Bold.ttf or system default */
    public final Font medium;
    /** Large bold font (30pt) from ArimoNerdFont-Bold.ttf or system default */
    public final Font big;

    /**
     * Initializes all font variants. Attempts to load from font files in assets/fonts directory,
     * falls back to basic JavaFX fonts if loading fails. Creates four font variations:
     * - Normal (14pt regular)
     * - Bold (14pt bold)
     * - Medium (20pt bold)
     * - Big (30pt bold)
     */
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
