package data;

import java.util.ArrayList;
import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Represents a user with personal details, loan associations, and serialization capabilities.
 * Extends Data class for ID management and provides JSON conversion utilities.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class User extends Data<User> {
    private String name;
    private String email;
    private String phoneNumber;
    private String countryCode;
    private ArrayList<Integer> loans;
    private LocalDate birthday;

    /**
     * Constructs a default User with empty fields and default country code.
     * <pre name="test">
     *   User user = new User();
     *   user.getID() === -1;
     *   user.getName() === "";
     *   user.getLoans().size() === 0;
     *   user.getCountryCode() === "+358";
     * </pre>
     */
    public User() {
        super(-1);
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.countryCode = "+358";
        this.loans = new ArrayList<Integer>();
        this.birthday = LocalDate.now();
    }

    /**
     * Constructs a User with specified parameters.
     * @param ID unique identifier
     * @param name full name of user
     * @param email contact email
     * @param countryCode phone country code
     * @param phoneNumber phone number
     * @param loans list of associated loan IDs
     * @param birthday date of birth
     * <pre name="test">
     *   ArrayList<Integer> loans = new ArrayList<>();
     *   loans.add(5);
     *   LocalDate date = LocalDate.of(2000,1,1);
     *   User user = new User(10, "Alice", "alice@test", "+1", "555-1234", loans, date);
     *   user.getID() === 10;
     *   user.getName() === "Alice";
     *   user.getLoans().get(0) === 5;
     *   user.getBirthday() === date;
     * </pre>
     */
    public User(int ID, String name, String email, String countryCode, String phoneNumber, ArrayList<Integer> loans,
            LocalDate birthday) {
        super(ID);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.loans = loans;
        this.birthday = birthday;
    }

    /**
     * Populates user data from JSON object.
     * @param json source JSON object containing user data
     * <pre name="test">
     *   JSONObject json = new JSONObject();
     *   json.put("id", 5);
     *   json.put("name", "Bob");
     *   json.put("email", "bob@test");
     *   json.put("phoneNumber", "0401234567");
     *   json.put("countryCode", "+358");
     *   json.put("birthday", "2000-01-01");
     *   JSONArray loans = new JSONArray();
     *   loans.put(1); loans.put(2);
     *   json.put("loans", loans);
     *   
     *   User user = new User();
     *   user.fromJsonObject(json);
     *   user.getID() === 5;
     *   user.getName() === "Bob";
     *   user.getLoans().size() === 2;
     *   user.getLoans().get(1) === 2;
     * </pre>
     */
    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.phoneNumber = json.getString("phoneNumber");
        this.countryCode = json.getString("countryCode");
        this.birthday = Data.stringToDate(json.getString("birthday"));
        JSONArray array = json.getJSONArray("loans");
        ArrayList<Integer> newList = new ArrayList<Integer>(array.length());

        for (int i = 0; i < array.length(); i++) {
            newList.add(array.getInt(i));
        }

        this.loans = newList;
    }

    /**
     * Converts user data to JSON object.
     * @return JSON representation of user
     * <pre name="test">
     *   User user = new User(7, "Eve", "eve@test", "+44", "070000000", new ArrayList<>(), LocalDate.of(1999,12,31));
     *   JSONObject json = user.toJsonObject();
     *   json.getInt("id") === 7;
     *   json.getString("name") === "Eve";
     *   json.getString("birthday") === "1999-12-31";
     * </pre>
     */
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("id", this.getID());
        object.put("name", this.name);
        object.put("email", this.email);
        object.put("phoneNumber", this.phoneNumber);
        object.put("countryCode", this.countryCode);
        object.put("loans", this.loans);
        object.put("birthday", Data.dateToString(this.birthday));

        return object;
    }

    /**
     * Creates a deep copy of the user.
     * @return new User instance with identical data
     * <pre name="test">
     *   ArrayList<Integer> loans = new ArrayList<>();
     *   loans.add(3);
     *   User original = new User(1, "Original", "orig@test", "+1", "555", loans, LocalDate.now());
     *   User copy = original.copy();
     *   copy.setLoans(new ArrayList<>());
     *   original.getLoans().size() === 1;
     *   copy.getLoans().size() === 0;
     * </pre>
     */
    public User copy() {
        ArrayList<Integer> newList = new ArrayList<Integer>(this.loans.size());
        for (int i = 0; i < this.loans.size(); i++) {
            newList.add(this.loans.get(i));
        }

        return new User(this.getID(), this.name, this.email, this.countryCode, this.phoneNumber, newList,
                this.birthday);
    }

    /**
     * Updates user's core information.
     * @param name new name
     * @param email new email
     * @param countryCode new country code
     * @param phoneNumber new phone number
     * @param birthday new birth date
     * <pre name="test">
     *   User user = new User();
     *   LocalDate newDate = LocalDate.of(2010,5,5);
     *   user.update("New Name", "new@email", "+999", "987654321", newDate);
     *   user.getName() === "New Name";
     *   user.getCountryCode() === "+999";
     *   user.getBirthday() === newDate;
     * </pre>
     */
    public void update(String name, String email, String countryCode, String phoneNumber,
            LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    /** @return user's full name */
    public String getName() {
        return this.name;
    }

    /** @return contact email address */
    public String getEmail() {
        return this.email;
    }

    /** @return phone number without country code */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /** @return phone country code */
    public String getCountryCode() {
        return this.countryCode;
    }

    /** @return list of associated loan IDs */
    public ArrayList<Integer> getLoans() {
        return this.loans;
    }

    /** @param loans new list of loan IDs */
    public void setLoans(ArrayList<Integer> loans){
        this.loans = loans;
    }

    /** @return date of birth */
    public LocalDate getBirthday() {
        return this.birthday;
    }
}
