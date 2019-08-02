
/**
 * Write a description of class Storage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
public class Storage {
    private static HashSet<Course>  c = new HashSet<Course>(); // internal class variable for list of Course objects
    private static HashSet<Account> a = new HashSet<Account>(); // internal class variable for list of Account objects
    
    // Constructor to load data from CourseList.dat and AccountList.dat
    public Storage() {
        loadStorage();
    }
    
    // Constructor to initilizes data
    public Storage(HashSet<Course> courseList, HashSet<Account> accountList) {
        c = courseList;
        a = accountList;
    }
    
    // Method to read in data from CourseList.dat
    public HashSet<Course> readCourseStorage() {
        HashSet<Course> courseList = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("CourseList.dat"));            
            System.out.println("Reading Course List from Binary File");
            courseList = (HashSet<Course>) inputStream.readObject();
            inputStream.close();         
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find CourseList.dat file");
            System.exit(0);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Problems with file input.");
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("Problem with Course input");
            System.exit(0);
        }
        return courseList;
    }
    
    // Method to write list of Course objects to binary file
    public void writeCourseStorage(HashSet<Course> courseList) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("CourseList.dat"));
            System.out.println("Writing Course List from Binary File");
            outputStream.writeObject(courseList);
            outputStream.close();
        }
        catch (IOException e) {
            System.out.println("Problem with file output.");
            System.exit(0);
        }
    }
    
    // Method to read in data from AccountList.dat
    public HashSet<Account> readAccountStorage() {
        HashSet<Account> accountList = null; 
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("AccountList.dat"));            
            System.out.println("Reading Account List from Binary File");
            accountList = (HashSet<Account>)inputStream.readObject();
            inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find AccountList.dat file");
            System.exit(0);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Problems with file input.");
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("Problem with Account input");
            System.exit(0);
        }
        return accountList;
    }
    
    // Method to write list of Course objects to binary file
    public void writeAccountStorage(HashSet<Account> accountList) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("AccountList.dat"));    
            System.out.println("Writing Account List from Binary File");
            outputStream.writeObject(accountList);
            outputStream.close();
        }
        catch (IOException e) {
            System.out.println("Problem with file output.");
            System.exit(0);
        }
    }
    
    // Method to load binary files' data onto internal class variables
    public void loadStorage() {
        c = readCourseStorage();
        a = readAccountStorage();
    }
    
    // Method to save internal class variables to binary files 
    public void saveStorage() {
        writeAccountStorage(a);
        writeCourseStorage(c);
    }
    
    // Method to add grade to student via the Storage class
    public void addGrade(Account account,int courseID, String assName, String student, int grade) {
            Iterator<Course> courseIterator = c.iterator();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                if (course.getCourseID() == courseID) {
                    course.addGrade(assName, grade, student);
                    break;
                }
                course = null;
            }
            saveStorage();
        
    }
    
    // Method to edit grade to student via the Storage class
    public void editGrade(Account account, int courseID, String assName, String student, int grade) {
            Iterator<Course> courseIterator = c.iterator();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                if (course.getCourseID() == courseID) {
                    course.editGrade(assName, grade, student);
                    break;
                }
            }
            saveStorage();
    }
    
    // Method to add an Assignment object to a Course object via the Storage class
    public void addAssignment (Account account, int courseID, String assName, String assDesc) {
            Iterator<Course> courseIterator = c.iterator();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                if (course.getCourseID() == courseID) {
                    course.addAssignment(assName, assDesc);
                    saveStorage();
                }
            }
    }
    
    // Method to remove an Assignment object from a Course object via the Storage class
    public void removeAssignment(Account account, int courseID, String assName) {
            Iterator<Course> courseIterator = c.iterator();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                if (course.getCourseID() == courseID) { 
                    course.removeAssignment(assName);
                    saveStorage();
                }
            }
    }
    
    // Method to produce a map of Assignment objects and their grades based on student Account object and ID of Course object
    public HashMap<Assignment, Integer> studentViewGrades(Account student, int courseID) {
        HashMap<Assignment, Integer> gradeList = new HashMap<Assignment, Integer>();
        Iterator<Course> courseIterator = c.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            if (course.getCourseID() == courseID) {
                gradeList = course.getGrade(student.getUsername());
            }
        }        
        return gradeList; 
    }
    
    // Method to produce a list of Assignment objects' String name and their grades based on professor or TA Account object and ID of Course object
    public HashMap<String, Integer> proftaViewGrades(Account profta, int courseID, String assName) {
            HashMap<String, Integer> gradeList = new HashMap<String, Integer>();
            Iterator<Course> courseIterator = c.iterator();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                if (course.getCourseID() == courseID) {
                    HashSet<Assignment> assignmentList = course.getAssignmentList();
                    Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
                    while (assignmentListIterator.hasNext()) {
                        Assignment assignment = assignmentListIterator.next();
                        
                        if (assignment.getName().equals(assName)) {
                            gradeList = assignment.getGrade();
                        }
                    }
                }    
            }
            return gradeList;        
    }
    
    // Method to verify Account object in database
    public Account verifyAccount(String username, String password) throws NullPointerException {
        Iterator<Account> accountIterator = a.iterator();
        while (accountIterator.hasNext()) {
            Account a = accountIterator.next();
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                return a;
            }
        }
        throw new NullPointerException("Username and Password combination does not exist.");
    }
    
    // Method to return list of Course objects
    public HashSet<Course> getCourseCatalog() {
        return c;
    }
    
    // Method to return list of Account objects
    public HashSet<Account> getAccountList(){
        return a;
    }
    
    // Method to return list of Assignment objects of a Course object
    public HashSet<Assignment> getAssignmentList(Account a, Course c) {        
        return c.getAssignmentList();
    }
    
    // Method to return a Course object based on its ID number    
    public Course getCourse(int courseID) {
        Course course = null;
        Iterator<Course> courseIterator = c.iterator();
        while (courseIterator.hasNext()) {
                course = courseIterator.next();
                if (course.getCourseID() == courseID) {
                    return course;
                }
        }
        return course;
    }
    
    // Method to return an Account object based on its username
    public Account getAccount(String username) {
        Account acc = null;
        Iterator<Account> accountListIterator = a.iterator();
        while (accountListIterator.hasNext()) {
            acc = accountListIterator.next();
            if (acc.getUsername().equals(username)) {
                return acc;
            }
        }
        return acc;
    }
    
    // Method to return list of student Account objects' username from a Course object
    public HashSet<String> getStudentRoster(Account a, Course course) {
        HashSet<String> studentRoster = new HashSet<String>();
        HashSet<Account> studentList =  course.getStudentList();
        Iterator<Account> studentListIterator = studentList.iterator();
        while (studentListIterator.hasNext()) {
            String student = (studentListIterator.next()).getUsername();
            studentRoster.add(student);
        }

        return studentRoster;
    }
    
    // Method to return list of TA Account objects' username from a Course object
    public HashSet<String> getTARoster(Account a, Course course) {
        HashSet<String> taRoster = new HashSet<String>();
        HashSet<Account> taList =  course.getTAList();
        Iterator<Account> taListIterator = taList.iterator();
        while (taListIterator.hasNext()) {
            String ta = (taListIterator.next()).getUsername();
            taRoster.add(ta);
        }
        return taRoster;
    }
    
    // Method to remove student Account info from a Course object and return the account info with course ID removed from its internal courseIDList
    public Account studentDropOut(Account a, Course course) {
        Course co = getCourse(course.getCourseID());
        Account acc = getAccount(a.getUsername());        
        co.removeStudent(a.getUsername());
        acc.removeCourse(course.getCourseID()); 
        saveStorage();
        return acc;
    }
    
    // Method to remove Course object from the list of Course objects in Storage and return account info with course ID removed from its internal courseIDList
    public Account removeCourse(Account a, Course course) {
        c.remove(getCourse(course.getCourseID()));
        Account acc = getAccount(a.getUsername());
        acc.removeCourse(course.getCourseID());        
        saveStorage();
        return acc;
    }
}

