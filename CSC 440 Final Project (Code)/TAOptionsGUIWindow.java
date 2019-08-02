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

public class TAOptionsGUIWindow extends JFrame {
    public static final int WIDTH = 400; // width of frame
    public static final int HEIGHT = 300; // height of frame
    Account ta; // TA Account object
    Course course; // chosen Course object
    public TAOptionsGUIWindow(Account a, Course c) {
        ta = a; // retains Account object
        course = c; // retain Course object
        
        // set settings for frame
        setTitle("TAOptionsGUIWindow"); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
       
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("TAOptionsGUIWindow");
        titlePanel.add(prompt);
        add(titlePanel);
        
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel);
        // view grades Button
        JButton viewGradesButton = new JButton("View Grades");
        viewGradesButton.addActionListener(new ViewGradesListener());
        buttonPanel.add(viewGradesButton);
        // view assignment button
        JButton viewAssignmentsButton = new JButton("View Assignments");
        viewAssignmentsButton.addActionListener(new ViewAssignmentsListener());
        buttonPanel.add(viewAssignmentsButton);
        // view roster button
        JButton viewRosterButton = new JButton("View Roster");
        viewRosterButton.addActionListener(new ViewRosterListener());
        buttonPanel.add(viewRosterButton);
        
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
           new TAGUIWindow(ta).setVisible(true);
        }
    }
    
    // view grade button listener
    private class ViewGradesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();          
           new TAAssignmentsGUIWindow(ta, course).setVisible(true);
           dispose();
        }
    }
    
    // view assignment button listener
    private class ViewAssignmentsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new TAViewAssignmentsGUI(ta, course).setVisible(true);
        }
    }
    
    // view roster button listener
    private class ViewRosterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            dispose();
            new TARosterWindow(ta, course).setVisible(true);
        }
    }
}
