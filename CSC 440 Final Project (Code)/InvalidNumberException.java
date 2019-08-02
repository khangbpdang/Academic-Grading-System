
/**
 * Write a description of class InvalidNumberException here.
 *
 * InvalidNumberException to represent a wrong numerical value being passed
 */
public class InvalidNumberException extends Exception {
    int num; // integer variable to represent wrong int value
    // Default constructor
    public InvalidNumberException() {
        super("DuplicateValueException");
    }
    
    // Construtor to save the wrong int value
    public InvalidNumberException(int newNum) {
        super("DuplicateValueException");
        num = newNum;
    }
    
    // Method to return the wrong integer value
    public int returnVariable() {
        return num;
    }
    
    // Conversion constructor that sets the message
    public InvalidNumberException(String message) {
        super(message);
    }
}
