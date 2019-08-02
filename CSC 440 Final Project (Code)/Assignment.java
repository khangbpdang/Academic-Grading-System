import java.util.*;
import java.io.Serializable;
public class Assignment implements Serializable {
    private String name; // Name of assignment or main identifier between different Assignment objects of the same Course
    private String desc; // description of assignment
    private HashMap<String, Integer> gradeMap = new HashMap<String, Integer>(); // map of grades for each student's username
    
    // Constructor to create an Assignment object based on a String for name
    public Assignment(String newName) {
        setName(newName);      
    }
    
    // Constructor to create an Assignment object based on a String for name and a String for description
    public Assignment(String newName, String newDesc) {
        setName(newName);
        setDesc(newDesc);
    }
    
    // Constructor to create an Assignment object based on a String for name, a String for description, an ID of the course and the list of Account objects
    public Assignment(String newName, String newDesc, int courseID, HashSet<Account> accountList) {
        setName(newName);
        setDesc(newDesc);
        Iterator<Account> accountListIterator = accountList.iterator();
        while (accountListIterator.hasNext()) {
            Account a = accountListIterator.next();
            if (a.getRole() == 's') {
                HashSet<Integer> courseIDList = a.getCourseIDList();
                Iterator<Integer> courseIDListIterator = courseIDList.iterator();
                while (courseIDListIterator.hasNext()) {
                    Integer i = courseIDListIterator.next();
                    if  (i.equals(courseID))
                        gradeMap.put(a.getUsername(), null);
                }
            }
        }
    }
    
    // Method to add grade to a student username if the grade is null in the map
    public void addGrade(String newUsername, int newGrade) throws NullPointerException, InvalidNumberException{
        if (newGrade < 0  || newGrade > 100)
            throw new InvalidNumberException(newGrade);
        else 
            if (!(checkIfGraded(newUsername))) {                 
                gradeMap.put(newUsername, newGrade);
            }    
            else{
                throw new NullPointerException("Assignment already graded. Assignment "+ name + " " +  newUsername);                
            }
    }
    
    // Method to edit grade of a student
    public void editGrade(String newUsername, int newGrade) throws NullPointerException, InvalidNumberException {
        if (newGrade < 0  || newGrade > 100)
            throw new InvalidNumberException("Error - Invalid grade - must be between 0 and 100");
        else 
            if (checkIfGraded(newUsername))
                gradeMap.replace(newUsername, newGrade);
            else
                throw new NullPointerException("Error - Can't edit grade of student" + newUsername);
    }
    
    // Method to check if a student has been graded
    public boolean checkIfGraded(String newUsername) throws NullPointerException{
        if (gradeMap.containsKey(newUsername)) {
            if (gradeMap.get(newUsername) == null) {
                return false;
            }
            else {
                return true;
            }
        }
        else
            throw new NullPointerException(newUsername + " is not in the list of students");
    }
    
    // Method to check if assignment is not graded for every student
    public boolean checkAllIfUngraded() {
        Set set = gradeMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (mentry.getValue() != null) 
                return false;
        }
        return true;
    }
    
    // Method to remove the grade of a student
    public void removeStudentGrade(String newUsername) {
        gradeMap.remove(newUsername);
    }    
  
    // Method to return the grades of every student in an Assignment object
    public HashMap<String, Integer> getGrade() {
        return gradeMap;
    }
    
    // Method to return grade of a single student
    public Integer getGrade(String newUsername) throws NullPointerException {
        if (gradeMap.containsKey(newUsername)) 
            return gradeMap.get(newUsername);        
        else
            return null;
    }
    
    // Method to change description of the assignment
    public void setDesc(String newDesc) {
        desc = newDesc;
    }
    
    // Method to return description of the assignment
    public String getDesc() {
        return desc;
    }
    
    // Method to change the name of the assignment
    public void setName(String newName) {
        name = newName;
    }
    
    // Method to return the name of the assignment
    public String getName() {
        return name;
    }
    
    // Method to compare 2 Assignment objects
    public boolean equals(Assignment a) {
            return name.equals(a.getName());
    }
    
    // Method to print an Assignment object
    public String toString() {
        return "Assignment " + name + "\nDesc: " + desc + " \n";
    }
}