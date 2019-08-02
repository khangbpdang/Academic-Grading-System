import java.io.Serializable;
import java.util.*;
public class Course implements Serializable{
    private String name; // course name
    private int courseID; // Identier for matching with Account objects
    private HashSet<Assignment> assignmentList = new HashSet<Assignment>(); // List of Assignment objects
    private String profUsername; // username of professor Account
    private HashSet<Account> taList = new HashSet<Account>(); // List of TA Account for the course
    private HashSet<Account> studentList = new HashSet<Account>(); // List of student Account for the course
    
    // Constructor needs a course name, course ID, name of prof username, list of Account objects
    public Course(String newName, int newCourseID, String prof, HashSet<Account> account) throws InvalidRoleException {
        name = newName;
        courseID = newCourseID;
        Integer courseNum = new Integer(0);
        boolean value = true;
        Iterator<Account> accountIterator = account.iterator();
        while (accountIterator.hasNext()) {
            Account a = accountIterator.next();            
            if (!(checkProfessorInList(prof, account)))
                throw new InvalidRoleException(prof + " is not a professor usernames." + "\n" + a.getRole());
            if (a.getRole() == 's'){
                HashSet<Integer> courseIDList = a.getCourseIDList();
                Iterator<Integer> courseIDIterator = courseIDList.iterator();                
                while (courseIDIterator.hasNext()){
                    courseNum = courseIDIterator.next();                   
                    if (courseNum.equals(courseID)) {
                        studentList.add(a);
                    }
                }
            }
            if (a.getRole() == 't'){
                HashSet<Integer> courseIDList = a.getCourseIDList();
                Iterator<Integer> courseIDIterator = courseIDList.iterator();                
                while (courseIDIterator.hasNext()){
                    courseNum = courseIDIterator.next();                   
                    if (courseNum.equals(courseID)) {
                        taList.add(a);
                    }
                }
            }      
        }
    }
    
    // Method to change name
    public void setName(String newName) {
        name = newName;
    }
    
    // Method to return course name
    public String getName() {
        return name;
    }
    
    // Method to change ID of course
    public void setCourseID(int newID) {
        courseID = newID;
    }
    
    // Method to return course ID
    public int getCourseID() {
        return courseID;
    }
    
    // Method to change list of Assignment objects
      public HashSet<Assignment> getAssignmentList() {
        return assignmentList;
    }
   
    // Method to return course name
    public void setAssignmentList(HashSet newAssignmentList) {
        assignmentList = newAssignmentList;
    }
    
    // Method to add a new Assignment object to list of Assignment objects with a String for name and a String for description
    public void addAssignment(String assName, String desc) {
        if (!(checkAssignmentInList(assName))) {
            Assignment a = new Assignment(assName, desc, courseID, studentList);
            assignmentList.add(a);
        }
        else
            throw new NullPointerException("Assignment is already in course " + getName());
    }
    
    // Method to add a new Assignment object to list of Assignment objects based on an Assignment object
    public void addAssignment(Assignment a) {
        addAssignment(a.getName(), a.getDesc());
    }
    
    // Method to remove an Assignment object from list of Assignment objects based on a String for name of assignment
    public void removeAssignment(String assName)  {
        if (checkAssignmentInList(assName)) {
            System.out.println("True");
            Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
            while (assignmentListIterator.hasNext()) {
                Assignment a = assignmentListIterator.next();
                if (a.checkAllIfUngraded() == true && a.getName().equals(assName)) 
                    assignmentList.remove(a);
            }
        }
        else
            throw new NullPointerException("Assignment" + assName + " is not elegible for removal.");
    }
    
    // Method to remove an Assignment object from list of Assignment objects based on an Assignment object
    public void removeAssignment(Assignment a) {    
        removeAssignment(a.getName());
    }
    
    // // Method to remove grade of a student in list of student based on student username
    public void removeStudentGrade(String student) {
        if (!(checkStudentInList(student))) {
            throw new NullPointerException("Student not in list");
        }
        else {
            Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
            while (assignmentListIterator.hasNext()) {
                Assignment a = assignmentListIterator.next();
                a.removeStudentGrade(student);
            }
            Iterator<Account> studentListIterator = studentList.iterator();
            while (studentListIterator.hasNext()) {
                Account s = studentListIterator.next();
                if (s.getUsername().equals(student)) {
                    studentList.remove(s);
                    break;
                }
            }
        }    
    }

    // Method to change professor name in the Course
    public boolean setProf(String prof, HashSet<Account> account) throws InvalidRoleException {        
        Iterator<Account> accountIterator = account.iterator();
        boolean value = false;
        while (accountIterator.hasNext()) {
            Account a = accountIterator.next();
            if (a.getUsername().equals(prof) && a.getRole() == 'p') {
                profUsername = prof;
                value = true;
            }
            else
                throw new InvalidRoleException(prof + " is not a professor username.");
        }
        return value;
    }
    
    // Method to return professor's username in the Course
    public String getProf() {
        return profUsername;
    }
    
    // Method to return a list of Assignment objects and grades for a student Account using student username
    public HashMap<Assignment, Integer> getGrade(String student) {        
        if (!(checkStudentInList(student))) {
            throw new NullPointerException("Student not in list");
        }
        else {
            HashMap<Assignment, Integer> studentGrade = new HashMap<Assignment, Integer>();
            Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
            while (assignmentListIterator.hasNext()) {
                Assignment a = assignmentListIterator.next();                
                studentGrade.put(a, a.getGrade(student));                
            }
            
            return studentGrade;
        }        
    }
    
