package data;
import java.time.LocalDate;
import org.json.JSONObject;

/**
 * Represents a book loan record linking users, books, and due dates.
 * Handles JSON serialization/deserialization for persistence.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Loan extends Data<Loan> {
    private int bookID;
    private int userID;
    private LocalDate dueDate;

    /**
     * Creates a default invalid loan with ID -1 and unset fields.
     * <pre name="test">
     *   Loan loan = new Loan();
     *   loan.getID() === -1;
     *   loan.getBookID() === -1;
     *   loan.getUserID() === -1;
     * </pre>
     */
    public Loan() {
        super(-1);
        this.bookID = -1;
        this.userID = -1;
    }

    /**
     * Constructs a loan with specified details.
     * @param ID unique identifier
     * @param book associated book ID
     * @param user associated user ID
     * @param date due date for return
     * <pre name="test">
     *   LocalDate date = LocalDate.now();
     *   Loan loan = new Loan(1, 5, 10, date);
     *   loan.getID() === 1;
     *   loan.toJsonObject().getInt("bookID") === 5;
     *   loan.toJsonObject().getString("dueDate") === Data.dateToString(date);
     * </pre>
     */
    public Loan(int ID, int book, int user, LocalDate date) {
        super(ID);
        this.bookID = book;
        this.userID = user;
        this.dueDate = date;
    }

    /**
     * Populates loan data from JSON object.
     * @param json source JSON data
     * <pre name="test">
     *   JSONObject json = new JSONObject();
     *   json.put("id", 2);
     *   json.put("bookID", 15);
     *   json.put("userID", 30);
     *   json.put("dueDate", "2023-10-10");
     *   Loan loan = new Loan();
     *   loan.fromJsonObject(json);
     *   loan.getID() === 2;
     *   loan.toJsonObject().getInt("userID") === 30;
     * </pre>
     */
    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.bookID = json.getInt("bookID");
        this.userID = json.getInt("userID");
        this.dueDate = Data.stringToDate(json.getString("dueDate"));
    }

    /**
     * Serializes loan to JSON format.
     * @return JSON representation of loan
     * <pre name="test">
     *   LocalDate date = LocalDate.of(2023, 12, 31);
     *   Loan loan = new Loan(3, 20, 40, date);
     *   JSONObject json = loan.toJsonObject();
     *   json.getInt("id") === 3;
     *   json.getString("dueDate") === "2023-12-31";
     * </pre>
     */
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("id", this.getID());
        object.put("userID", this.userID);
        object.put("bookID", this.bookID);
        object.put("dueDate", Data.dateToString(this.dueDate));

        return object;
    }

    /** @return associated book ID */
    public int getBookID() {
        return this.bookID;
    }

    /** @return associated user ID */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Creates an identical copy of this loan.
     * @return new Loan instance with same data
     * <pre name="test">
     *   Loan original = new Loan(4, 6, 8, LocalDate.now());
     *   Loan copy = original.copy();
     *   copy.getID() === original.getID();
     *   copy.toJsonObject().similar(original.toJsonObject()) === true;
     * </pre>
     */
    public Loan copy() {
        return new Loan(this.getID(), bookID, userID, dueDate);
    }
}
