import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
public class StudentGUIWindow extends JFrame{
    Account s; // student Account object
    public static final int WIDTH = 300; // Width of frame
    public static final int HEIGHT = 200; // Height of frame 
    public StudentGUIWindow(Account a) {
        s = a; // retains student Account object
        
        HashSet<Integer> courseIDList = a.getCourseIDList(); // gets list of course ID in student Account object
        int buttonNum = courseIDList.size(); // gets size of said list

        Iterator<Integer> courseIDListIterator = courseIDList.iterator(); // iterator for list of course ID in student Account object
        Storage storage = new Storage(); // initializes Storage object to access database
        HashSet<Course> courseList = storage.readCourseStorage(); // gets the list of Course objects in database
        Iterator<Course> courseListIterator = courseList.iterator(); // iterator for list of Course objects in database
        
        // sets settings for frame
        setTitle("StudentGUIWindow"); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("StudentGUIWindow");
        topPanel.add(prompt);
        add(topPanel);
        
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(buttonNum,1));
        buttonPanel.setBackground(Color.WHITE);
        // loops to create as many Button objects as associated with the student Account
        while (courseListIterator.hasNext()) {
            Course c = courseListIterator.next();
            if (courseIDList.contains(c.getCourseID())) {
                JButton button = new JButton("Course: " + c.getName());
                button.addActionListener(new OptionsListener(c));
                buttonPanel.add(button);
            }
        }
        add(buttonPanel);
        
        // third panel - return button panel
        JPanel returnPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ReturnListener());        
        returnPanel.add(returnButton);
        add(returnPanel);        
    }
    
    // return button listener
    private class ReturnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new LoginWindow().setVisible(true);
        }
    }
    
    // course button panel
    private class OptionsListener implements ActionListener {
        Course c;
        public OptionsListener(Course co) {
            c = co;  
        }
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new StudentOptionsGUIWindow(s, c).setVisible(true);
        }
    }   
}