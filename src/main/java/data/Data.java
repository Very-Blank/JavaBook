package data;

import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for data entities providing JSON serialization and date handling.
 * Forces implementing classes to support deep copying and JSON conversion.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 * @param <T> self-referential generic type for fluent copying
 */
public abstract class Data<T extends Data<T>> {
    private int ID;

    /**
     * Initializes data object with specified ID.
     * @param ID unique identifier for the data object
     */
    public Data(int ID) {
        this.ID = ID;
    }

    /**
     * Populates object fields from JSON representation.
     * @param json source JSON data
     */
    public abstract void fromJsonObject(JSONObject json);

    /**
     * Serializes object to JSON format.
     * @return JSON representation of the object
     */
    public abstract JSONObject toJsonObject();

    /**
     * Converts LocalDate to ISO-style string format (dd LLLL yyyy).
     * @param date date to convert
     * @return formatted date string
     */
    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return date.format(formatter);
    }

    /**
     * Parses ISO-style date string (dd LLLL yyyy) to LocalDate.
     * @param date string to parse
     * @return parsed date object
     */
    public static LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Creates a deep copy of the object.
     * @return new independent instance with identical data
     */
    public abstract T copy();

    /** @return unique identifier of the data object */
    public int getID() {
        return this.ID;
    }

    /**
     * Updates the object's unique identifier.
     * @param ID new identifier value
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
