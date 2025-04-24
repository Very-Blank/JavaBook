package data;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Main database class managing books, users, and loans with sparse set storage.
 * Handles data persistence through JSON serialization and complex loan relationships.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class Database {
    private SparseSet<Book> books;
    private SparseSet<Loan> loans;
    private SparseSet<User> users;

    /** Initializes empty database with fresh sparse sets */
    public Database() {
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();
    }

    /**
     * Loads database state from JSON file, overwriting current data.
     * @param path file path to read from
     * @throws IOException if file operations fail
     * <pre name="test">
     *   Database db = new Database();
     *   JSONObject testData = new JSONObject();
     *   testData.put("books", new JSONArray());
     *   testData.put("users", new JSONArray());
     *   testData.put("loans", new JSONArray());
     *   Files.writeString(Path.of("test.json"), testData.toString());
     *   db.readDataFromFile("test.json");
     *   db.getBooks().size() === 0;
     * </pre>
     */
    public void readDataFromFile(String path) throws IOException {
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();

        String content = Files.readString(Path.of(path));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray books = jsonObject.getJSONArray("books");

        for (int i = 0; i < books.length(); i++) {
            JSONObject book = books.getJSONObject(i);
            Book newBook = new Book();
            newBook.fromJsonObject(book);
            this.addBook(newBook);
        }

        JSONArray users = jsonObject.getJSONArray("users");
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            User newUser = new User();
            newUser.fromJsonObject(user);
            this.addUser(newUser);
        }

        JSONArray loans = jsonObject.getJSONArray("loans");
        for (int i = 0; i < loans.length(); i++) {
            JSONObject loan = loans.getJSONObject(i);
            Loan newLoan = new Loan();
            newLoan.fromJsonObject(loan);
            this.updateLoan(newLoan);
        }
    }

    /**
     * Writes current database state to JSON file.
     * @param path file path to write to
     * @throws IOException if file operations fail
     */
    public void writeDataToFile(String path) throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonBooks = new JSONArray();
        ArrayList<Book> books = this.books.getDense();
        for (int i = 0; i < books.size(); i++) {
            jsonBooks.put(books.get(i).toJsonObject());
        }
        jsonObject.put("books", jsonBooks);

        JSONArray jsonUsers = new JSONArray();
        ArrayList<User> users = this.users.getDense();
        for (int i = 0; i < users.size(); i++) {
            jsonUsers.put(users.get(i).toJsonObject());
        }
        jsonObject.put("users", jsonUsers);

        JSONArray jsonLoans = new JSONArray();
        ArrayList<Loan> loans = this.loans.getDense();
        for (int i = 0; i < loans.size(); i++) {
            jsonLoans.put(loans.get(i).toJsonObject());
        }
        jsonObject.put("loans", jsonLoans);

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toString(4));
        file.flush();
        file.close();
    }

    /** Clears all data from the database */
    public void dumb() {
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();
    }

    /**
     * Adds book to database, generating ID if needed.
     * @param book book to add
     * @return assigned ID
     */
    public int addBook(Book book) {
        return this.books.setValueAt(book);
    }

    /**
     * Removes book and associated loan from database.
     * @param ID book ID to remove
     * @throws DataException if book doesn't exist
     * <pre name="test">
     *   Database db = new Database();
     *   int id = db.addBook(new Book());
     *   db.deleteBook(id);
     *   boolean thrown = false;
     *   try { db.getBook(id); } 
     *   catch (DataException e) { thrown = true; }
     *   thrown === true;
     * </pre>
     */
    public void deleteBook(int ID) throws DataException {
        Book book = this.getBook(ID);
        if (0 <= book.getLoan()) {
            this.deleteLoan(book.getLoan());
        }
        this.books.removeValueAt(ID);
    }

    /**
     * @return copied list of all books in database
     * <pre name="test">
     *   Database db = new Database();
     *   db.addBook(new Book());
     *   ArrayList<Book> books = db.getBooks();
     *   books.get(0).setLoan(5);
     *   db.getBooks().get(0).getLoan() === -1;
     * </pre>
     */
    public ArrayList<Book> getBooks() {
        ArrayList<Book> dense = this.books.getDense();
        ArrayList<Book> books = new ArrayList<Book>(dense.size());
        for (int i = 0; i < dense.size(); i++) {
            books.add(dense.get(i).copy());
        }
        return books;
    }

    /**
     * Retrieves book copy by ID.
     * @param ID book identifier
     * @return copied book instance
     * @throws DataException if book doesn't exist
     */
    public Book getBook(int ID) throws DataException {
        return books.get(ID).copy();
    }

    /**
     * @return list of books not currently loaned
     * @throws DataException if data access fails
     * <pre name="test">
     *   Database db = new Database();
     *   int id1 = db.addBook(new Book());
     *   int id2 = db.addBook(new Book());
     *   Loan loan = new Loan(-1, id1, 0, LocalDate.now());
     *   int loanId = db.updateLoan(loan);
     *   db.getBook(id1).setLoan(loanId);
     *   db.getAvailableBooks().size() === 1;
     * </pre>
     */
    public ArrayList<Book> getAvailableBooks() throws DataException {
        ArrayList<Book> books = getBooks();
        int i = 0;
        while (i < books.size()) {
            if (0 <= books.get(i).getLoan()) {
                books.remove(i);
            } else {
                i++;
            }
        }
        return books;
    }

    /**
     * Updates existing book entry.
     * @param book updated book data
     * @return book ID
     */
    public int updateBook(Book book) {
        return books.setValueAt(book);
    }

    /**
     * @param book book to check
     * @return loaner's name or null if not loaned
     * <pre name="test">
     *   Database db = new Database();
     *   int userId = db.addUser(new User(-1, "Alice", "a@b", "+358", "040", new ArrayList<>(), LocalDate.now()));
     *   int bookId = db.addBook(new Book());
     *   Loan loan = new Loan(-1, bookId, userId, LocalDate.now());
     *   int loanId = db.updateLoan(loan);
     *   db.getBook(bookId).setLoan(loanId);
     *   db.getLoanerName(db.getBook(bookId)) === "Alice";
     * </pre>
     */
    public String getLoanerName(Book book) {
        try {
            Loan loan = this.getLoan(book.getLoan());
            return this.getUser(loan.getUserID()).getName();
        } catch (DataException _) {
            return null;
        }
    }

    /**
     * Adds user to database, generating ID if needed.
     * @param user user to add
     * @return assigned ID
     */
    public int addUser(User user) {
        return this.users.setValueAt(user);
    }

    /**
     * Removes user and all associated loans.
     * @param ID user ID to remove
     * @throws DataException if user doesn't exist
     * <pre name="test">
     *   Database db = new Database();
     *   int userId = db.addUser(new User());
     *   int bookId = db.addBook(new Book());
     *   Loan loan = new Loan(-1, bookId, userId, LocalDate.now());
     *   int loanId = db.updateLoan(loan);
     *   db.getUser(userId).getLoans().add(loanId);
     *   db.deleteUser(userId);
     *   db.getUsers().size() === 0;
     *   db.getLoans().getDense().size() === 0;
     * </pre>
     */
    public void deleteUser(int ID) throws DataException {
        User user = this.users.get(ID);
        ArrayList<Integer> loans = user.getLoans();
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = this.loans.get(loans.get(i));
            Book book = this.books.get(loan.getBookID());
            book.setLoan(-1);
            this.loans.removeValueAt(loans.get(i));
        }
        this.users.removeValueAt(ID);
    }

    /**
     * Updates user's loan relationships with validation.
     * @param ID user ID to update
     * @param loanedBooks books to associate with user
     * @throws DataException on invalid loan relationships
     * <pre name="test">
     *   Database db = new Database();
     *   int userId = db.addUser(new User());
     *   int bookId = db.addBook(new Book());
     *   ArrayList<Book> loans = new ArrayList<>();
     *   loans.add(db.getBook(bookId));
     *   db.updateLoansForUser(userId, loans);
     *   db.getUser(userId).getLoans().size() === 1;
     * </pre>
     */
    public void updateLoansForUser(int ID, ArrayList<Book> loanedBooks) throws DataException {
        ArrayList<Integer> newLoans = new ArrayList<Integer>(loanedBooks.size());
        HashSet<Integer> seenLoans = new HashSet<Integer>();
        User user = this.users.get(ID);

        for (int i = 0; i < loanedBooks.size(); i++) {
            int loanID = loanedBooks.get(i).getLoan();
            Book loanedBook = this.books.get(loanedBooks.get(i).getID());
            
            if (0 <= loanID) {
                Loan loan = this.loans.get(loanID);
                if(loan.getUserID() != user.getID() || loan.getBookID() != loanedBook.getID()){
                    throw new DataException("Incorrect ID's for loan");
                }
                seenLoans.add(loan.getID());
                newLoans.add(loan.getID());
            } else {
                if(0 <= loanedBook.getLoan()){
                    throw new DataException("Book was loaned by somebody else");
                }
                Loan newLoan = new Loan(-1, loanedBook.getID(), user.getID(), LocalDate.now());
                int newLoanID = this.updateLoan(newLoan);
                loanedBook.setLoan(newLoanID);
                newLoans.add(newLoanID);
            }
        }

        ArrayList<Integer> oldLoans = user.getLoans();
        for (int i = 0; i < oldLoans.size(); i++) {
            if (!seenLoans.contains(oldLoans.get(i))) {
                Loan loan = this.loans.get(oldLoans.get(i));
                Book book = this.books.get(loan.getBookID());
                book.setLoan(-1);
                this.loans.removeValueAt(oldLoans.get(i));
            }
        }

        user.setLoans(newLoans);
    }

    /**
     * @param userCopy user copy containing ID
     * @return list of books loaned by user
     * @throws DataException if user doesn't exist
     */
    public ArrayList<Book> getUserLoanedBooks(User userCopy) throws DataException {
        User user = this.users.get(userCopy.getID());
        ArrayList<Integer> loans = user.getLoans();
        ArrayList<Book> books = new ArrayList<Book>(loans.size());
        for (int i = 0; i < loans.size(); i++) {
            books.add(this.books.get(this.loans.get(loans.get(i)).getBookID()).copy());
        }
        return books;
    }

    /**
     * @return copied list of all users
     * <pre name="test">
     *   Database db = new Database();
     *   db.addUser(new User());
     *   db.getUsers().get(0).setName("Alice");
     *   db.getUsers().get(0).getName() === "Alice";
     *   db.getUsers().get(0).getName() === "";
     * </pre>
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> dense = this.users.getDense();
        ArrayList<User> users = new ArrayList<User>(dense.size());
        for (int i = 0; i < dense.size(); i++) {
            users.add(dense.get(i).copy());
        }
        return users;
    }

    /**
     * Retrieves user copy by ID.
     * @param ID user identifier
     * @return copied user instance
     * @throws DataException if user doesn't exist
     */
    public User getUser(int ID) throws DataException {
        return this.users.get(ID).copy();
    }

    /**
     * Updates existing user entry.
     * @param user updated user data
     * @return user ID
     */
    public int updateUser(User user) {
        return this.users.setValueAt(user);
    }

    /**
     * Adds or updates loan in database.
     * @param loan loan data
     * @return assigned loan ID
     */
    public int updateLoan(Loan loan) {
        return this.loans.setValueAt(loan);
    }

    /**
     * Retrieves loan copy by ID.
     * @param ID loan identifier
     * @return copied loan instance
     * @throws DataException if loan doesn't exist
     */
    public Loan getLoan(int ID) throws DataException {
        return this.loans.get(ID).copy();
    }

    /**
     * Removes loan and updates associated book/user.
     * @param ID loan ID to remove
     * @throws DataException if loan doesn't exist
     * <pre name="test">
     *   Database db = new Database();
     *   int loanId = db.updateLoan(new Loan());
     *   db.deleteLoan(loanId);
     *   db.getLoans().getDense().size() === 0;
     * </pre>
     */
    public void deleteLoan(int ID) throws DataException {
        Loan loan = this.loans.get(ID);
        Book book = this.books.get(loan.getBookID());
        User user = this.users.get(loan.getUserID());

        ArrayList<Integer> loans = user.getLoans();
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i) == ID) {
                loans.remove(i);
                break;
            }
        }

        book.setLoan(-1);
        this.loans.removeValueAt(ID);
    }
}
