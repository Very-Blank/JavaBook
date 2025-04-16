package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Database {
    private SparseSet<Book> books;
    private SparseSet<Loan> loans;
    private SparseSet<User> users;

    public Database() {
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();

        try {
            String content = Files.readString(Path.of("src/main/resources/data.json"));
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
                this.addLoan(newLoan);
            }

        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public int addBook(Book book) {
        return this.books.setValueAt(book);
    }

    public void deleteBook(Book book) throws DataException {
        // FIXME: DELETE LOANS
        this.books.removeValueAt(book);
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> dense = this.books.getDense();
        ArrayList<Book> books = new ArrayList<Book>(dense.size());
        for (int i = 0; i < dense.size(); i++) {
            books.add(dense.get(i).copy());
        }

        return books;
    }

    public Book getBook(int ID) throws DataException {
        return books.get(ID).copy();
    }

    public ArrayList<Book> getAvailableBooks() throws DataException {
        ArrayList<Book> books = getBooks();
        int i = 0;
        while (i < books.size()) {
            if (books.get(i).loan() >= 0) {
                books.remove(i);
            } else{
                i++;
            }
        }

        return books;
    }

    // FIXME: ADD ID CHECKING HERE AND VALIDATION
    public int updateBook(Book book) {
        return books.setValueAt(book);
    }

    // Returns null if loan is invalid
    public String getLoanerName(Book book){
        try {
            Loan loan = this.getLoan(book.loan());
            return this.getUser(loan.userID()).name();
        } catch (DataException _) {
            return null;
        }
    }

    public int addUser(User user) {
        return this.users.setValueAt(user);
    }

    public void deleteUser(User user) throws DataException {
        // FIXME: DELETE LOANS
        this.users.removeValueAt(user);
    }

    public void updateLoansFor(User user, ArrayList<Book> loanedBooks) throws DataException{

    }

    // User might be a copy with wrong data!
    // So we get the orginal!
    public ArrayList<Book> getUserLoanedBooks(User userCopy) throws DataException {
        User user = users.get(userCopy.getID());
        ArrayList<Integer> loans = user.loans();

        ArrayList<Book> books = new ArrayList<Book>(loans.size());
        for (int i = 0; i < loans.size(); i++) {
            books.add(this.books.get(this.loans.get(loans.get(i)).bookID()).copy());
        }

        return books;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> dense = this.users.getDense();
        ArrayList<User> users = new ArrayList<User>(dense.size());
        for (int i = 0; i < dense.size(); i++) {
            users.add(dense.get(i).copy());
        }

        return users;
    }

    public User getUser(int ID) throws DataException {
        return this.users.get(ID).copy();
    }

    // FIXME: ADD ID CHECKING HERE AND VALIDATION
    public int updateUser(User user) {
        return this.users.setValueAt(user);
    }

    public int addLoan(Loan loan) {
        return this.loans.setValueAt(loan);
    }

    public Loan getLoan(int ID) throws DataException {
        return this.loans.get(ID).copy();
    }

    public void deleteLoan(Loan loan) throws DataException {
        this.loans.removeValueAt(loan);
    }
}
