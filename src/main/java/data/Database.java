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

        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public int addBook(Book book) {
        return this.books.setValueAt(book);
    }

    public void deleteBook(Book book) throws DataException {
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
        try {
            return books.get(ID).copy();
        } catch (DataException e) {
            throw e;
        }
    }

    // FIXME: ADD ID CHECKING HERE AND VALIDATION
    public int updateBook(Book book) {
        return books.setValueAt(book);
    }

    public int addUser(User user) {
        return this.users.setValueAt(user);
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
        try {
            return users.get(ID).copy();
        } catch (DataException e) {
            throw e;
        }
    }

    // FIXME: ADD ID CHECKING HERE AND VALIDATION
    public int updateUser(User user) {
        return users.setValueAt(user);
    }
}
