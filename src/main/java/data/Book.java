package data;

import java.time.LocalDate;
import org.json.JSONObject;

/**
 * Represents a book entity with metadata and loan management capabilities.
 * Handles JSON serialization and provides copy functionality.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Book extends Data<Book> {
    private String title;
    private String author;
    private String summary;
    private String imagePath;
    private LocalDate publication;
    private int loan;

    /**
     * Creates default invalid book with empty fields and dummy image.
     * <pre name="test">
     *   Book book = new Book();
     *   book.getID() === -1;
     *   book.getLoan() === -1;
     *   book.getImagePath() === "src/main/resources/bookCovers/dummy_150x150_000000_ffb2fc.png";
     * </pre>
     */
    public Book() {
        super(-1);
        this.loan = -1;
        this.title = "";
        this.author = "";
        this.summary = "";
        this.imagePath = "src/main/resources/bookCovers/dummy_150x150_000000_ffb2fc.png";
        this.publication = LocalDate.now();
    }

    /**
     * Constructs book with specified parameters.
     * @param ID unique identifier
     * @param loan associated loan ID (-1 if available)
     * @param title book title
     * @param author book author
     * @param summary description text
     * @param imagePath cover image path
     * @param publication release date
     * <pre name="test">
     *   LocalDate date = LocalDate.of(2020,5,5);
     *   Book book = new Book(5, -1, "Title", "Author", "Summary", "path.png", date);
     *   book.getTitle() === "Title";
     *   book.getPublication() === date;
     * </pre>
     */
    public Book(int ID, int loan, String title, String author, String summary, String imagePath,
            LocalDate publication) {
        super(ID);
        this.loan = loan;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.imagePath = imagePath;
        this.publication = publication;
    }

    /**
     * Creates deep copy of the book.
     * @return new independent Book instance
     * <pre name="test">
     *   Book original = new Book(1, -1, "Original", "Auth", "Sum", "img", LocalDate.now());
     *   Book copy = original.copy();
     *   copy.setLoan(5);
     *   original.getLoan() === -1;
     * </pre>
     */
    public Book copy() {
        return new Book(getID(), this.loan, this.title, this.author, this.summary, this.imagePath, this.publication);
    }

    /**
     * Populates book data from JSON object.
     * @param json source JSON data
     * <pre name="test">
     *   JSONObject json = new JSONObject();
     *   json.put("id", 5);
     *   json.put("loan", 10);
     *   json.put("title", "JSON Book");
     *   json.put("author", "JSON Author");
     *   json.put("summary", "Test summary");
     *   json.put("imagePath", "test.png");
     *   json.put("publication", "2020-05-05");
     *   Book book = new Book();
     *   book.fromJsonObject(json);
     *   book.getID() === 5;
     *   book.getTitle() === "JSON Book";
     * </pre>
     */
    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.loan = json.getInt("loan");
        this.title = json.getString("title");
        this.author = json.getString("author");
        this.summary = json.getString("summary");
        this.imagePath = json.getString("imagePath");
        this.publication = Data.stringToDate(json.getString("publication"));
    }

    /**
     * Serializes book to JSON format.
     * @return JSON representation
     * <pre name="test">
     *   Book book = new Book(7, -1, "Test", "Auth", "Sum", "img", LocalDate.of(2015,10,10));
     *   JSONObject json = book.toJsonObject();
     *   json.getInt("id") === 7;
     *   json.getString("publication") === "10 October 2015";
     * </pre>
     */
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("id", this.getID());
        object.put("loan", this.loan);
        object.put("title", this.title);
        object.put("author", this.author);
        object.put("summary", this.summary);
        object.put("imagePath", this.imagePath);
        object.put("publication", Data.dateToString(this.publication));

        return object;
    }

    /**
     * @return loan status as human-readable string
     * <pre name="test">
     *   Book book = new Book();
     *   book.loanToString() === "Available";
     *   book.setLoan(5);
     *   book.loanToString() === "Loaned";
     * </pre>
     */
    public String loanToString() {
        return (0 <= this.loan) ? "Loaned" : "Available";
    }

    /**
     * Updates book metadata fields.
     * @param title new title
     * @param author new author
     * @param summary new description
     * @param imagePath new cover image path
     * @param publication new release date
     * <pre name="test">
     *   Book book = new Book();
     *   LocalDate date = LocalDate.now();
     *   book.update("New", "Auth", "Sum", "img.png", date);
     *   book.getTitle() === "New";
     *   book.getPublication() === date;
     * </pre>
     */
    public void update(String title, String author, String summary, String imagePath, LocalDate publication) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.imagePath = imagePath;
        this.publication = publication;
    }

    /** @return associated loan ID (-1 if available) */
    public int getLoan() {
        return this.loan;
    }

    /** @param ID new loan ID (-1 to mark available) */
    public void setLoan(int ID) {
        this.loan = ID;
    }

    /** @return book title */
    public String getTitle() {
        return this.title;
    }

    /** @return author name */
    public String getAuthor() {
        return this.author;
    }

    /** @return description text */
    public String getSummary() {
        return this.summary;
    }

    /** @return cover image path */
    public String getImagePath() {
        return this.imagePath;
    }

    /** @return publication date */
    public LocalDate getPublication() {
        return this.publication;
    }
}
