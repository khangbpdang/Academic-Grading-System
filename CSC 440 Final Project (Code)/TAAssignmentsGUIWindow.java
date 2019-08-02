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

public class TAAssignmentsGUIWindow extends JFrame{
    Account ta; // TA Account object
    Course course; // current Course object
    public static final int WIDTH = 300; // width of frame
    public static final int HEIGHT = 200; // height of frame
    public TAAssignmentsGUIWindow(Account a, Course c) {
        ta = a; // retains TA Account object
        course = c; // retains current Course object
        
        HashSet<Integer> courseIDList = a.getCourseIDList(); // gets list of course ID in TA Account object
        int buttonNum = courseIDList.size(); // gets size of said list
        
        // set settings for frame
        setTitle("TAAssignmentsGUIWindow");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("TAAssignmentsGUIWindow");
        topPanel.add(prompt);
        add(topPanel);
        
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(buttonNum,1));
        buttonPanel.setBackground(Color.WHITE); 
        // assignment access         
        HashSet<Assignment> assignmentList = course.getAssignmentList();
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        // loops to create as many Button objects as the number of assignments associated with the TA Account
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
           new TAOptionsGUIWindow(ta, course).setVisible(true);
        }
    }
    
    // assignment buttons listener
    private class OptionsListener implements ActionListener {
        Course c;
        Assignment a;
        public OptionsListener(Course co, Assignment ass) {
            c = co;
            a = ass;
        }
        
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           TAViewGradesGUI gui = new TAViewGradesGUI(ta, c, a);
           gui.pack();
           gui.setVisible(true);
           dispose();
        }
    }

}