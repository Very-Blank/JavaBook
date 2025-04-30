package data;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Provides utility methods for sorting and searching Book collections.
 * Supports case-insensitive title sorting and partial title matching.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class BookUtils {
    /**
     * Case-insensitive comparator for book titles.
     * <pre name="test">
     *   Book book1 = new Book(1, -1, "Apple", "", "", "", null);
     *   Book book2 = new Book(2, -1, "banana", "", "", "", null);
     *   BookUtils.TITLE_COMPARATOR.compare(book1, book2) < 0;
     * </pre>
     */
    public static final Comparator<Book> TITLE_COMPARATOR = 
        Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);

    /**
     * Sorts books by title in-place using case-insensitive comparison.
     * @param books ArrayList to sort (modified directly)
     * <pre name="test">
     *   ArrayList<Book> books = new ArrayList<>();
     *   books.add(new Book(1, -1, "Zebra", "", "", "", null));
     *   books.add(new Book(2, -1, "Apple", "", "", "", null));
     *   BookUtils.sortBooksByTitle(books);
     *   books.get(0).getTitle() === "Apple";
     * </pre>
     */
    public static void sortBooksByTitle(ArrayList<Book> books) {
        Collections.sort(books, TITLE_COMPARATOR);
    }

    /**
     * Finds books containing search query in title (case-insensitive partial match).
     * @param books ArrayList to search through
     * @param searchQuery partial title to match (ignores leading/trailing whitespace)
     * @return new ArrayList of matching books (empty if no matches)
     * <pre name="test">
     *   ArrayList<Book> books = new ArrayList<>();
     *   books.add(new Book(1, -1, "Harry Potter", "", "", "", null));
     *   books.add(new Book(2, -1, "The Hobbit", "", "", "", null));
     *   BookUtils.findBooksByTitle(books, "harry").size() === 1;
     *   BookUtils.findBooksByTitle(books, "").isEmpty() === true;
     * </pre>
     */
    public static ArrayList<Book> findBooksByTitle(ArrayList<Book> books, String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String trimmedQuery = searchQuery.trim().toLowerCase();

        return books.stream()
            .filter(book -> book.getTitle() != null 
                    && book.getTitle().toLowerCase().contains(trimmedQuery))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
