package data;

/**
 * Custom exception for data management operations in the library system.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public class DataException extends Exception {
    /**
     * Creates a new data exception with specified message.
     * @param message descriptive error message
     */
    public DataException(String message) {
        super(message);
    }
}
