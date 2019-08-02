import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 * Write a description of class DataInitializer here.
 *
 * @author (your name)
 * @version (a version number or a date)
*/
import java.util.*;
public class DataInitializer
{
    public static void main (String[] args) {
        try{
            // Initializes Account objects to access database
            // Students
            Account s1 = new Account("khangdang", "1234", 's');
            Account s2 = new Account("hamilton", "1234", 's');
            Account s3 = new Account("kail", "1234", 's');
            
            // Professors
            Account p1 = new Account("alexander", "1234", 'p');
            Account p2 = new Account("brendan", "1234", 'p');
            
            // TAs
            Account t1 = new Account("jackson", "1234", 't');
            Account t2 = new Account("michael", "1234", 't');
            Account t3 = new Account("javitz", "1234", 't');
            HashSet<Account> accountList = new HashSet<Account>();
            
            // Adds Course ID number to Account
            s1.addCourse(1000);
            s1.addCourse(1001);
            s2.addCourse(1000);
            s2.addCourse(1001);
            s2.addCourse(1002);
            s3.addCourse(1001);
            s3.addCourse(1002);        
            p1.addCourse(1000);
            p1.addCourse(1001);
            p2.addCourse(1002);
            t1.addCourse(1000);
            t1.addCourse(1001);
            t1.addCourse(1002);
            t2.addCourse(1001);
            t2.addCourse(1002);
            t3.addCourse(1002);
                        
            // Add Account objects into list to keep in database
            accountList.add(s1);
            accountList.add(s2);
            accountList.add(s3);
            accountList.add(p1);
            accountList.add(p2);
            accountList.add(t1);
            accountList.add(t2);
            accountList.add(t3);
         
            // Creates Course c1
            Course c1 = new Course("Prob Theory", 1000, p1.getUsername(), accountList);
            
            // Assignment  objects for Course c1
            Assignment a11 = new Assignment("1", "Nothing", 1000, accountList);
            Assignment a12 = new Assignment("2", "Nothing", 1000, accountList);
            c1.addAssignment(a11);
            c1.addAssignment(a12);
            // Creates and adds Assignment objects using String
            c1.addAssignment("4", "This Assignment Is NOT Graded");
            c1.addAssignment("5", "This Assignment Is NOT Graded");    
            // grades for Account in Assignment a11 and a12
            c1.addGrade("1", 100, "khangdang");
            c1.addGrade("2", 70, "khangdang");
            
            
            // Creates Course c2
            Course c2 = new Course("English", 1001, p1.getUsername(), accountList);
            
            // Assignment  objects for Course c2 
            Assignment a21 = new Assignment("1", "Nothing", 1001, accountList); 
            Assignment a22 = new Assignment("2", "Nothing", 1001, accountList);
            c2.addAssignment(a21);
            c2.addAssignment(a22);
            c2.addGrade("1", 100, "hamilton");
            
            // Creates Course c3            
            Course c3 = new Course("Comp Sci", 1002, p2.getUsername(), accountList);
            // Adds assignments for Course c3
            c3.addAssignment("1", "This Assignment Is NOT Graded");
            c3.addAssignment("2", "This Assignment Is NOT Graded");
            Assignment c33 = new Assignment("3", "Nothing", 1002, accountList);
            c3.addAssignment(c33);
            c3.addGrade("3", 100, "kail");
            
            // Puts Course Objects into courseList
            HashSet<Course> courseList = new HashSet<Course>();
            courseList.add(c1);
            courseList.add(c2);
            courseList.add(c3);
            
            // Initilizes Storage to store lists of Account objects and Course objects
            Storage storage = new Storage(courseList, accountList);
            storage.writeAccountStorage(accountList);
            storage.writeCourseStorage(courseList);
            storage.loadStorage();
            
            // Adds and edits grades of students through Storage
            storage.addGrade(t1, 1000, "1", "hamilton", 50);
            storage.addGrade(t1, 1001, "1", "kail", 70);
            storage.editGrade(p1, 1000, "1", "hamilton", 70);
        }
        catch (InvalidRoleException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
}
