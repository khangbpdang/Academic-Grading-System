import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
public class ProfessorOptionsGUIWindow extends JFrame {
    public static final int WIDTH = 400; // width and height of frame
    public static final int HEIGHT = 300;
    Account p; // prof Account object
    Course course; // current Course object
    public ProfessorOptionsGUIWindow(Account a, Course c) {
        p = a; // retains prof Account object and current Course object
        course = c;
        
        // setting for frame
        setTitle("ProfessorOptionsGUIWindow");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("ProfessorOptionsGUIWindow");
        titlePanel.add(prompt);
        add(titlePanel);
   
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel);
        // view grade button
        JButton viewGradesButton = new JButton("View Grades");
        viewGradesButton.addActionListener(new ViewGradesListener());
        buttonPanel.add(viewGradesButton);
        // view roster button
        JButton viewRosterButton = new JButton("View Roster");
        viewRosterButton.addActionListener(new ViewRosterListener());
        buttonPanel.add(viewRosterButton);
        // view assignment button
        JButton viewAssignmentsButton = new JButton("View Assignments");
        viewAssignmentsButton.addActionListener(new ViewAssignmentsListener());
        buttonPanel.add(viewAssignmentsButton);
        // view list of TA button
        JButton viewTAButton = new JButton("View TA List");
        viewTAButton.addActionListener(new ViewTAListener());
        buttonPanel.add(viewTAButton);
        // remove Course object from database button
        JButton removeCourseButton = new JButton("Remove Course");
        removeCourseButton.addActionListener(new RemoveCourseListener());
        buttonPanel.add(removeCourseButton);
        
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
           new ProfessorGUIWindow(p).setVisible(true);
        }
    }
    
    // view grade button listener
    private class ViewGradesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new ProfessorAssignmentsGUIWindow(p, course).setVisible(true);
        }
    }
    
    // view assignment button listener
    private class ViewAssignmentsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand(); 
           dispose();
           ProfessorViewAssignmentsGUI gui = new ProfessorViewAssignmentsGUI(p, course);
           gui.pack();
           gui.setVisible(true);
        }
    }
    
    // view roster button listener
    private class ViewRosterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            dispose();
            new ProfRosterWindow(p, course).setVisible(true);
        }
    }
    
    // view list of TA button listener
    private class ViewTAListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            dispose();
            new ViewTAWindow(p, course).setVisible(true);
        }
    }
    
    // remove Course object button listener
    private class RemoveCourseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            Storage storage = new Storage();
            Account a = storage.removeCourse(p, course);
            (new ProfessorGUIWindow(a)).setVisible(true);
            dispose();
        }
    }
}
