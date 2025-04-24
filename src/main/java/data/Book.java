package data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class Book extends Data<Book> {
    private String title;
    private String author;
    private String summary;
    private String imagePath;
    private LocalDate publication;

    private int loan;

    public Book() {
        super(-1);
        this.loan = -1;
        this.title = "";
        this.author = "";
        this.summary = "";
        this.imagePath = "src/main/resources/bookCovers/dummy_150x150_000000_ffb2fc.png";
        this.publication = LocalDate.now();
    }

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

    public Book copy() {
        return new Book(getID(), this.loan, this.title, this.author, this.summary, this.imagePath, this.publication);
    }

    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.loan = json.getInt("loan");
        this.title = json.getString("title");
        this.author = json.getString("author");
        this.summary = json.getString("summary");
        this.imagePath = json.getString("imagePath");
        this.publication = Data.stringToDate(json.getString("publication"));
    }

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

    public String loanToString() {
        if (0 <= this.loan) {
            return "Loaned";
        } else {
            return "Available";
        }
    }

    public void update(String title, String author, String summary, String imagePath, LocalDate publication) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.imagePath = imagePath;
        this.publication = publication;
    }

    public int getLoan() {
        return this.loan;
    }

    public void setLoan(int ID) {
        this.loan = ID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public LocalDate getPublication() {
        return this.publication;
    }
}
