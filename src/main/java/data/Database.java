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

public class Database {
    private SparseSet<Book> books;
    private SparseSet<Loan> loans;
    private SparseSet<User> users;

    public Database() {
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();
    }

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

    public void writeDataToFile(String path) throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonBooks = new JSONArray();
        ArrayList<Book> books = this.books.getDense();
        for (int i = 0; i < books.size(); i++) {
            JSONObject book = books.get(i).toJsonObject();
            jsonBooks.put(book);
        }

        jsonObject.put("books", jsonBooks);

        JSONArray jsonUsers = new JSONArray();
        ArrayList<User> users = this.users.getDense();
        for (int i = 0; i < users.size(); i++) {
            JSONObject user = users.get(i).toJsonObject();
            jsonUsers.put(user);
        }

        jsonObject.put("users", jsonUsers);

        JSONArray jsonLoans = new JSONArray();
        ArrayList<Loan> loans = this.loans.getDense();
        for (int i = 0; i < loans.size(); i++) {
            JSONObject loan = loans.get(i).toJsonObject();
            jsonLoans.put(loan);
        }

        jsonObject.put("loans", jsonLoans);

        // Write to file
        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toString(4));
        file.flush();
        file.close();
    }

    public void dumb(){
        this.books = new SparseSet<Book>();
        this.loans = new SparseSet<Loan>();
        this.users = new SparseSet<User>();
    }

    public int addBook(Book book) {
        return this.books.setValueAt(book);
    }

    public void deleteBook(int ID) throws DataException {
        Book book = this.getBook(ID);
        if (0 <= book.getLoan()) {
            this.deleteLoan(book.getLoan());
        }

        this.books.removeValueAt(ID);
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
            if (0 <= books.get(i).getLoan()) {
                books.remove(i);
            } else {
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
    public String getLoanerName(Book book) {
        try {
            Loan loan = this.getLoan(book.getLoan());
            return this.getUser(loan.getUserID()).getName();
        } catch (DataException _) {
            return null;
        }
    }

    public int addUser(User user) {
        return this.users.setValueAt(user);
    }

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

    public void updateLoansForUser(int ID, ArrayList<Book> loanedBooks) throws DataException{
        ArrayList<Integer> newLoans = new ArrayList<Integer>(loanedBooks.size());
        HashSet<Integer> seenLoans = new HashSet<Integer>();
        User user = this.users.get(ID);

        for (int i = 0; i < loanedBooks.size(); i++) {
            int loanID = loanedBooks.get(i).getLoan();
            Book loanedBook = this.books.get(loanedBooks.get(i).getID());
            //Old loan
            if (0 <= loanID) {
                Loan loan = this.loans.get(loanID);
                if(loan.getUserID() != user.getID() || loan.getBookID() != loanedBook.getID()){
                    throw new DataException("Incorrect ID's for loan");
                }

                seenLoans.add(loan.getID());
                newLoans.add(loan.getID());
            } else{ //New loan
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

    // User might be a copy with wrong data!
    // So we get the orginal!
    public ArrayList<Book> getUserLoanedBooks(User userCopy) throws DataException {
        User user = this.users.get(userCopy.getID());
        ArrayList<Integer> loans = user.getLoans();

        ArrayList<Book> books = new ArrayList<Book>(loans.size());
        for (int i = 0; i < loans.size(); i++) {
            books.add(this.books.get(this.loans.get(loans.get(i)).getBookID()).copy());
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

    public int updateUser(User user) {
        return this.users.setValueAt(user);
    }

    public int updateLoan(Loan loan) {
        return this.loans.setValueAt(loan);
    }

    public Loan getLoan(int ID) throws DataException {
        return this.loans.get(ID).copy();
    }

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
