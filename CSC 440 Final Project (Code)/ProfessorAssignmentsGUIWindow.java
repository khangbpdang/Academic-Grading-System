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
public class ProfessorAssignmentsGUIWindow extends JFrame{
    Account p; // prof Account object
    Course course; // current Course object
    public static final int WIDTH = 300; // width and height of frame
    public static final int HEIGHT = 200;
    public ProfessorAssignmentsGUIWindow(Account a, Course c) {
        p = a; // retains prof Account object and current Course object
        course = c;
        HashSet<Integer> courseIDList = a.getCourseIDList(); // gets list of course ID in prof Account object
        int buttonNum = courseIDList.size(); // gets size of said list
        
        // settings of frame
        setTitle("ProfessorAssignmentsGUIWindow");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("ProfessorAssignmentsGUIWindow");
        topPanel.add(prompt);
        add(topPanel);
        
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(buttonNum,1));
        buttonPanel.setBackground(Color.WHITE);
        // accesses assignment in the current Course object
        HashSet<Assignment> assignmentList = course.getAssignmentList();
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        // loops to create as many Button objects as the number of assignments associated with the prof Account
        while (assignmentListIterator.hasNext()) {
            Assignment ass = assignmentListIterator.next();
            JButton button = new JButton("Assignment: " + ass.getName());
            button.addActionListener(new OptionsListener(c, ass));
            buttonPanel.add(button);
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
           new ProfessorOptionsGUIWindow(p, course).setVisible(true);
        }
    }
    
    // assignment button listener
    private class OptionsListener implements ActionListener {
        Course c;
        Assignment a;
        public OptionsListener(Course co, Assignment ass) {
            c = co;
            a = ass;
        }
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           ProfessorViewGradesGUI gui = new ProfessorViewGradesGUI(p, c, a);
           gui.pack();
           gui.setVisible(true);
           dispose();
        }
    }

    
}