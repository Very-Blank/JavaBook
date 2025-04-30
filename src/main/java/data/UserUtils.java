package data;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Provides utility methods for sorting and searching User collections.
 * Supports case-insensitive name sorting and partial name matching.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class UserUtils {
    /**
     * Case-insensitive comparator for user names.
     * <pre name="test">
     *   User user1 = new User(1, "Apple", "", "+358", "", new ArrayList<>(), LocalDate.now());
     *   User user2 = new User(2, "banana", "", "+358", "", new ArrayList<>(), LocalDate.now());
     *   UserUtils.NAME_COMPARATOR.compare(user1, user2) < 0;
     * </pre>
     */
    public static final Comparator<User> NAME_COMPARATOR = 
        Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER);

    /**
     * Sorts users by name in-place using case-insensitive comparison.
     * @param users ArrayList to sort (modified directly)
     * <pre name="test">
     *   ArrayList<User> users = new ArrayList<>();
     *   users.add(new User(1, "Zebra", "", "+358", "", new ArrayList<>(), LocalDate.now()));
     *   users.add(new User(2, "Apple", "", "+358", "", new ArrayList<>(), LocalDate.now()));
     *   UserUtils.sortUsersByName(users);
     *   users.get(0).getName() === "Apple";
     * </pre>
     */
    public static void sortUsersByName(ArrayList<User> users) {
        Collections.sort(users, NAME_COMPARATOR);
    }

    /**
     * Finds users containing search query in name (case-insensitive partial match).
     * @param users ArrayList to search through
     * @param searchQuery partial name to match (ignores leading/trailing whitespace)
     * @return new ArrayList of matching users (empty if no matches)
     * <pre name="test">
     *   ArrayList<User> users = new ArrayList<>();
     *   users.add(new User(1, "Harry Potter", "", "+358", "", new ArrayList<>(), LocalDate.now()));
     *   users.add(new User(2, "The Hobbit", "", "+358", "", new ArrayList<>(), LocalDate.now()));
     *   UserUtils.findUsersByName(users, "harry").size() === 1;
     *   UserUtils.findUsersByName(users, "").isEmpty() === true;
     * </pre>
     */
    public static ArrayList<User> findUsersByName(ArrayList<User> users, String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return users;
        }

        String trimmedQuery = searchQuery.trim().toLowerCase();

        return users.stream()
            .filter(user -> user.getName() != null 
                    && user.getName().toLowerCase().contains(trimmedQuery))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