    // Method to add grade to an assignment of a student 
    public void addGrade(String assName, int grade, String student) throws NullPointerException {
        if (!(checkStudentInList(student))) {
            throw new NullPointerException("Student not in list");
        }
        else{           
            Iterator<Assignment> assignmentListIterator = assignmentList.iterator();       
            while (assignmentListIterator.hasNext()) {
                Assignment a = assignmentListIterator.next();
                if (!(checkAssignmentInList(assName))) {
                    throw new NullPointerException("Assignment " + assName + " not found in course " + name);            
                }
                if (a.getName().equals(assName)) {
                    try {
                        a.addGrade(student, grade);
                    }
                    catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (InvalidNumberException e) {
                        System.out.println(e.getMessage());
                    }
                }              
            }        
        }            
    }
    
    // Method to edit grade of an assignment of a student 
    public void editGrade(String assName, int grade, String student) throws NullPointerException {
        if (!(checkStudentInList(student))) {
            throw new NullPointerException("Student not in list");
        }
        else{
            Iterator<Assignment> assignmentListIterator = assignmentList.iterator();       
            while (assignmentListIterator.hasNext()) {
                Assignment a = assignmentListIterator.next();
                if (!(checkAssignmentInList(assName))) {
                    throw new NullPointerException("Assignment " + a.getName() + " not found in course " + name);            
                }
                if (a.getName().equals(assName)) {
                    try {
                        a.editGrade(student, grade);
                    }
                    catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (InvalidNumberException e) {
                        System.out.println(e.getMessage());
                    }
                }              
            }        
        }            
    }
    
    // Method to add TA Account to the course
    public boolean addTA(String t, HashSet<Account> account) throws InvalidRoleException{
        Iterator<Account> accountIterator = account.iterator();
        boolean value = false;
        while (accountIterator.hasNext()) {
            Account a = accountIterator.next();
            if (a.getUsername().equals(t) && a.getRole() == 't') {
                taList.add(a);
                value = true;
            }
        }
        return value;
    }
    
    // Method to check if TA Account is in the course
    public boolean checkTAInList(String ta) {
        Iterator<Account> taListIterator = taList.iterator();        
        Account t;
        while (taListIterator.hasNext()) {
            t = taListIterator.next();
            if (t.getUsername().equals(ta)) {
                return true;
            }
        }
        return false;
    }
    
    // Method to check if TA username is in a list of Account objects
    public boolean checkTAInList(String ta, HashSet<Account> account) {
        Iterator<Account> accountIterator = account.iterator();
        Account t;
        while (accountIterator.hasNext()) {
            t = accountIterator.next();
            if (t.getUsername().equals(ta) && t.getRole() == 't') {
                return true;
            }
        }
        return false;
    }
    
    // Method to check if professor Account is in the course
     public boolean checkProfInList(String p) {
        return p.equals(profUsername);
    }
    
    // Method to check if professor username is in a list of Account objects
    public boolean checkProfessorInList(String prof, HashSet<Account> account) {
        Iterator<Account> accountIterator = account.iterator();
        Account p;
        while (accountIterator.hasNext()) {
            p = accountIterator.next();
            if (p.getUsername().equals(prof) && p.getRole() == 'p') {
                return true;
            }
        }
        return false;
    }
    
    // Method to check if student Account is in the course
    public boolean checkStudentInList(String student) {
        Iterator<Account> studentListIterator = studentList.iterator();        
        Account s;
        while (studentListIterator.hasNext()) {
            s = studentListIterator.next();
            if (s.getUsername().equals(student)) {
                return true;
            }
        }
        return false;
    }
    
    // Method to return a list of assignment names and their grades fot students
    public Map<String, Integer> getAssignmentGrade(Assignment a) {
        Map<String, Integer> assignmentGrade = new HashMap();
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        while (assignmentListIterator.hasNext()) {
            Assignment b = assignmentListIterator.next();
            if (a.equals(b))
                return b.getGrade();
        }
        return assignmentGrade;
    }
    
    // Method to check if assignment is in the list of Assignment objects
    public boolean checkAssignmentInList(String assName) {
         Iterator<Assignment> assignmentListIterator = assignmentList.iterator();       
         while (assignmentListIterator.hasNext()) {
             Assignment a = assignmentListIterator.next();
             if (a.getName().equals(assName)) {
                 return true;
             }
         }
         return false;
    }
    
    // Method to compare 2 Course objects based on ID number
    public boolean equals(Course c) {
        if (getCourseID() == c.getCourseID())
            return true;
        else
            return false;
    }
    
    // Method to print a Course object
    public String toString() {
        return "Course: " + name + "\nCourse ID: "+ courseID + "\n";
    }
    
    // Method to return the list of student Account
    public HashSet<Account> getStudentList() {
        return studentList;
    }
    
    // Method to return the list of TA Account
    public HashSet<Account> getTAList() {
        return taList;
    }
    
    // Method to remove student from Course object based on username
    public void removeStudent(String name) {
        Iterator<Account> studentListIterator = studentList.iterator();
        while (studentListIterator.hasNext()) {
            Account a = studentListIterator.next();
            if (a.getUsername().equals(name)) {
                studentList.remove(a);
            }
        }
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        while (assignmentListIterator.hasNext()) {
            Assignment a = assignmentListIterator.next();
            a.getGrade().remove(name);
        }
    }
}    