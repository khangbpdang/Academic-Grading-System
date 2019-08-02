
/**
 * Write a description of class InvalidRole here.
 * 
 * InvalidRoleException to represent a wrong Account object's role being passed
 */
public class InvalidRoleException extends Exception {
    char role; // role char variable
    // Default constructor
    public InvalidRoleException() {
        super("DuplicateValueException");
    }
    
    // Constructor to save the wrong role
    public InvalidRoleException(char newRole) {
        super("DuplicateValueException");
        role = newRole;
    }
    
    // Method to return wrong role
    public char returnVariable() {
        return role;
    }
    
    // Conversion constructor that sets the message
    public InvalidRoleException(String message) {
        super(message);
    }
}
