package data;

/**
 * Represents the possible states of an book in a lending system.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 */
public enum State {
    /** Book is currently borrowed by a user */
    loaned,
    /** Book is available for borrowing */
    available,
    /** Book cannot be located in the system */
    missing;
}
