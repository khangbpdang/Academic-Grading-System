import java.io.Serializable;
import java.util.HashSet;
/**
 * Write a description of class Account here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Account implements Serializable{
    private String username, pw; // String objects to contain Account log in username and password
    private char role; // a char variable to determine: p - Professor; s - Student; t - TA; d - default role
    private HashSet<Integer> courseIDList = new HashSet(); // List to contain course ID associated with the Account object
    
    // Constructor for default Account object
    public Account() {
        setUsername("");
        setPassword("");
        setRole('d');
    }
    
    // Constructor for role-specific creation of Account object
    public Account(String newUsername, String newPw, char newRole) {
        setUsername(newUsername);
        setPassword(newPw);
        if ((newRole == 'p') || (newRole == 's') || newRole == 't')
            setRole(newRole);
        else
            setRole('d');
    }
    
    // Method to return username of Account object
    public String getUsername() {
        return username;
    }
    
    // Method to return password of Account object
    public String getPassword() {
        return pw;
    }
    
    // Method to return role of Account object
    public char getRole() {
        return role;
    }
    
    // Method to return list of course ID of Account object
    public HashSet getCourseIDList() {
        return courseIDList;
    }
    
    // Method to change username of Account object
    public void setUsername(String newUsername) {
        username = newUsername;
    }
    
    // Method to change password of Account object
    public void setPassword(String newPw) {
        pw = newPw;
    }
    
    // Method to change role of Account object
    public void setRole(char newRole) {
        role = newRole;
    }
    
    // Method to add a course ID to the list of course ID
    public boolean addCourse(int id) {
        return courseIDList.add(id);
    }
    
    // Method to remove a course ID from list of course ID
    public boolean removeCourse(int id) {
        return courseIDList.remove(id);
    }
    
    // Method to compare 2 Account objects
    public boolean equals(Account a) {
        if (getUsername().equals(a.getUsername()))
            return true;
        else
            return false;
    }
    
    // Method to print an Account object
    public String toString() {
        return ("username: " + getUsername() + "\nrole: " +getRole());
    }
}
